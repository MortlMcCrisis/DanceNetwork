package com.mortl.dancenetwork.controller;

import com.mortl.dancenetwork.dto.EventDTO;
import com.mortl.dancenetwork.dto.NewsfeedEntryDTO;
import com.mortl.dancenetwork.model.Event;
import com.mortl.dancenetwork.service.EventService;
import jakarta.servlet.http.HttpServletResponse;
import java.net.URI;
import java.net.URISyntaxException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/event")
@Slf4j
@RequiredArgsConstructor
public class EventController {

  private final EventService eventService;

  @PostMapping
  public ResponseEntity createEvent(@RequestBody EventDTO eventDTO) throws URISyntaxException {
    EventDTO savedEvent = eventService.createEvent(eventDTO);
    return ResponseEntity.created(new URI("/api/v1/event/" + savedEvent.id())).body(savedEvent);
  }

  @GetMapping("/{id}")
  public EventDTO getEvent(@PathVariable Long id) {
    log.info("Fetching event with id '" + id + "'");
    return eventService.getEvent(id);
  }

  @PutMapping("/{id}")
  public ResponseEntity updateNewsfeedEntry(@PathVariable Long id, @RequestBody EventDTO eventDTO) throws NotFoundException {
    if( id != eventDTO.id()){
      throw new IllegalArgumentException("id in path (" + id + ") does not match the id of the object (" + eventDTO.id() + ").");
    }

    log.info(eventDTO.toString());

    return ResponseEntity.ok(eventService.updateEvent(eventDTO));
  }
}
