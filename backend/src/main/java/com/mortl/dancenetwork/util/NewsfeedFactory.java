package com.mortl.dancenetwork.util;

import com.mortl.dancenetwork.model.User;
import com.mortl.dancenetwork.entity.Event;
import com.mortl.dancenetwork.entity.NewsfeedEntry;
import com.mortl.dancenetwork.enums.NewsfeedEntryType;
import com.mortl.dancenetwork.entity.Ticket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class NewsfeedFactory {

  public NewsfeedEntry createEventPublishedNewsfeedEntry(User user, Event event) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");
    return NewsfeedEntry.builder()
        .type(NewsfeedEntryType.EVENT_CREATION)
        .creator(user.uuid())
        .textField(user.username() + " created the event " + event.getName() + ".\nIt finds place at " + event.getLocation() + " at " + formatter.format(event.getStartDate()) + ".")
        .image(event.getProfileImage())
        .creationDate(LocalDateTime.now())
        .build();
  }

  public NewsfeedEntry createTicketsBoughtNewsfeedEntry(User user, List<Ticket> tickets) {
    return NewsfeedEntry.builder()
        .type(NewsfeedEntryType.TICKET_BOUGHT)
        .creator(user.uuid())
        .textField(user.username() + " bought tickets for event " + tickets.get(0).getTicketType().getEvent().getName() + ".")
        //TODO add link to event in the textfield
        .creationDate(LocalDateTime.now())
        .build();
  }

}
