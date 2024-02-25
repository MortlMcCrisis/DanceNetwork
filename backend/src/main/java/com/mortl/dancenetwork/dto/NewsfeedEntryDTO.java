package com.mortl.dancenetwork.dto;

import com.mortl.dancenetwork.model.NewsfeedEntry;
import java.time.LocalDateTime;
import java.util.UUID;

public record NewsfeedEntryDTO(Long id, UUID creator, String textField, LocalDateTime creationDate) {

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

  public static NewsfeedEntryDTO fromModel(NewsfeedEntry newsfeedEntry){
    return new NewsfeedEntryDTO(
        newsfeedEntry.getId(),
        newsfeedEntry.getCreator(),
        newsfeedEntry.getTextField(),
        newsfeedEntry.getCreationDate());
  }
}