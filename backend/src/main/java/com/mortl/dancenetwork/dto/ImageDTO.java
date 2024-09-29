package com.mortl.dancenetwork.dto;

import jakarta.validation.constraints.NotEmpty;

public record ImageDTO(
    @NotEmpty
    String path/*,
  String title,
  LocalDateTime uploadTime*/
)
{

}
