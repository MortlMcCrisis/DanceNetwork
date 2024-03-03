package com.mortl.dancenetwork.bot;

import com.mortl.dancenetwork.dto.NewsfeedEntryDTO;
import com.mortl.dancenetwork.entity.User;
import com.mortl.dancenetwork.model.Event;
import com.mortl.dancenetwork.model.NewsfeedEntry;
import com.mortl.dancenetwork.model.NewsfeedEntryType;
import com.mortl.dancenetwork.repository.EventRepository;
import com.mortl.dancenetwork.repository.NewsfeedEntryRepository;
import com.mortl.dancenetwork.service.IUserService;
import com.mortl.dancenetwork.util.NewsfeedFactory;
import de.svenjacobs.loremipsum.LoremIpsum;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NewsfeedBot {

  private NewsfeedEntryRepository newsfeedEntryRepository;

  private EventRepository eventRepository;

  private NewsfeedFactory newsfeedFactory;

  private LoremIpsum loremIpsum;

  private List<User> users;

  public NewsfeedBot(
      IUserService userService,
      EventRepository eventRepository,
      NewsfeedEntryRepository newsfeedEntryRepository,
      NewsfeedFactory newsfeedFactory){
    this.newsfeedEntryRepository = newsfeedEntryRepository;
    this.eventRepository = eventRepository;
    this.newsfeedFactory = newsfeedFactory;
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
    eventRepository.saveAndFlush( event );
    newsfeedEntryRepository.saveAndFlush(newsfeedFactory.createEventPublishedNewsfeedEntry(user, event));
  }

  private void createRandomNewsfeed(User user){
    Random random = new Random();

    // Generieren Sie einen Zufallswert zwischen 20 und 3000
    int minValue = 20;
    int maxValue = 200;
    int randomValue = random.nextInt(maxValue - minValue + 1) + minValue;

    newsfeedEntryRepository.saveAndFlush(NewsfeedEntry.builder()
            .type(NewsfeedEntryType.POST)
            .creator(user.uuid())
            .textField(loremIpsum.getWords(randomValue))
            .creationDate(LocalDateTime.now())
            .build());
  }
}
