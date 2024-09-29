package com.mortl.dancenetwork.service.impl;

import com.mortl.dancenetwork.dto.TicketTypeDTO;
import com.mortl.dancenetwork.entity.TicketType;
import com.mortl.dancenetwork.mapper.TicketTypeMapper;
import com.mortl.dancenetwork.repository.TicketTypeRepository;
import com.mortl.dancenetwork.service.ITicketTypeService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TicketTypeServiceImpl implements ITicketTypeService
{

  private static final Logger log = LoggerFactory.getLogger(TicketTypeServiceImpl.class);

  private final TicketTypeRepository ticketTypeRepository;

  private final TicketTypeMapper ticketTypeMapper;

  public TicketTypeServiceImpl(TicketTypeRepository ticketTypeRepository,
      TicketTypeMapper ticketTypeMapper)
  {
    this.ticketTypeRepository = ticketTypeRepository;
    this.ticketTypeMapper = ticketTypeMapper;
  }


  @Override
  public List<TicketTypeDTO> getTicketTypesForEvent(Long eventId)
  {
    return ticketTypeRepository.findByEventId(eventId).stream()
        .map(ticket -> ticketTypeMapper.toDTO(ticket))
        .toList();
  }

  @Override
  public void addTicketType(TicketTypeDTO ticketTypeDTO)
  {
    TicketType ticketType = ticketTypeMapper.toModel(ticketTypeDTO);
    log.debug("Saving ticket: " + ticketType);
    ticketTypeRepository.saveAndFlush(ticketType);
  }

  @Override
  public void updateTicketTypes(List<TicketTypeDTO> ticketTypeDTOs)
  {
    //TODO test with application context test when works with jwt (see EventControllerClosedSpec)
    List<Long> newTicketIds = ticketTypeDTOs.stream()
        .map(TicketTypeDTO::id)
        .toList();

    List<Long> ticketTypeIdsToDelete = ticketTypeRepository.findByEventId(
            ticketTypeDTOs.get(0).eventId()).stream()
        .filter(oldTicketType -> !newTicketIds.contains(oldTicketType.getId()))
        .map(TicketType::getId)
        .toList();
    List<TicketType> ticketTypesToAdd = ticketTypeDTOs.stream()
        .map(ticketTypeDTO -> ticketTypeMapper.toModel(ticketTypeDTO))
        .toList();

    ticketTypeRepository.saveAllAndFlush(ticketTypesToAdd);
    ticketTypeRepository.deleteAllById(ticketTypeIdsToDelete);
  }
}
