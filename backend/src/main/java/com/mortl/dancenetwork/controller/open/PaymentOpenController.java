package com.mortl.dancenetwork.controller.open;

import com.mortl.dancenetwork.dto.PaymentRequestDTO;
import com.mortl.dancenetwork.service.IPaymentService;
import com.mortl.dancenetwork.service.ITicketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/open/v1/payments")
@Slf4j
@RequiredArgsConstructor
public class PaymentOpenController {

  private final IPaymentService paymentService;

  private final ITicketService ticketService;

  @PostMapping
  public ResponseEntity<Void> newPayment(@RequestBody PaymentRequestDTO tickets) {
    paymentService.doPayment(tickets);

    ticketService.addTickets(tickets.tickets());

    return ResponseEntity.ok().build();
  }
}