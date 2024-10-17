package com.mortl.dancenetwork.controller.open;

import com.mortl.dancenetwork.dto.PaymentRequestDTO;
import com.mortl.dancenetwork.entity.Ticket;
import com.mortl.dancenetwork.mapper.TicketMapper;
import com.mortl.dancenetwork.service.IPaymentService;
import java.util.List;
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

  private final TicketMapper ticketMapper;

  public PaymentOpenController(IPaymentService paymentService, TicketMapper ticketMapper)
  {
    this.paymentService = paymentService;
    this.ticketMapper = ticketMapper;
  }

  @PostMapping("/create-checkout-session")
  public ResponseEntity<Map<String, String>> createSession(
      @RequestBody PaymentRequestDTO paymentRequestDTO)
  {
    List<Ticket> tickets = paymentRequestDTO.tickets().stream().map(ticketMapper::toEntity)
        .toList();
    return ResponseEntity.ok(paymentService.createSession(tickets));
  }

  @GetMapping("/session-status")
  public ResponseEntity<Map<String, String>> sessionStatus(
      @RequestParam(name = "session_id") String sessionId,
      @RequestParam(name = "ticket_order_id") Long ticketOrderId)
  {
    return ResponseEntity.ok(paymentService.sessionStatus(sessionId, ticketOrderId));
  }
}