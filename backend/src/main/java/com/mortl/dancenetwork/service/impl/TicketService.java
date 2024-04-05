package com.mortl.dancenetwork.service.impl;

import com.mortl.dancenetwork.dto.EventDTO;
import com.mortl.dancenetwork.dto.TicketDTO;
import com.mortl.dancenetwork.dto.PersonalTicketDataDTO;
import com.mortl.dancenetwork.mapper.EventMapper;
import com.mortl.dancenetwork.mapper.NewsfeedEntryMapper;
import com.mortl.dancenetwork.model.Ticket;
import com.mortl.dancenetwork.repository.TicketRepository;
import com.mortl.dancenetwork.repository.TicketTypeRepository;
import com.mortl.dancenetwork.service.INewsfeedEntryService;
import com.mortl.dancenetwork.service.IQrCodeService;
import com.mortl.dancenetwork.service.ITicketService;
import com.mortl.dancenetwork.service.IUserService;
import com.mortl.dancenetwork.util.NewsfeedFactory;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TicketService implements ITicketService {

  private final TicketTypeRepository ticketTypeRepository;

  private final TicketRepository ticketRepository;

  private final IUserService userService;

  private final IQrCodeService qrCodeService;

  private final INewsfeedEntryService newsfeedEntryService;

  private final NewsfeedFactory newsfeedFactory;

  private final NewsfeedEntryMapper newsfeedEntryMapper;

  @Override
  public void addTickets(Map<Long, PersonalTicketDataDTO> tickets) {
    //TODO validate that only tickets for published events can be added and write test for this
    log.info("Adding " + tickets.size() + " tickets.");
    for(Entry<Long, PersonalTicketDataDTO> ticket : tickets.entrySet()){
      PersonalTicketDataDTO personalTicketDataDTO = ticket.getValue();
      Ticket ticket2 = ticketRepository.save(Ticket.builder()
          .owner(userService.getCurrentUser().uuid())
          .gender(personalTicketDataDTO.gender())
          .ticketType(ticketTypeRepository.getReferenceById(ticket.getKey()))
          .phone(personalTicketDataDTO.phone())
          .role(personalTicketDataDTO.role())
          .lastName(personalTicketDataDTO.lastName())
          .firstName(personalTicketDataDTO.firstName())
          .address(personalTicketDataDTO.address())
          .country(personalTicketDataDTO.country())
          .phone(personalTicketDataDTO.phone())
          .email(personalTicketDataDTO.email())
          .build());

      try {
        qrCodeService.createQRImage("code"+ticket2.getId()+".png", ticket2.getId().toString());
      }
      catch (Exception e){
        log.error("Da fuq");
      }
    }

    Entry<Long, PersonalTicketDataDTO> aTicket = tickets.entrySet().stream().findAny().get();
    PersonalTicketDataDTO personalTicketDataDTO = aTicket.getValue();
    Long ticketTypeId = aTicket.getKey();
    newsfeedEntryService.createNewsfeedEntry(newsfeedEntryMapper.toDTO(
      newsfeedFactory.createTicketsBoughtNewsfeedEntry(
          userService.getCurrentUser(),
          List.of(Ticket.builder()
              .owner(userService.getCurrentUser().uuid())
              .gender(personalTicketDataDTO.gender())
              .ticketType(ticketTypeRepository.getReferenceById(ticketTypeId))
              .phone(personalTicketDataDTO.phone())
              .role(personalTicketDataDTO.role())
              .lastName(personalTicketDataDTO.lastName())
              .firstName(personalTicketDataDTO.firstName())
              .address(personalTicketDataDTO.address())
              .country(personalTicketDataDTO.country())
              .phone(personalTicketDataDTO.phone())
              .email(personalTicketDataDTO.email())
              .build())))
    );
  }

  @Override
  public List<TicketDTO> getTicketsForUser(){
    return ticketRepository.findByOwner(userService.getCurrentUser().uuid()).stream()
        .map(ticket -> new TicketDTO(
            ticket.getId(),
            ticket.getTicketType().getDescription(),
            ticket.getTicketType().getId(),
            new PersonalTicketDataDTO(
                ticket.getFirstName(),
                ticket.getLastName(),
                ticket.getAddress(),
                ticket.getCountry(),
                ticket.getEmail(),
                ticket.getPhone(),
                ticket.getRole(),
                ticket.getGender()
            ),
            eventMapper.toDTO(ticket.getTicketType().getEvent())
        ))
        .toList();
  }
  private final EventMapper eventMapper;
}
