package com.mortl.dancenetwork.bot;

import com.mortl.dancenetwork.entity.Event;
import com.mortl.dancenetwork.entity.NewsfeedEntry;
import com.mortl.dancenetwork.entity.Ticket;
import com.mortl.dancenetwork.entity.TicketOrder;
import com.mortl.dancenetwork.entity.TicketType;
import com.mortl.dancenetwork.enums.DancingRole;
import com.mortl.dancenetwork.enums.Gender;
import com.mortl.dancenetwork.enums.NewsfeedEntryType;
import com.mortl.dancenetwork.model.User;
import com.mortl.dancenetwork.repository.EventRepository;
import com.mortl.dancenetwork.repository.NewsfeedEntryRepository;
import com.mortl.dancenetwork.repository.TicketOrderRepository;
import com.mortl.dancenetwork.repository.TicketRepository;
import com.mortl.dancenetwork.repository.TicketTypeRepository;
import com.mortl.dancenetwork.service.IUserService;
import com.mortl.dancenetwork.util.NewsfeedFactory;
import de.svenjacobs.loremipsum.LoremIpsum;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import javax.money.Monetary;
import org.javamoney.moneta.Money;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Profile("!test")
public class ShowDataCreator
{

  //private static final String IMAGE_BASE_URL = "http://10.0.2.2:8080";
  //private static final String IMAGE_BASE_URL = "http://localhost:8080";
  private static final String IMAGE_BASE_URL = "";

  private NewsfeedEntryRepository newsfeedEntryRepository;

  private EventRepository eventRepository;

  private NewsfeedFactory newsfeedFactory;

  private TicketTypeRepository ticketTypeRepository;

  private TicketRepository ticketRepository;

  private TicketOrderRepository ticketOrderRepository;

  private LoremIpsum loremIpsum;

  private List<User> users;

  public ShowDataCreator(
      IUserService userService,
      EventRepository eventRepository,
      NewsfeedEntryRepository newsfeedEntryRepository,
      TicketTypeRepository ticketTypeRepository,
      TicketOrderRepository ticketOrderRepository,
      TicketRepository ticketRepository,
      NewsfeedFactory newsfeedFactory)
  {
    this.newsfeedEntryRepository = newsfeedEntryRepository;
    this.eventRepository = eventRepository;
    this.newsfeedFactory = newsfeedFactory;
    this.ticketTypeRepository = ticketTypeRepository;
    this.ticketRepository = ticketRepository;
    this.ticketOrderRepository = ticketOrderRepository;

    this.loremIpsum = new LoremIpsum();
    this.users = userService.getAllUsers();
  }

  @Scheduled(fixedRate = Long.MAX_VALUE)
  public void createNewsfeedEntries()
  {
    users.stream().forEach(user -> createRandomNewsfeed(user));
  }

  @Scheduled(fixedDelay = Long.MAX_VALUE)
  public void createEvents()
  {
    User bachaturoUser = users.get(new Random().nextInt(0, users.size()));
    Event bachaturo = new Event(
        null,
        "Bachaturo",
        bachaturoUser.getUuid(),
        "bachaturo@info.pl",
        LocalDate.of(2025, 8, 16),
        LocalTime.NOON,
        LocalDate.of(2025, 8, 18),
        "plac Sławika i Antalla 1, 40-163 Katowice, Poland",
        "https://bachaturo.com/",
        IMAGE_BASE_URL + "/images/Bachaturo.png",
        //"/upload/2c22e8eef931ef9a428195d8f1e55573/Bachaturo.png",
        null,
        true,
        LocalDateTime.now().minusMonths(8));
    createEvent(bachaturo, bachaturoUser);
    createTicketType(bachaturo, new TicketType(
        null,
        "FULLPASS",
        "All workshops, all parties on all dance floors, and the Kizomba social room.",
        Money.of(121.00f, Monetary.getCurrency("EUR")),
        100L,
        bachaturo));
    createTicketType(bachaturo, new TicketType(
        null,
        "GROUP FULLPASS (with promocode)",
        "All workshops, all parties on all dance floors, and the Kizomba social room.",
        Money.of(114.00f, Monetary.getCurrency("EUR")),
        100L,
        bachaturo));
    createTicketType(bachaturo, new TicketType(
        null,
        "Partypass",
        "All Parties and Kizomba Social Room",
        Money.of(85.00f, Monetary.getCurrency("EUR")),
        100L,
        bachaturo));

    User bachatationUser = users.get(new Random().nextInt(0, users.size()));
    Event bachatation = new Event(
        null,
        "Bachatation",
        bachatationUser.getUuid(),
        "events@sonlatino.de",
        LocalDate.of(2025, 2, 22),
        LocalTime.NOON,
        LocalDate.of(2025, 2, 25),
        "Son Latino Studios, Gablonzer Str. 9, 76185 Karlsruhe",
        "http://www.bachatation.de/",
        IMAGE_BASE_URL + "/images/Bachatation.png",
        //"/upload/2c22e8eef931ef9a428195d8f1e55573/Bachatation.png",
        null,
        true,
        LocalDateTime.now().minusMonths(8));
    createEvent(bachatation, bachatationUser);
    createTicketType(bachatation, new TicketType(
        null,
        "FULLPASS",
        "Includes all Workshops & all Parties",
        Money.of(109, Monetary.getCurrency("EUR")),
        100L,
        bachatation));
    createTicketType(bachatation, new TicketType(
        null,
        "MASTER CLASS",
        "Master Class with Kike & Nahir",
        Money.of(109, Monetary.getCurrency("EUR")),
        100L,
        bachatation));
    createTicketType(bachatation, new TicketType(
        null,
        "VIP Party-Pass",
        "All 4 Parties wit Pre-Party-Workshop",
        Money.of(45, Monetary.getCurrency("EUR")),
        100L,
        bachatation));
    createTicketType(bachatation, new TicketType(
        null,
        "PARTY-PASS",
        "All 4 Parties",
        Money.of(29, Monetary.getCurrency("EUR")),
        100L,
        bachatation));

    User backataRoyalUser = users.get(new Random().nextInt(0, users.size()));
    Event bachataRoyal = new Event(
        null,
        "Bachata Zouk Royals",
        backataRoyalUser.getUuid(),
        "florence.vouriot@gmx.de",
        LocalDate.of(2025, 10, 25),
        LocalTime.NOON,
        LocalDate.of(2025, 10, 27),
        "Freiburg, Kaiser Joseph Straße 268 79098 - Freiburg Baden-Württemberg",
        "https://www.goandance.com/en/events/6476-bachata-zouk-royals-2025",
        IMAGE_BASE_URL + "/images/BachataZouk.png",
        //"/upload/2c22e8eef931ef9a428195d8f1e55573/BachataZouk.png",
        null,
        true,
        LocalDateTime.now().minusMonths(8));
    createEvent(bachataRoyal, backataRoyalUser);
    createTicketType(bachataRoyal, new TicketType(
        null,
        "Fullpass - BachataZoukRoyals- Leader",
        "Access to all Workshops (as a leader,and social rooms, including shows",
        Money.of(135, Monetary.getCurrency("EUR")),
        100L,
        bachataRoyal));
    createTicketType(bachataRoyal, new TicketType(
        null,
        "Fullpass - BachataZoukRoyals - Follower",
        "Full access to all Workshops (as a follower,, party and shows",
        Money.of(135, Monetary.getCurrency("EUR")),
        100L,
        bachataRoyal));
    createTicketType(bachataRoyal, new TicketType(
        null,
        "Party Pass",
        "This pass will give you access to the Friday, Saturday night socials and sunday day & night social.<br/><br/>only limited amount available! Last year, we were sold out, and we expect the same to happen this year.",
        Money.of(65, Monetary.getCurrency("EUR")),
        100L,
        bachataRoyal));
    createTicketType(bachataRoyal, new TicketType(
        null,
        "Friday Day Pass",
        "This Day Pass includes all workshops, shows and social on Friday the 25th of October 2025.<br/>On Friday the workshops will start at 6 p.m.<br/>After the pre party workshops at 9 p.m. the social will start at 10 p.m.<br/>The shows will be around midnight ",
        Money.of(35, Monetary.getCurrency("EUR")),
        100L,
        bachataRoyal));
    createTicketType(bachataRoyal, new TicketType(
        null,
        "Saturday Day Pass- Leader",
        "This Day Pass, for Leader, includes all workshops, shows and social on Saturday the 26th of October 2025.<br/>On Saturday the workshops will start at 2 p.m.<br/>The day social will start at 6 p.m. and the night social will start at 10 p.m.<br/>The shows will be around midnight.",
        Money.of(65, Monetary.getCurrency("EUR")),
        100L,
        bachataRoyal));
    createTicketType(bachataRoyal, new TicketType(
        null,
        "Saturday Day Pass - Follower",
        "This Day Pass, for Follower, includes all workshops, shows and social on Saturday the 26th of October 2025.<br/>On Saturday the workshops will start at 2 p.m.<br/>The day social will start at 6 p.m. and the night social will start at 10 p.m.<br/>The shows will be around midnight.",
        Money.of(65, Monetary.getCurrency("EUR")),
        100L,
        bachataRoyal));
    createTicketType(bachataRoyal, new TicketType(
        null,
        "Sunday Day Pass - Leader",
        "This Day Pass, for Leader, includes all workshops and social on Sunday the 27th of October 2025.<br/>On Sunday the workshops will start at 1 p.m.<br/>The day social will start at 4 p.m. until midnight",
        Money.of(45, Monetary.getCurrency("EUR")),
        100L,
        bachataRoyal));
    createTicketType(bachataRoyal, new TicketType(
        null,
        "Sunday Day Pass - Follower",
        "This Day Pass, for Follower, includes all workshops and social on Sunday the 27th of October 2025.<br/>On Sunday the workshops will start at 1 p.m.<br/>The day social will start at 4 p.m. until midnight",
        Money.of(45, Monetary.getCurrency("EUR")),
        100L,
        bachataRoyal));
    createTicketType(bachataRoyal, new TicketType(
        null,
        "Friday night social",
        "The Friday night ticket for the 25th of October includes the social starting at 10 p.m. and the shows around midnight",
        Money.of(25, Monetary.getCurrency("EUR")),
        100L,
        bachataRoyal));
    createTicketType(bachataRoyal, new TicketType(
        null,
        "Saturday night social",
        "The Saturday night ticket for the 26th of October includes the social starting at 10 p.m. and the shows around midnight",
        Money.of(30, Monetary.getCurrency("EUR")),
        100L,
        bachataRoyal));
    createTicketType(bachataRoyal, new TicketType(
        null,
        "Sunday social ticket",
        "The Sunday social ticket for the 27th of October includes the day social starting at 4 p.m. till midnight",
        Money.of(15, Monetary.getCurrency("EUR")),
        100L,
        bachataRoyal));

    User bachatologyUser = users.get(new Random().nextInt(0, users.size()));
    Event bachatology = new Event(
        null,
        "Bachatology",
        backataRoyalUser.getUuid(),
        null,
        LocalDate.of(2025, 9, 27),
        LocalTime.NOON,
        LocalDate.of(2025, 9, 28),
        "Karlsruhe, Hans-Sachs-Straße 8, 76133",
        "https://www.tickettailor.com/events/socialdancevip/1218659?fbclid=IwZXh0bgNhZW0CMTEAAR3BWpgiEoj5x8tOD7WnCdVqaebNOKNLqwGtW5ghvZBw3cSvt5XwBYsJY6A_aem_Xdvk1biN-Ez2WXS4MEUxzg&sfnsn=scwspwa",
        IMAGE_BASE_URL + "/images/Bachatology.png",
        //"/upload/2c22e8eef931ef9a428195d8f1e55573/Bachatology.png",
        null,
        true,
        LocalDateTime.now().minusMonths(8));
    createEvent(bachatology, bachatologyUser);
    createRandomTicketTypesForEvent(bachatology);
  }

  private void createEvent(Event event, User user)
  {
    event = eventRepository.saveAndFlush(event);
    newsfeedEntryRepository.saveAndFlush(
        newsfeedFactory.createEventPublishedNewsfeedEntry(user, event));
  }

  private void createRandomTicketTypesForEvent(Event event)
  {
    int amountOfTickets = new Random().nextInt(2, 5);
    for (int i = 0; i < amountOfTickets; i++)
    {
      createTicketType(event, new TicketType(
          null,
          getRandomLoremIpsum(5, 20),
          loremIpsum.getWords(new Random().nextInt(1, 10)),
          Money.of((BigDecimal.valueOf(new Random().nextFloat(15, 250))
              .setScale(2, BigDecimal.ROUND_HALF_DOWN)
              .floatValue()), Monetary.getCurrency("EUR")),
          100L,
          event));
    }
  }

  private void createRandomNewsfeed(User user)
  {
    newsfeedEntryRepository.saveAndFlush(new NewsfeedEntry(
        null,
        NewsfeedEntryType.POST,
        user.getUuid(),
        getRandomLoremIpsum(20, 200),
        null,
        LocalDateTime.now()));
  }

  private String getRandomLoremIpsum(int min, int max)
  {
    return loremIpsum.getWords(new Random().nextInt(max - min + 1) + min);
  }

  private void createRandomTickets(Event event, TicketType ticketType)
  {
    Set<Ticket> tickets = new HashSet<>();
    TicketOrder ticketOrder = new TicketOrder(null, getRandomDate(event.getCreatedAt()));
    ticketOrder = ticketOrderRepository.saveAndFlush(ticketOrder);
    for (int i = 0; i < new Random().nextInt(0, 1); i++)
    {
      tickets.add(new Ticket(
          null,
          users.get(new Random().nextInt(0, users.size())).getUuid(),
          ticketType,
          getRandomLoremIpsum(1, 3),
          getRandomLoremIpsum(1, 2),
          getRandomLoremIpsum(5, 20),
          "GERMANY",
          "test@test.de",
          getRandomLoremIpsum(5, 20),
          getRandomDancingRole(),
          getRandomGender(),
          ticketOrder));
    }
    ticketRepository.saveAllAndFlush(tickets);
  }

  private void createTicketType(Event event, TicketType ticketType)
  {
    ticketTypeRepository.saveAndFlush(ticketType);
    createRandomTickets(event, ticketType);
  }

  private DancingRole getRandomDancingRole()
  {
    DancingRole[] dancingRoles = DancingRole.values();
    return dancingRoles[new Random().nextInt(dancingRoles.length)];
  }

  private Gender getRandomGender()
  {
    Gender[] gender = Gender.values();
    return gender[new Random().nextInt(gender.length)];
  }

  private LocalDateTime getRandomDate(LocalDateTime startDate)
  {
    LocalDateTime endDate = LocalDateTime.now();

    long startEpochSecond = startDate.toEpochSecond(java.time.ZoneOffset.UTC);
    long endEpochSecond = endDate.toEpochSecond(java.time.ZoneOffset.UTC);

    long randomEpochSecond = ThreadLocalRandom.current().nextLong(startEpochSecond, endEpochSecond);

    return LocalDateTime.ofEpochSecond(randomEpochSecond, 0, java.time.ZoneOffset.UTC);
  }
}
