package com.mortl.dancenetwork.controller;

import com.mortl.dancenetwork.dto.PaymentRequestDTO;
import com.mortl.dancenetwork.service.IPaymentService;
import com.mortl.dancenetwork.service.ITicketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payments")
@Slf4j
@RequiredArgsConstructor
public class PaymentController {

  private final IPaymentService paymentService;

  private final ITicketService ticketService;

  @PostMapping
  public ResponseEntity postPaymentInfo(@RequestBody PaymentRequestDTO paymentRequestDTO) {
    paymentService.doPayment(paymentRequestDTO);

    ticketService.addTickets(paymentRequestDTO.personalData());

    return ResponseEntity.ok().build();
  }
}