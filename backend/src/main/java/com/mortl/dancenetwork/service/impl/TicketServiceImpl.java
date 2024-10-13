package com.mortl.dancenetwork.service.impl;

import com.mortl.dancenetwork.entity.Ticket;
import com.mortl.dancenetwork.repository.TicketRepository;
import com.mortl.dancenetwork.service.IQrCodeService;
import com.mortl.dancenetwork.service.ITicketService;
import com.mortl.dancenetwork.service.IUserService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TicketServiceImpl implements ITicketService
{

  private static final Logger log = LoggerFactory.getLogger(TicketServiceImpl.class);

  private final TicketRepository ticketRepository;

  private final IUserService userService;

  private final IQrCodeService qrCodeService;

  public TicketServiceImpl(TicketRepository ticketRepository, IUserService userService,
      IQrCodeService qrCodeService)
  {
    this.ticketRepository = ticketRepository;
    this.userService = userService;
    this.qrCodeService = qrCodeService;
  }

  @Override
  public List<Ticket> activateTickets(List<Ticket> tickets)
  {
    tickets.forEach(ticket -> log.info("new ticket: {}", ticket.getTicketType().getId()));

    for (Ticket ticket : tickets)
    {
      try
      {
        qrCodeService.createQRImage("code" + ticket.getId() + ".png", ticket.getId().toString());
      }
      catch (Exception e)
      {
        log.error("Da fuq");
      }
    }

    return tickets;
  }

  @Override
  public List<Ticket> getTicketsForUser()
  {
    return ticketRepository.findByOwnerOrderByEventStartDateAsc(
        userService.getNonNullCurrentUser().getUuid());
  }

  @Override
  public List<Ticket> getTicketsEvent(long eventId)
  {
    return ticketRepository.findByEventId(eventId);
  }
}
