package com.mortl.dancenetwork.service.impl;

import com.mortl.dancenetwork.dto.TicketTypeDTO;
import com.mortl.dancenetwork.model.Event;
import com.mortl.dancenetwork.repository.EventRepository;
import com.mortl.dancenetwork.repository.TicketTypeRepository;
import com.mortl.dancenetwork.service.ITicketTypeService;
import java.util.List;
import javax.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TicketTypeServiceImpl implements ITicketTypeService {

  private final TicketTypeRepository ticketTypeRepository;

  private final EventRepository eventRepository;

  public List<TicketTypeDTO> getTicketTypesForEvent(Long eventId){
    return ticketTypeRepository.getTicketTypesForEvent(eventId).stream()
        .map(TicketTypeDTO::fromModel)
        .toList();
  }

  @Override
  public void addTicketType(TicketTypeDTO ticketTypeDTO) {
    Event event = eventRepository.findById(ticketTypeDTO.eventId())
        .orElseThrow(() -> new NotFoundException());
    ticketTypeRepository.saveAndFlush(ticketTypeDTO.toModel(event));
  }
}
