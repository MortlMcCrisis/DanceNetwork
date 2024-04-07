package com.mortl.dancenetwork.service;

import com.mortl.dancenetwork.dto.TicketDTO;
import com.mortl.dancenetwork.dto.TicketInfoDTO;
import java.util.List;

public interface ITicketService {

  void addTickets(List<TicketDTO> tickets);

  List<TicketInfoDTO> getTicketInfosForUser();
}
