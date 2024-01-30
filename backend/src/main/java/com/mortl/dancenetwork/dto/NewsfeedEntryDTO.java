package com.mortl.dancenetwork.dto;

import com.mortl.dancenetwork.model.NewsfeedEntry;
import java.util.Date;
import java.util.UUID;

public record NewsfeedEntryDTO(Long id, String userName, Sex creatorSex, String textField, Date creationDate) {

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

  public static NewsfeedEntryDTO fromModel(NewsfeedEntry newsfeedEntry, String userName, Sex creatorSex){
    return new NewsfeedEntryDTO(
        newsfeedEntry.getId(),
        userName,
        creatorSex,
        newsfeedEntry.getTextField(),
        newsfeedEntry.getCreationDate());
  }
}