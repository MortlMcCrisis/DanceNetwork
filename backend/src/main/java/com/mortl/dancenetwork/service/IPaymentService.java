package com.mortl.dancenetwork.service;

import com.mortl.dancenetwork.dto.PaymentRequestDTO;
import java.util.Map;

public interface IPaymentService
{

  Map<String, String> createSession(PaymentRequestDTO ticketRequest);

  Map<String, String> sessionStatus(String sessionId);
}
