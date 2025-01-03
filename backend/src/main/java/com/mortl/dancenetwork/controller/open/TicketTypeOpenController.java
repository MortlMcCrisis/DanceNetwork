package com.mortl.dancenetwork.controller.open;

import com.mortl.dancenetwork.dto.TicketTypeDTO;
import com.mortl.dancenetwork.entity.TicketType;
import com.mortl.dancenetwork.mapper.TicketTypeMapper;
import com.mortl.dancenetwork.service.ITicketTypeService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/open/v1/ticket-types")
public class TicketTypeOpenController
{

  private static final Logger log = LoggerFactory.getLogger(TicketTypeOpenController.class);

  private final ITicketTypeService ticketTypeService;

  private final TicketTypeMapper ticketTypeMapper;

  public TicketTypeOpenController(ITicketTypeService ticketTypeService,
      TicketTypeMapper ticketTypeMapper)
  {
    this.ticketTypeService = ticketTypeService;
    this.ticketTypeMapper = ticketTypeMapper;
  }

  @GetMapping
  public ResponseEntity<List<TicketTypeDTO>> getTicketTypesForEvent(@RequestParam long eventId)
  {
    log.info("Fetching ticket types for event  " + eventId);

    List<TicketType> ticketTypesForEvent = ticketTypeService.getTicketTypesForEvent(eventId);
    return ResponseEntity.ok(ticketTypesForEvent.stream().map(ticketTypeMapper::toDTO).toList());
  }
}
