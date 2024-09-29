package com.mortl.dancenetwork.dto;

import com.mortl.dancenetwork.enums.NewsfeedEntryType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.UUID;

public record NewsfeedEntryDTO(
    Long id,
    @NotEmpty
    NewsfeedEntryType type,
    @NotEmpty
    UUID creator,
    @NotBlank
    String textField,
    String image,
    @NotEmpty
    LocalDateTime creationDate)
{

  //TODO fabric class which sets automatically username and time
  //TODO pass also Date?
  //TODO creation date created in database
}