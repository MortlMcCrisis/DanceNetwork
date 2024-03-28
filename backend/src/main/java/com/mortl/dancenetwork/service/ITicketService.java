package com.mortl.dancenetwork.service;

import com.mortl.dancenetwork.dto.TicketDTO;
import com.mortl.dancenetwork.dto.PersonalTicketDataDTO;
import java.util.List;
import java.util.Map;

public interface ITicketService {

  void addTickets(Map<Long, PersonalTicketDataDTO> personalTicketDataDTOS);

  List<TicketDTO> getTicketsForUser();
}
