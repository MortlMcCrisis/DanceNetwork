package com.mortl.dancenetwork.service.impl;

import com.mortl.dancenetwork.dto.TicketDTO;
import com.mortl.dancenetwork.dto.TicketInfoDTO;
import com.mortl.dancenetwork.entity.Ticket;
import com.mortl.dancenetwork.mapper.EventMapper;
import com.mortl.dancenetwork.mapper.NewsfeedEntryMapper;
import com.mortl.dancenetwork.mapper.TicketMapper;
import com.mortl.dancenetwork.mapper.TicketTypeMapper;
import com.mortl.dancenetwork.model.User;
import com.mortl.dancenetwork.repository.TicketRepository;
import com.mortl.dancenetwork.service.INewsfeedEntryService;
import com.mortl.dancenetwork.service.IQrCodeService;
import com.mortl.dancenetwork.service.ITicketService;
import com.mortl.dancenetwork.service.IUserService;
import com.mortl.dancenetwork.util.NewsfeedFactory;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TicketServiceImpl implements ITicketService {

  private static final Logger log = LoggerFactory.getLogger(TicketServiceImpl.class);

  private final TicketRepository ticketRepository;

  private final IUserService userService;

  private final IQrCodeService qrCodeService;

  private final INewsfeedEntryService newsfeedEntryService;

  private final NewsfeedFactory newsfeedFactory;

  private final NewsfeedEntryMapper newsfeedEntryMapper;

  private final TicketMapper ticketMapper;

  private final EventMapper eventMapper;

  private final TicketTypeMapper ticketTypeMapper;

  public TicketServiceImpl(TicketRepository ticketRepository, IUserService userService,
      IQrCodeService qrCodeService, INewsfeedEntryService newsfeedEntryService,
      NewsfeedFactory newsfeedFactory, NewsfeedEntryMapper newsfeedEntryMapper,
      TicketMapper ticketMapper, EventMapper eventMapper, TicketTypeMapper ticketTypeMapper) {
    this.ticketRepository = ticketRepository;
    this.userService = userService;
    this.qrCodeService = qrCodeService;
    this.newsfeedEntryService = newsfeedEntryService;
    this.newsfeedFactory = newsfeedFactory;
    this.newsfeedEntryMapper = newsfeedEntryMapper;
    this.ticketMapper = ticketMapper;
    this.eventMapper = eventMapper;
    this.ticketTypeMapper = ticketTypeMapper;
  }

  @Override
  public List<Ticket> addTickets(List<TicketDTO> tickets) {
    tickets.forEach(ticket -> log.info("new ticket: {}", ticket.ticketTypeId()));
    Optional<User> currentUser = userService.getCurrentUser();
    List<Ticket> t = tickets.stream()
        .map(ticketMapper::toEntity)
        .peek(ticket -> ticket.setOwner(getUuidOfUser(currentUser)))
        .peek(ticket -> ticket.setBuyDate(LocalDateTime.now()))
        .toList();

    List<Ticket> savedTickets = ticketRepository.saveAllAndFlush(
        tickets.stream()
            .map(ticketMapper::toEntity)
            .peek(ticket -> ticket.setOwner(getUuidOfUser(currentUser)))
            .peek(ticket -> ticket.setBuyDate(LocalDateTime.now()))
            .toList());

    for(Ticket ticket : savedTickets) {
      try {
        qrCodeService.createQRImage("code" + ticket.getId() + ".png", ticket.getId().toString());
      } catch (Exception e) {
        log.error("Da fuq");
      }
    }

    if(currentUser.isPresent()) {
      newsfeedEntryService.createNewsfeedEntry(newsfeedEntryMapper.toDTO(
              newsfeedFactory.createTicketsBoughtNewsfeedEntry(
                  currentUser.get(),
                  savedTickets
              )
          )
      );
    }

    return savedTickets;
  }

  private UUID getUuidOfUser(Optional<User> user){
    if(user.isEmpty()){
      return null;
    }
    return user.get().getUuid();
  }

  @Override
  public List<TicketInfoDTO> getTicketInfosForUser(){
    return ticketRepository.findByOwnerOrderByEventStartDateAsc(userService.getNonNullCurrentUser().getUuid()).stream()
        .map(this::createTicketInfoDTO)
        .toList();
  }

  @Override
  public List<TicketInfoDTO> getTicketInfosForEvent(long eventId) {
    return ticketRepository.findByEventId(eventId).stream()
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