package com.mortl.dancenetwork.service.impl;

import com.mortl.dancenetwork.dto.PaymentRequestDTO;
import com.mortl.dancenetwork.dto.TicketDTO;
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
import com.stripe.model.checkout.Session;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class PaymentServiceImpl implements IPaymentService
{

  //TODO make configurable to be dependent to environment
  private static final String DOMAIN = "http://localhost:3000";

  private static final String PRICE_ID = "price_1Q4LKpK0od2j0zBCtJVHWfIA";

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
//    SessionCreateParams params =
//        SessionCreateParams.builder()
//            .setUiMode(SessionCreateParams.UiMode.EMBEDDED)
//            .setMode(SessionCreateParams.Mode.PAYMENT)
//            // TODO create frontend url where I can redirect to
//            .setReturnUrl(DOMAIN
//                + "/dashboards/app/dance-event-detail/3/dance-buy-ticket-invoice?session_id={CHECKOUT_SESSION_ID}")
//            .addLineItem(
//                SessionCreateParams.LineItem.builder()
//                    .setQuantity(1L)
//                    // Provide the exact Price ID (for example, pr_1234) of the product you want to sell
//                    .setPrice(PRICE_ID)
//                    .build())
//            .build();
//
//    Session session;
//    try
//    {
//      session = Session.create(params);
//    } catch (StripeException e)
//    {
//      throw new RuntimeException(e);
//    }
//
//    Map<String, String> map = new HashMap<>();
//    map.put("clientSecret",
//        session.getRawJsonObject().getAsJsonPrimitive("client_secret").getAsString());
//
//    return map;

    List<Ticket> tickets = ticketRequest.tickets().stream().map(ticketMapper::toEntity).toList();

    //TODO add tickets and status of payment to database

    try
    {
      return stripeService.createSession(tickets);
    } catch (StripeException e)
    {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Map<String, String> sessionStatus(String sessionId)
  {
    Session session;
    try
    {
      session = Session.retrieve(sessionId);
    } catch (StripeException e)
    {
      throw new RuntimeException(e);
    }

    Map<String, String> map = new HashMap();
    map.put("status", session.getRawJsonObject().getAsJsonPrimitive("status").getAsString());
    map.put("customer_email",
        session.getRawJsonObject().getAsJsonObject("customer_details").getAsJsonPrimitive("email")
            .getAsString());

    return map;
  }

  @Override
  public void doPayment(PaymentRequestDTO paymentRequestDTO)
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
  }
}
