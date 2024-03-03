package com.mortl.dancenetwork.controller;

import com.mortl.dancenetwork.dto.EventDTO;
import com.mortl.dancenetwork.service.IEventService;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

  private final IEventService eventService;

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

  @GetMapping
  public List<Long> getAllEvents() {
    return eventService.getAllPublishedEvents();
  }

  @PutMapping("/{id}")
  public ResponseEntity updateEvent(@PathVariable Long id, @RequestBody EventDTO eventDTO) {
    log.info("saving event with id " + id);
    if( id != eventDTO.id()){
      throw new IllegalArgumentException("id in path (" + id + ") does not match the id of the object (" + eventDTO.id() + ").");
    }

    return ResponseEntity.ok(eventService.updateEvent(eventDTO));
  }

  @PutMapping("/{id}/publish")
  public ResponseEntity publishEvent(@PathVariable Long id) {
    log.info("Publishing event with id " + id);

    eventService.publishEvent(id);

    return ResponseEntity.ok().build();
  }
}
