package com.mortl.dancenetwork.service;

import com.mortl.dancenetwork.dto.TicketDTO;
import com.mortl.dancenetwork.dto.TicketInfoDTO;
import com.mortl.dancenetwork.entity.Ticket;
import java.util.List;

public interface ITicketService {

  List<Ticket> addTickets(List<TicketDTO> tickets);

  List<TicketInfoDTO> getTicketInfosForUser();

  List<TicketInfoDTO> getTicketInfosForEvent(long eventId);
}
