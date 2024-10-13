package com.mortl.dancenetwork.service.impl;

import com.mortl.dancenetwork.entity.TicketType;
import com.mortl.dancenetwork.repository.TicketTypeRepository;
import com.mortl.dancenetwork.service.IStripeService;
import com.mortl.dancenetwork.service.ITicketTypeService;
import com.stripe.exception.StripeException;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class TicketTypeServiceImpl implements ITicketTypeService
{

  private final TicketTypeRepository ticketTypeRepository;

  private final IStripeService stripeService;

  public TicketTypeServiceImpl(
      TicketTypeRepository ticketTypeRepository,
      IStripeService stripeService)
  {
    this.ticketTypeRepository = ticketTypeRepository;
    this.stripeService = stripeService;
  }

  @Override
  public List<TicketType> getTicketTypesForEvent(Long eventId)
  {
    return ticketTypeRepository.findByEventId(eventId);
  }

  @Override
  @Transactional
  public List<TicketType> updateTicketTypes(List<TicketType> ticketTypesToAddOrUpdate)
  {
    //TODO test with application context test when works with jwt (see EventControllerClosedSpec)
    List<Long> ticketTypeIds = ticketTypesToAddOrUpdate.stream()
        .map(TicketType::getId)
        .toList();

    List<Long> ticketTypeIdsToDelete = ticketTypeRepository.findByEventId(
            ticketTypesToAddOrUpdate.get(0).getEvent().getId()).stream()
        .filter(oldTicketType -> !ticketTypeIds.contains(oldTicketType.getId()))
        .map(TicketType::getId)
        .toList();

    ticketTypesToAddOrUpdate = ticketTypeRepository.saveAllAndFlush(ticketTypesToAddOrUpdate);
    if (!ticketTypesToAddOrUpdate.isEmpty() && ticketTypesToAddOrUpdate.get(0).getEvent()
        .isPublished())
    {
      try
      {
        stripeService.syncTicketTypes(ticketTypesToAddOrUpdate);
      }
      catch (StripeException e)
      {
        throw new RuntimeException(e);
      }
    }
    ticketTypeRepository.deleteAllById(ticketTypeIdsToDelete);

    return ticketTypesToAddOrUpdate;
  }
}
