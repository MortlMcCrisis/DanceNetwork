package com.mortl.dancenetwork.service.impl;

import com.mortl.dancenetwork.dto.TicketDTO;
import com.mortl.dancenetwork.dto.TicketInfoDTO;
import com.mortl.dancenetwork.mapper.EventMapper;
import com.mortl.dancenetwork.mapper.NewsfeedEntryMapper;
import com.mortl.dancenetwork.mapper.TicketMapper;
import com.mortl.dancenetwork.mapper.TicketTypeMapper;
import com.mortl.dancenetwork.model.Ticket;
import com.mortl.dancenetwork.repository.TicketRepository;
import com.mortl.dancenetwork.service.INewsfeedEntryService;
import com.mortl.dancenetwork.service.IQrCodeService;
import com.mortl.dancenetwork.service.ITicketService;
import com.mortl.dancenetwork.service.IUserService;
import com.mortl.dancenetwork.util.NewsfeedFactory;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TicketService implements ITicketService {

  private final TicketRepository ticketRepository;

  private final IUserService userService;

  private final IQrCodeService qrCodeService;

  private final INewsfeedEntryService newsfeedEntryService;

  private final NewsfeedFactory newsfeedFactory;

  private final NewsfeedEntryMapper newsfeedEntryMapper;

  private final TicketMapper ticketMapper;

  private final EventMapper eventMapper;

  private final TicketTypeMapper ticketTypeMapper;

  @Override
  public void addTickets(List<TicketDTO> tickets) {
    tickets.forEach(ticket -> log.info("new tickets: {}", ticket.ticketTypeId()));
    List<Ticket> savedTickets = ticketRepository.saveAllAndFlush(
        tickets.stream()
            .map(ticketMapper::toModel)
            .peek(ticket -> ticket.setOwner(userService.getCurrentUser().uuid()))
            .toList());

    for(Ticket ticket : savedTickets) {
      try {
        qrCodeService.createQRImage("code" + ticket.getId() + ".png", ticket.getId().toString());
      } catch (Exception e) {
        log.error("Da fuq");
      }
    }

    newsfeedEntryService.createNewsfeedEntry(newsfeedEntryMapper.toDTO(
        newsfeedFactory.createTicketsBoughtNewsfeedEntry(
            userService.getCurrentUser(),
            savedTickets
        )
      )
    );
  }

  @Override
  public List<TicketInfoDTO> getTicketInfosForUser(){
    //TODO order by event start date in repository
    return ticketRepository.findByOwner(userService.getCurrentUser().uuid()).stream()
        .map(this::createTicketInfoDTO)
        .toList();
  }

  private TicketInfoDTO createTicketInfoDTO(Ticket ticket){
    return new TicketInfoDTO(
        ticketMapper.toDTO(ticket),
        ticketTypeMapper.toDTO(ticket.getTicketType()),
        eventMapper.toDTO(ticket.getTicketType().getEvent()));
  }
}
