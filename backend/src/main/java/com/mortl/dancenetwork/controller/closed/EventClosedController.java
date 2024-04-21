package com.mortl.dancenetwork.controller.closed;

import com.mortl.dancenetwork.dto.EventDTO;
import com.mortl.dancenetwork.service.IEventService;
import java.net.URI;
import java.net.URISyntaxException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/closed/v1/events")
@Slf4j
@RequiredArgsConstructor
public class EventClosedController {

  private final IEventService eventService;

  @PostMapping
  public ResponseEntity createEvent(@RequestBody EventDTO eventDTO) throws URISyntaxException {
    EventDTO savedEvent = eventService.createEvent(eventDTO);
    return ResponseEntity.created(new URI("/api/v1/events/" + savedEvent.id())).body(savedEvent);
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
