package com.mortl.dancenetwork.service.impl;

import com.mortl.dancenetwork.entity.Ticket;
import com.mortl.dancenetwork.entity.TicketOrder;
import com.mortl.dancenetwork.model.User;
import com.mortl.dancenetwork.repository.TicketOrderRepository;
import com.mortl.dancenetwork.repository.TicketRepository;
import com.mortl.dancenetwork.repository.TicketTypeRepository;
import com.mortl.dancenetwork.service.IMailService;
import com.mortl.dancenetwork.service.INewsfeedEntryService;
import com.mortl.dancenetwork.service.IPaymentService;
import com.mortl.dancenetwork.service.IPdfService;
import com.mortl.dancenetwork.service.IStripeService;
import com.mortl.dancenetwork.service.ITicketService;
import com.mortl.dancenetwork.service.IUserService;
import com.mortl.dancenetwork.util.NewsfeedFactory;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class PaymentServiceImpl implements IPaymentService
{

  private final ITicketService ticketService;

  private final IMailService mailService;

  private final IPdfService pdfService;

  private final IUserService userService;

  private final IStripeService stripeService;

  private TicketRepository ticketRepository;

  private final TicketTypeRepository ticketTypeRepository;

  private final TicketOrderRepository ticketOrderRepository;

  private final INewsfeedEntryService newsfeedEntryService;

  private final NewsfeedFactory newsfeedFactory;

  public PaymentServiceImpl(ITicketService ticketService, IMailService mailService,
      IPdfService pdfService, IUserService userService, IStripeService stripeService,
      TicketRepository ticketRepository, TicketTypeRepository ticketTypeRepository,
      TicketOrderRepository ticketOrderRepository, INewsfeedEntryService newsfeedEntryService,
      NewsfeedFactory newsfeedFactory)
  {
    this.ticketService = ticketService;
    this.mailService = mailService;
    this.pdfService = pdfService;
    this.userService = userService;
    this.stripeService = stripeService;
    this.ticketTypeRepository = ticketTypeRepository;
    this.ticketRepository = ticketRepository;
    this.ticketOrderRepository = ticketOrderRepository;
    this.newsfeedEntryService = newsfeedEntryService;
    this.newsfeedFactory = newsfeedFactory;

    Stripe.apiKey = "sk_test_51Q4KA0K0od2j0zBCF5uf3kNMMON4Pk1UlauYuN4jmvU7o3hSrZPJQMzZoP1RdkXRL0SxmHwbYV6XZ5TcYcabf5Su00T5jycRGh";
  }

  @Override
  public Map<String, String> createSession(List<Ticket> tickets)
  {
    TicketOrder ticketOrder = ticketOrderRepository.saveAndFlush(new TicketOrder());

    tickets.forEach(ticket -> setOwnerAndTicketOrder(ticket, ticketOrder));
    tickets = ticketRepository.saveAllAndFlush(tickets);

    Map<String, String> session;
    try
    {
      session = stripeService.createSession(tickets, ticketOrder.getId());
    }
    catch (StripeException e)
    {
      throw new RuntimeException(e);
    }

    session.put("order_id", String.valueOf(ticketOrder.getId()));

    return session;
  }

  private void setOwnerAndTicketOrder(Ticket ticket, TicketOrder ticketOrder)
  {
    ticket.setOwner(getUuidOfUser(userService.getCurrentUser()));
    ticket.setOrder(ticketOrder);
  }

  @Override
  public Map<String, String> sessionStatus(String sessionId, long orderId)
  {
    Map<String, String> sessionStatus;
    try
    {
      sessionStatus = stripeService.sessionStatus(sessionId);
    }
    catch (StripeException e)
    {
      throw new RuntimeException(e);
    }

    boolean paymentSuccessful = sessionStatus.get("status").equals("complete");
    if (!paymentSuccessful)
    {
      return sessionStatus;
    }

    Optional<TicketOrder> orderOptional = ticketOrderRepository.findById(orderId);
    if (orderOptional.isEmpty())
    {
      //TODO explicit order handling. Tickets are payed, but no order was found
    }
    TicketOrder ticketOrder = orderOptional.get();
    if (ticketOrder.getBuyDate() != null)
    {
      return sessionStatus;
    }
    ticketOrder.setBuyDate(LocalDateTime.now());
    ticketOrderRepository.saveAndFlush(ticketOrder);

    List<Ticket> tickets = ticketRepository.findByOrderId(orderId);
    tickets = ticketService.activateTickets(tickets);

    pdfService.createPdf(tickets);
    for (Ticket ticket : tickets)
    {
      //TODO send email to who? Stripe mail(customer_email)?
      mailService.sendEmailWithAttachment(
          //userService.getCurrentUser().get().email(),
          ticket.getEmail(),
          "Ticket for " + ticketTypeRepository.findById(tickets.get(0).getTicketType().getId())
              .get()
              .getEvent().getName(),
          "Tickets for " + tickets.size() + " person" + (tickets.size() > 1 ? "s" : "") + ".");
    }

    Optional<User> currentUser = userService.getCurrentUser();
    if (currentUser.isPresent())
    {
      newsfeedEntryService.createNewsfeedEntry(
          newsfeedFactory.createTicketsBoughtNewsfeedEntry(
              currentUser.get(),
              tickets
          )
      );
    }

    return sessionStatus;
  }

  private UUID getUuidOfUser(Optional<User> user)
  {
    if (user.isEmpty())
    {
      return null;
    }
    return user.get().getUuid();
  }
}
