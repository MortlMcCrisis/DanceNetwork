package com.mortl.dancenetwork.service;

import com.mortl.dancenetwork.entity.TicketType;
import java.util.List;

public interface ITicketTypeService
{

  List<TicketType> getTicketTypesForEvent(Long eventId);

  List<TicketType> updateTicketTypes(long eventId, List<TicketType> ticketType);
}
