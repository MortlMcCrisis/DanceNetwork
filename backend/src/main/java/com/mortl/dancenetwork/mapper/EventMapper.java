package com.mortl.dancenetwork.mapper;

import com.mortl.dancenetwork.dto.EventDTO;
import com.mortl.dancenetwork.entity.Event;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EventMapper
{

  EventDTO toDTO(Event event);

  @Mapping(target = "createdAt", ignore = true)
  Event toEntity(EventDTO eventDTO);
}