package com.mortl.dancenetwork.dto;

import java.time.LocalDate;
import java.util.UUID;

public record EventDTO(
    Long id,
    UUID creator,//TODO brauche ich das hier? löschen?
    String profileImage,
    LocalDate startDate,
    LocalDate endDate,
    String name,
    String location,
    String website,
    String email,
    boolean published) {
}