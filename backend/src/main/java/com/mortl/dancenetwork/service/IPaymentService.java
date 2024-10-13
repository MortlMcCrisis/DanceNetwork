package com.mortl.dancenetwork.service;

import com.mortl.dancenetwork.entity.Ticket;
import java.util.List;
import java.util.Map;

public interface IPaymentService
{

  Map<String, String> createSession(List<Ticket> tickets);

  Map<String, String> sessionStatus(String sessionId, long orderId);
}
