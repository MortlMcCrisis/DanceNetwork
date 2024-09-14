package com.mortl.dancenetwork.mapper;

import com.mortl.dancenetwork.dto.EventDTO;
import com.mortl.dancenetwork.entity.Event;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventMapper {

  EventDTO toDTO(Event event);

  Event toModel(EventDTO eventDTO);
}