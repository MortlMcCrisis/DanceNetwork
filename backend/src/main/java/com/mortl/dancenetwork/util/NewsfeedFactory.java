package com.mortl.dancenetwork.util;

import com.mortl.dancenetwork.dto.NewsfeedEntryDTO;
import com.mortl.dancenetwork.entity.User;
import com.mortl.dancenetwork.model.Event;
import com.mortl.dancenetwork.model.NewsfeedEntry;
import com.mortl.dancenetwork.model.NewsfeedEntryType;
import java.time.LocalDateTime;
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
}
