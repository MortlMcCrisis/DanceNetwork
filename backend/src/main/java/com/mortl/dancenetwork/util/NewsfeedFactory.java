package com.mortl.dancenetwork.util;

import com.mortl.dancenetwork.entity.Event;
import com.mortl.dancenetwork.entity.NewsfeedEntry;
import com.mortl.dancenetwork.entity.Ticket;
import com.mortl.dancenetwork.enums.NewsfeedEntryType;
import com.mortl.dancenetwork.model.User;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class NewsfeedFactory {

  public NewsfeedEntry createEventPublishedNewsfeedEntry(User user, Event event) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");
    return new NewsfeedEntry(
        null,
        NewsfeedEntryType.EVENT_CREATION,
        user.getUuid(),
        user.getUsername() + " created the event " + event.getName() + ".\nIt finds place at " + event.getLocation() + " at " + formatter.format(event.getStartDate()) + ".",
        event.getProfileImage(),
        LocalDateTime.now());
  }

  public NewsfeedEntry createTicketsBoughtNewsfeedEntry(User user, List<Ticket> tickets) {
    return new NewsfeedEntry(
        null,
        NewsfeedEntryType.TICKET_BOUGHT,
        user.getUuid(),
        //TODO add link to event in the textfield
        user.getUsername() + " bought tickets for event " + tickets.get(0).getTicketType().getEvent().getName() + ".",
        null,
        LocalDateTime.now());
  }

}
