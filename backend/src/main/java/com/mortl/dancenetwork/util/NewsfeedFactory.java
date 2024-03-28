package com.mortl.dancenetwork.util;

import com.mortl.dancenetwork.dto.NewsfeedEntryDTO;
import com.mortl.dancenetwork.entity.User;
import com.mortl.dancenetwork.model.Event;
import com.mortl.dancenetwork.model.NewsfeedEntry;
import com.mortl.dancenetwork.model.NewsfeedEntryType;
import com.mortl.dancenetwork.model.Ticket;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class NewsfeedFactory {

  public NewsfeedEntry createEventPublishedNewsfeedEntry(User user, Event event) {
    return NewsfeedEntry.builder()
        .type(NewsfeedEntryType.EVENT_CREATION)
        .creator(user.uuid())
        .textField(user.username() + " created the event " + event.getName() + ".\nIt finds place at " + event.getLocation() + " at " + event.getStartDate() + ".")
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
