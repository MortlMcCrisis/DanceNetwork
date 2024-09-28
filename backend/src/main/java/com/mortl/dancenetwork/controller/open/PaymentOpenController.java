package com.mortl.dancenetwork.controller.open;

import com.mortl.dancenetwork.dto.PaymentRequestDTO;
import com.mortl.dancenetwork.service.IPaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/open/v1/payments")
public class PaymentOpenController {

  private final IPaymentService paymentService;

  public PaymentOpenController(IPaymentService paymentService) {
    this.paymentService = paymentService;
  }

  @PostMapping
  public ResponseEntity<Void> newPayment(@RequestBody PaymentRequestDTO tickets) {
    paymentService.doPayment(tickets);

    return ResponseEntity.ok().build();
  }
}