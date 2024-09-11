package com.mortl.dancenetwork.bot;

import com.mortl.dancenetwork.entity.User;
import com.mortl.dancenetwork.model.Event;
import com.mortl.dancenetwork.model.NewsfeedEntry;
import com.mortl.dancenetwork.model.NewsfeedEntryType;
import com.mortl.dancenetwork.model.TicketType;
import com.mortl.dancenetwork.repository.EventRepository;
import com.mortl.dancenetwork.repository.NewsfeedEntryRepository;
import com.mortl.dancenetwork.repository.TicketTypeRepository;
import com.mortl.dancenetwork.service.IUserService;
import com.mortl.dancenetwork.util.NewsfeedFactory;
import de.svenjacobs.loremipsum.LoremIpsum;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Profile("!test")
public class ShowDataCreator {

  private NewsfeedEntryRepository newsfeedEntryRepository;

  private EventRepository eventRepository;

  private NewsfeedFactory newsfeedFactory;

  private TicketTypeRepository ticketTypeRepository;

  private LoremIpsum loremIpsum;

  private List<User> users;

  public ShowDataCreator(
      IUserService userService,
      EventRepository eventRepository,
      NewsfeedEntryRepository newsfeedEntryRepository,
      TicketTypeRepository ticketTypeRepository,
      NewsfeedFactory newsfeedFactory){
    this.newsfeedEntryRepository = newsfeedEntryRepository;
    this.eventRepository = eventRepository;
    this.newsfeedFactory = newsfeedFactory;
    this.ticketTypeRepository = ticketTypeRepository;
    loremIpsum = new LoremIpsum();
    users = userService.getAllUsers();
  }

  @Scheduled(fixedRate = 24000000)
  public void createNewsfeedEntries() {
    users.stream().forEach(user -> createRandomNewsfeed(user));
  }

  @Scheduled(fixedDelay=Long.MAX_VALUE)
  public void createEvents(){
    User bachaturoUser = users.get(new Random().nextInt(0, users.size()));
    Event bachaturo = Event.builder()
        .creator(bachaturoUser.uuid())
        .startDate(LocalDate.of(2024, 8, 16))
        .startTime(LocalTime.NOON)
        .endDate(LocalDate.of(2024, 8, 18))
        .name("Bachaturo")
        .location("plac Sławika i Antalla 1, 40-163 Katowice, Poland")
        .website("https://bachaturo.com/")
        .email("bachaturo@info.pl")
        .published(true)
        .profileImage("/upload/2c22e8eef931ef9a428195d8f1e55573/Bachaturo.png")
        .build();
    createEvent(bachaturo, bachaturoUser);

    User bachatationUser = users.get(new Random().nextInt(0, users.size()));
    Event bachatation = Event.builder()
        .creator(bachatationUser.uuid())
        .startDate(LocalDate.of(2024, 2, 22))
        .startTime(LocalTime.NOON)
        .endDate(LocalDate.of(2024, 2, 25))
        .name("Bachatation")
        .location("Son Latino Studios, Gablonzer Str. 9, 76185 Karlsruhe")
        .website("http://www.bachatation.de/")
        .email("events@sonlatino.de")
        .published(true)
        .profileImage("/upload/2c22e8eef931ef9a428195d8f1e55573/Bachatation.png")
        .build();
    createEvent(bachatation, bachatationUser);

    User backataRoyalUser = users.get(new Random().nextInt(0, users.size()));
    Event bachataRoyal = Event.builder()
        .creator(backataRoyalUser.uuid())
        .startDate(LocalDate.of(2024, 10, 25))
        .startTime(LocalTime.NOON)
        .endDate(LocalDate.of(2024, 10, 27))
        .name("Bachata Zouk Royals")
        .location("Freiburg, Kaiser Joseph Straße 268 79098 - Freiburg Baden-Württemberg")
        .website("https://www.goandance.com/en/events/6476-bachata-zouk-royals-2024")
        .email("florence.vouriot@gmx.de")
        .published(true)
        .profileImage("/upload/2c22e8eef931ef9a428195d8f1e55573/BachataZouk.png")
        .build();
    createEvent(bachataRoyal, backataRoyalUser);

    User bachatologyUser = users.get(new Random().nextInt(0, users.size()));
    Event bachatology = Event.builder()
        .creator(backataRoyalUser.uuid())
        .startDate(LocalDate.of(2024, 9, 27))
        .startTime(LocalTime.NOON)
        .endDate(LocalDate.of(2024, 9, 28))
        .name("Bachatology")
        .location("Karlsruhe, Hans-Sachs-Straße 8, 76133")
        .website("https://www.tickettailor.com/events/socialdancevip/1218659?fbclid=IwZXh0bgNhZW0CMTEAAR3BWpgiEoj5x8tOD7WnCdVqaebNOKNLqwGtW5ghvZBw3cSvt5XwBYsJY6A_aem_Xdvk1biN-Ez2WXS4MEUxzg&sfnsn=scwspwa")
        .published(true)
        .profileImage("/upload/2c22e8eef931ef9a428195d8f1e55573/Bachatology.png")
        .build();
    createEvent(bachatology, bachatologyUser);
  }

  private void createEvent(Event event, User user){
    event = eventRepository.saveAndFlush( event );
    newsfeedEntryRepository.saveAndFlush(newsfeedFactory.createEventPublishedNewsfeedEntry(user, event));
    createTicketTypesForEvent(event);
  }

  private void createTicketTypesForEvent(Event event)
  {
    int amountOfTickets = new Random().nextInt(2, 10);
    for(int i=0; i<amountOfTickets; i++) {
      ticketTypeRepository.saveAndFlush(TicketType.builder()
          .price(BigDecimal.valueOf(new Random().nextFloat(15, 250))
              .setScale(2, BigDecimal.ROUND_HALF_DOWN)
              .floatValue())
          .description(getRandomLoremIpsum(5, 20))
          .name(loremIpsum.getWords(new Random().nextInt(1, 10)))
          .event(event)
          .build());
    }
  }

  private void createRandomNewsfeed(User user){
    newsfeedEntryRepository.saveAndFlush(NewsfeedEntry.builder()
            .type(NewsfeedEntryType.POST)
            .creator(user.uuid())
            .textField(getRandomLoremIpsum(20, 200))
            .creationDate(LocalDateTime.now())
            .build());
  }

  private String getRandomLoremIpsum(int min, int max){
    return loremIpsum.getWords(new Random().nextInt(max - min + 1) + min);
  }
}
