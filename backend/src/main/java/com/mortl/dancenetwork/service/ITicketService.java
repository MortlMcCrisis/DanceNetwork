package com.mortl.dancenetwork.service;

import com.mortl.dancenetwork.entity.Ticket;
import java.util.List;

public interface ITicketService
{

  List<Ticket> activateTickets(List<Ticket> tickets);

  List<Ticket> getTicketsForUser();

  List<Ticket> getTicketsEvent(long eventId);
}
