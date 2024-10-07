package com.mortl.dancenetwork.service;

import com.mortl.dancenetwork.entity.Ticket;
import com.mortl.dancenetwork.entity.TicketType;
import com.stripe.exception.StripeException;
import java.util.List;
import java.util.Map;

public interface IStripeService
{

  Map<String, String> createSession(List<Ticket> tickets) throws StripeException;

  Map<String, String> sessionStatus(String sessionId) throws StripeException;

  void syncTicketTypes(String url, List<TicketType> newTicketTypes) throws StripeException;

  void activateTickets(long eventId) throws StripeException;
}
