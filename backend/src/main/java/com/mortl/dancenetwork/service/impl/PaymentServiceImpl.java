package com.mortl.dancenetwork.service.impl;

import com.mortl.dancenetwork.dto.PaymentRequestDTO;
import com.mortl.dancenetwork.entity.Ticket;
import com.mortl.dancenetwork.mapper.TicketMapper;
import com.mortl.dancenetwork.repository.TicketTypeRepository;
import com.mortl.dancenetwork.service.IMailService;
import com.mortl.dancenetwork.service.IPaymentService;
import com.mortl.dancenetwork.service.IPdfService;
import com.mortl.dancenetwork.service.IStripeService;
import com.mortl.dancenetwork.service.ITicketService;
import com.mortl.dancenetwork.service.IUserService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class PaymentServiceImpl implements IPaymentService
{

  private final ITicketService ticketService;

  private final IMailService mailService;

  private final IPdfService pdfService;

  private final IUserService userService;

  private final IStripeService stripeService;

  private final TicketTypeRepository ticketTypeRepository;

  private final TicketMapper ticketMapper;

  public PaymentServiceImpl(ITicketService ticketService, IMailService mailService,
      IPdfService pdfService, IUserService userService, IStripeService stripeService,
      TicketTypeRepository ticketTypeRepository, TicketMapper ticketMapper)
  {
    this.ticketService = ticketService;
    this.mailService = mailService;
    this.pdfService = pdfService;
    this.userService = userService;
    this.stripeService = stripeService;
    this.ticketTypeRepository = ticketTypeRepository;
    this.ticketMapper = ticketMapper;

    Stripe.apiKey = "sk_test_51Q4KA0K0od2j0zBCF5uf3kNMMON4Pk1UlauYuN4jmvU7o3hSrZPJQMzZoP1RdkXRL0SxmHwbYV6XZ5TcYcabf5Su00T5jycRGh";
  }

  @Override
  public Map<String, String> createSession(PaymentRequestDTO ticketRequest)
  {
    List<Ticket> tickets = ticketRequest.tickets().stream().map(ticketMapper::toEntity).toList();

    //TODO add tickets and status of payment to database

    try
    {
      return stripeService.createSession(tickets);
    }
    catch (StripeException e)
    {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Map<String, String> sessionStatus(String sessionId)
  {
    try
    {
      return stripeService.sessionStatus(sessionId);
    }
    catch (StripeException e)
    {
      throw new RuntimeException(e);
    }
  }

  //TODO move to stripe logic
  /*private void doPayment(PaymentRequestDTO paymentRequestDTO)
  {
    List<TicketDTO> tickets = paymentRequestDTO.tickets();

    List<Ticket> savedTickets = ticketService.addTickets(tickets);
    pdfService.createPdf(savedTickets);
    for (Ticket ticket : savedTickets)
    {
      mailService.sendEmailWithAttachment(
          //userService.getCurrentUser().get().email(),
          ticket.getEmail(),
          "Ticket for " + ticketTypeRepository.findById(tickets.get(0).ticketTypeId()).get()
              .getEvent().getName(),
          "Tickets for " + tickets.size() + " person" + (tickets.size() > 1 ? "s" : "") + ".");
    }
  }*/
}
