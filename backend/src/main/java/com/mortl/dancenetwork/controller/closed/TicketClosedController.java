package com.mortl.dancenetwork.controller.closed;

import com.mortl.dancenetwork.dto.TicketInfoDTO;
import com.mortl.dancenetwork.entity.Ticket;
import com.mortl.dancenetwork.mapper.EventMapper;
import com.mortl.dancenetwork.mapper.TicketMapper;
import com.mortl.dancenetwork.mapper.TicketTypeMapper;
import com.mortl.dancenetwork.service.ITicketService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/closed/v1/tickets")
public class TicketClosedController
{

  private final ITicketService ticketService;

  private final TicketMapper ticketMapper;

  private final TicketTypeMapper ticketTypeMapper;

  private final EventMapper eventMapper;

  public TicketClosedController(ITicketService ticketService, TicketMapper ticketMapper,
      TicketTypeMapper ticketTypeMapper, EventMapper eventMapper)
  {
    this.ticketService = ticketService;
    this.ticketMapper = ticketMapper;
    this.ticketTypeMapper = ticketTypeMapper;
    this.eventMapper = eventMapper;
  }

  @GetMapping("/infos")
  public ResponseEntity<List<TicketInfoDTO>> getTicketInfosForUser()
  {
    List<Ticket> ticketsForUser = ticketService.getTicketsForUser();
    return ResponseEntity.ok(ticketsForUser.stream().map(this::createTicketInfoDTO).toList());
  }

  @GetMapping
  public ResponseEntity<List<TicketInfoDTO>> getTicketInfosForEvent(@RequestParam long eventId)
  {
    List<Ticket> ticketsEvent = ticketService.getTicketsEvent(eventId);
    return ResponseEntity.ok(ticketsEvent.stream().map(this::createTicketInfoDTO).toList());
  }

  private TicketInfoDTO createTicketInfoDTO(Ticket ticket)
  {
    return new TicketInfoDTO(
        ticketMapper.toDTO(ticket),
        ticketTypeMapper.toDTO(ticket.getTicketType()),
        eventMapper.toDTO(ticket.getTicketType().getEvent()));
  }
}
