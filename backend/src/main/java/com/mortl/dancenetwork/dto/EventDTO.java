package com.mortl.dancenetwork.dto;

import com.mortl.dancenetwork.model.Event;
import java.util.Date;
import java.util.UUID;

public record EventDTO(Long id, UUID creator, Date startDate, Date endDate, String name, String location, String website, String email, boolean published) {

  public Event toModel(UUID creator){
    return Event.builder()
        .id(this.id)
        .creator(creator)
        .startDate(this.startDate)
        .endDate(this.endDate)
        .name(this.name)
        .location(this.location)
        .website(this.website)
        .email(this.email)
        .published(this.published)
        .build();
  }

  public static EventDTO fromModel(Event event){
    return new EventDTO(
        event.getId(),
        event.getCreator(),
        event.getStartDate(),
        event.getEndDate(),
        event.getName(),
        event.getLocation(),
        event.getWebsite(),
        event.getEmail(),
        event.isPublished());
  }
}