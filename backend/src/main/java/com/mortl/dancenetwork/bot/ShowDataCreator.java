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
import java.util.List;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
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
        .endDate(LocalDate.of(2024, 8, 18))
        .name("Bachaturo")
        .location("plac Sławika i Antalla 1, 40-163 Katowice, Poland")
        .website("https://bachaturo.com/")
        .email("bachaturo@info.pl")
        .published(true)
        .build();
    createEvent(bachaturo, bachaturoUser);

    User bachatationUser = users.get(new Random().nextInt(0, users.size()));
    Event bachatation = Event.builder()
        .creator(bachatationUser.uuid())
        .startDate(LocalDate.of(2024, 2, 22))
        .endDate(LocalDate.of(2024, 2, 25))
        .name("Bachatation")
        .location("Son Latino Studios, Gablonzer Str. 9, 76185 Karlsruhe")
        .website("http://www.bachatation.de/")
        .email("events@sonlatino.de")
        .published(true)
        .build();
    createEvent(bachatation, bachatationUser);
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
