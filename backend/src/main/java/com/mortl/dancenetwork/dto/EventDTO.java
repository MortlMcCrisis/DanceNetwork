package com.mortl.dancenetwork.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record EventDTO(
    Long id,
    @NotEmpty
    UUID creator,
    String profileImage,
    String bannerImage,
    @NotEmpty
    LocalDate startDate,
    @NotEmpty
    LocalTime startTime,
    LocalDate endDate,
    @NotEmpty
    String name,
    String location,
    String website,
    @Email
    String email,
    boolean published) {
}