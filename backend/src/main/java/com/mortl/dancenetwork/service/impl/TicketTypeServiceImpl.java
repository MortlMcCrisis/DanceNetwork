package com.mortl.dancenetwork.service.impl;

import com.mortl.dancenetwork.dto.TicketTypeDTO;
import com.mortl.dancenetwork.mapper.TicketTypeMapper;
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

  public List<TicketTypeDTO> getTicketTypesForEvent(Long eventId){
    return ticketTypeRepository.getTicketTypesForEvent(eventId).stream()
        .map(ticket -> ticketTypeMapper.toDTO(ticket))
        .toList();
  }

  @Override
  public void addTicketType(TicketTypeDTO ticketTypeDTO) {;
    ticketTypeRepository.saveAndFlush(ticketTypeMapper.toModel(ticketTypeDTO));
  }
}
