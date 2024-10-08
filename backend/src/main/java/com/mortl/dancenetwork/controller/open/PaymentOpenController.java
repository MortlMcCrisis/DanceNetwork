package com.mortl.dancenetwork.controller.open;

import com.mortl.dancenetwork.dto.PaymentRequestDTO;
import com.mortl.dancenetwork.service.IPaymentService;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/open/v1/payments")
public class PaymentOpenController
{

  private final IPaymentService paymentService;

  public PaymentOpenController(IPaymentService paymentService)
  {
    this.paymentService = paymentService;
  }

  @PostMapping("/create-checkout-session")
  public ResponseEntity<Map<String, String>> createSession(@RequestBody PaymentRequestDTO tickets)
  {
    return ResponseEntity.ok(paymentService.createSession(tickets));
  }

  @GetMapping("/session-status")
  public ResponseEntity<Map<String, String>> sessionStatus(@RequestParam String session_id)
  {
    return ResponseEntity.ok(paymentService.sessionStatus(session_id));
  }
}