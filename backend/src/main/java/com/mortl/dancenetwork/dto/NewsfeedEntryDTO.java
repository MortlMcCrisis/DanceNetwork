package com.mortl.dancenetwork.dto;

import com.mortl.dancenetwork.enums.NewsfeedEntryType;
import java.time.LocalDateTime;
import java.util.UUID;

public record NewsfeedEntryDTO(
    Long id,
    NewsfeedEntryType type,
    UUID creator,
    String textField,
    String image,
    LocalDateTime creationDate) {

  //TODO fabric class which sets automatically username and time
  //TODO pass also Date?
  //TODO creation date created in database
}