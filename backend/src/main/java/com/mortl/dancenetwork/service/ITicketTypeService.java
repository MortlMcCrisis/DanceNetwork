package com.mortl.dancenetwork.service;

import com.mortl.dancenetwork.dto.TicketTypeDTO;
import java.util.List;

public interface ITicketTypeService
{

  List<TicketTypeDTO> getTicketTypesForEvent(Long eventId);

  List<TicketTypeDTO> updateTicketTypes(List<TicketTypeDTO> ticketTypeDTO);
}
