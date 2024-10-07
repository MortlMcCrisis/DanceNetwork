package com.mortl.dancenetwork.service.impl;

import com.mortl.dancenetwork.dto.TicketTypeDTO;
import com.mortl.dancenetwork.entity.TicketType;
import com.mortl.dancenetwork.mapper.TicketTypeMapper;
import com.mortl.dancenetwork.repository.TicketTypeRepository;
import com.mortl.dancenetwork.service.IStripeService;
import com.mortl.dancenetwork.service.ITicketTypeService;
import com.stripe.exception.StripeException;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class TicketTypeServiceImpl implements ITicketTypeService
{

  private final TicketTypeRepository ticketTypeRepository;

  private final TicketTypeMapper ticketTypeMapper;

  private final IStripeService stripeService;

  public TicketTypeServiceImpl(
      TicketTypeRepository ticketTypeRepository,
      TicketTypeMapper ticketTypeMapper,
      IStripeService stripeService)
  {
    this.ticketTypeRepository = ticketTypeRepository;
    this.ticketTypeMapper = ticketTypeMapper;
    this.stripeService = stripeService;
  }

  @Override
  public List<TicketTypeDTO> getTicketTypesForEvent(Long eventId)
  {
    return ticketTypeRepository.findByEventId(eventId).stream()
        .map(ticket -> ticketTypeMapper.toDTO(ticket))
        .toList();
  }

  @Override
  public List<TicketTypeDTO> updateTicketTypes(List<TicketTypeDTO> ticketTypeDTOs)
  {
    //TODO test with application context test when works with jwt (see EventControllerClosedSpec)
    List<Long> newTicketTypeIds = ticketTypeDTOs.stream()
        .map(TicketTypeDTO::id)
        .toList();

    List<Long> ticketTypeIdsToDelete = ticketTypeRepository.findByEventId(
            ticketTypeDTOs.get(0).eventId()).stream()
        .filter(oldTicketType -> !newTicketTypeIds.contains(oldTicketType.getId()))
        .map(TicketType::getId)
        .toList();
    List<TicketType> ticketTypesToAddOrUpdate = ticketTypeDTOs.stream()
        .map(ticketTypeDTO -> ticketTypeMapper.toModel(ticketTypeDTO))
        .toList();

    ticketTypeRepository.saveAllAndFlush(ticketTypesToAddOrUpdate);
    if (!ticketTypesToAddOrUpdate.isEmpty() && ticketTypesToAddOrUpdate.get(0).getEvent()
        .isPublished())
    {
      try
      {
        stripeService.syncTicketTypes(ticketTypesToAddOrUpdate);
      } catch (StripeException e)
      {
        throw new RuntimeException(e);
      }
    }
    ticketTypeRepository.deleteAllById(ticketTypeIdsToDelete);

    return ticketTypesToAddOrUpdate.stream()
        .map(ticketTypeMapper::toDTO)
        .toList();
  }
}
