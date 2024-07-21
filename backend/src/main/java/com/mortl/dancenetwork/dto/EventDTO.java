package com.mortl.dancenetwork.dto;

import java.time.LocalDate;
import java.util.UUID;

public record EventDTO(
    Long id,
    UUID creator,
    String profileImage,
    String bannerImage,
    LocalDate startDate,
    LocalDate endDate,
    String name,
    String location,
    String website,
    String email,
    boolean published) {
}