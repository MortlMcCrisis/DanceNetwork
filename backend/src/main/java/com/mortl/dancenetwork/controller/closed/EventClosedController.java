package com.mortl.dancenetwork.controller.closed;

import com.mortl.dancenetwork.dto.EventDTO;
import com.mortl.dancenetwork.service.IEventService;
import jakarta.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import javax.ws.rs.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/closed/v1/events")
public class EventClosedController {

  private static final Logger log = LoggerFactory.getLogger(EventClosedController.class);

  private final IEventService eventService;

  public EventClosedController(IEventService eventService) {
    this.eventService = eventService;
  }

  @PostMapping
  public ResponseEntity<EventDTO> createEvent(@Valid @RequestBody EventDTO eventDTO) throws URISyntaxException {
    EventDTO savedEvent = eventService.createEvent(eventDTO);
    return ResponseEntity.created(new URI("/api/v1/events/" + savedEvent.id())).body(savedEvent);
  }

  @PutMapping("/{id}")
  public ResponseEntity<EventDTO> updateEvent(@PathVariable Long id, @Valid @RequestBody EventDTO eventDTO)
      throws NotFoundException, IllegalAccessException {
    log.info("saving event with id " + id);
    if( id != eventDTO.id()){
      throw new IllegalArgumentException("id in path (" + id + ") does not match the id of the object (" + eventDTO.id() + ").");
    }

    return ResponseEntity.ok(eventService.updateEvent(eventDTO));
  }

  @PutMapping("/{id}/publish")
  public ResponseEntity<Void> publishEvent(@PathVariable Long id)
      throws NotFoundException, IllegalAccessException{
    log.info("Publishing event with id " + id);

    eventService.publishEvent(id);

    return ResponseEntity.ok().build();
  }
}
