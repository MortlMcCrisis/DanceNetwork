package com.mortl.dancenetwork.dto;

import com.mortl.dancenetwork.entity.User;
import com.mortl.dancenetwork.model.NewsfeedEntry;
import java.util.Date;
import java.util.UUID;

public record NewsfeedEntryDTO(String photoPath, Long id, String userName, Sex creatorSex, String textField, Date creationDate) {

  //TODO fabric class which sets automatically username and time
  //TODO pass also Date?
  //TODO creation date created in database
  //TODO use mapstruct?
  public NewsfeedEntry toModel(UUID creator){
    return NewsfeedEntry.builder()
        .id(this.id)
        .creator(creator)
        .textField(this.textField)
        .creationDate(new Date())
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