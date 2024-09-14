package com.mortl.dancenetwork.service.impl;

import com.mortl.dancenetwork.dto.TicketTypeDTO;
import com.mortl.dancenetwork.mapper.TicketTypeMapper;
import com.mortl.dancenetwork.entity.TicketType;
import com.mortl.dancenetwork.repository.TicketTypeRepository;
import com.mortl.dancenetwork.service.ITicketTypeService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TicketTypeServiceImpl implements ITicketTypeService {

  private final TicketTypeRepository ticketTypeRepository;

  private final TicketTypeMapper ticketTypeMapper;

  @Override
  public List<TicketTypeDTO> getTicketTypesForEvent(Long eventId){
    return ticketTypeRepository.findByEventId(eventId).stream()
        .map(ticket -> ticketTypeMapper.toDTO(ticket))
        .toList();
  }

  @Override
  public void addTicketType(TicketTypeDTO ticketTypeDTO) {;
    ticketTypeRepository.saveAndFlush(ticketTypeMapper.toModel(ticketTypeDTO));
  }

  @Override
  public void updateTicketTypes(List<TicketTypeDTO> ticketTypeDTOs) {
    //TODO test with application context test when works with jwt (see EventControllerClosedSpec)
    List<Long> newTicketIds = ticketTypeDTOs.stream()
        .map(TicketTypeDTO::id)
        .toList();
    List<Long> ticketTypeIdsToDelete = ticketTypeRepository.findAll().stream()
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
