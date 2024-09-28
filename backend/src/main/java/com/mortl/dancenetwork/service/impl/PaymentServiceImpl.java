package com.mortl.dancenetwork.service.impl;

import com.mortl.dancenetwork.dto.PaymentRequestDTO;
import com.mortl.dancenetwork.dto.TicketDTO;
import com.mortl.dancenetwork.entity.Ticket;
import com.mortl.dancenetwork.repository.TicketTypeRepository;
import com.mortl.dancenetwork.service.IMailService;
import com.mortl.dancenetwork.service.IPaymentService;
import com.mortl.dancenetwork.service.IPdfService;
import com.mortl.dancenetwork.service.ITicketService;
import com.mortl.dancenetwork.service.IUserService;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class PaymentServiceImpl implements IPaymentService {

  private final ITicketService ticketService;

  private final IMailService mailService;

  private final IPdfService pdfService;

  private final IUserService userService;

  private final TicketTypeRepository ticketTypeRepository;

  public PaymentServiceImpl(ITicketService ticketService, IMailService mailService,
      IPdfService pdfService, IUserService userService, TicketTypeRepository ticketTypeRepository) {
    this.ticketService = ticketService;
    this.mailService = mailService;
    this.pdfService = pdfService;
    this.userService = userService;
    this.ticketTypeRepository = ticketTypeRepository;
  }

  @Override
  public void doPayment(PaymentRequestDTO paymentRequestDTO) {

    List<TicketDTO> tickets = paymentRequestDTO.tickets();

    List<Ticket> savedTickets = ticketService.addTickets(tickets);
    pdfService.createPdf(savedTickets);
    for(Ticket ticket : savedTickets) {
      mailService.sendEmailWithAttachment(
          //userService.getCurrentUser().get().email(),
          ticket.getEmail(),
          "Ticket for " + ticketTypeRepository.findById(tickets.get(0).ticketTypeId()).get()
              .getEvent().getName(),
          "Tickets for " + tickets.size() + " person" + (tickets.size() > 1 ? "s" : "") + ".");
    }
  }
}
