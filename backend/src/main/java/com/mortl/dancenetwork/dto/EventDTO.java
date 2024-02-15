package com.mortl.dancenetwork.dto;

import com.mortl.dancenetwork.entity.User;
import com.mortl.dancenetwork.model.Event;
import com.mortl.dancenetwork.model.NewsfeedEntry;
import java.util.Date;
import java.util.UUID;

public record EventDTO(Long id, UUID creator, Date date, Date enddate, String name, String address, String url, String mail) {

  public Event toModel(UUID creator){
    return Event.builder()
        .id(this.id)
        .creator(creator)
        .date(this.date)
        .enddate(this.enddate)
        .name(this.name)
        .address(this.address)
        .url(this.url)
        .mail(this.mail)
        .build();
  }

  public static EventDTO fromModel(Event event){
    return new EventDTO(
        event.getId(),
        event.getCreator(),
        event.getDate(),
        event.getEnddate(),
        event.getName(),
        event.getAddress(),
        event.getUrl(),
        event.getMail());
  }
}