package com.mortl.dancenetwork.dto;

import com.mortl.dancenetwork.entity.User;
import com.mortl.dancenetwork.model.NewsfeedEntry;
import java.time.LocalDateTime;
import java.util.UUID;

//TODO move photo path somewhere else. Has nothing to do with the newsfeed entry
// --> only set creator id and pull user data for every user separately by the newsfeed entry component
public record NewsfeedEntryDTO(String photoPath, Long id, String userName, Sex creatorSex, String textField, LocalDateTime creationDate) {

  //TODO fabric class which sets automatically username and time
  //TODO pass also Date?
  //TODO creation date created in database
  //TODO use mapstruct?
  public NewsfeedEntry toModel(UUID creator){
    return NewsfeedEntry.builder()
        .id(this.id)
        .creator(creator)
        .textField(this.textField)
        .creationDate(LocalDateTime.now())
        .build();
  }

  public static NewsfeedEntryDTO fromModel(NewsfeedEntry newsfeedEntry, User creator){
    return new NewsfeedEntryDTO(
        creator.photoPath(),
        newsfeedEntry.getId(),
        creator.username(),
        creator.sex(),
        newsfeedEntry.getTextField(),
        newsfeedEntry.getCreationDate());
  }
}