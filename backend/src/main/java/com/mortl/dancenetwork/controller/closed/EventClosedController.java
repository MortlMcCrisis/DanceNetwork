package com.mortl.dancenetwork.controller.closed;

import com.mortl.dancenetwork.dto.EventDTO;
import com.mortl.dancenetwork.dto.PatchEventDTO;
import com.mortl.dancenetwork.entity.Event;
import com.mortl.dancenetwork.mapper.EventMapper;
import com.mortl.dancenetwork.service.IEventService;
import jakarta.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import javax.ws.rs.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/closed/v1/events")
public class EventClosedController
{

  private static final Logger log = LoggerFactory.getLogger(EventClosedController.class);

  private final IEventService eventService;

  private final EventMapper eventMapper;

  public EventClosedController(IEventService eventService, EventMapper eventMapper)
  {
    this.eventService = eventService;
    this.eventMapper = eventMapper;
  }

  @PostMapping
  public ResponseEntity<EventDTO> createEvent(@Valid @RequestBody EventDTO eventDTO)
      throws URISyntaxException
  {
    Event savedEvent = eventService.createEvent(eventMapper.toEntity(eventDTO));
    return ResponseEntity
        .created(new URI("/api/v1/events/" + savedEvent.getId()))
        .body(eventMapper.toDTO(savedEvent));
  }

  @PutMapping("/{id}")
  public ResponseEntity<EventDTO> updateEvent(@PathVariable Long id,
      @Valid @RequestBody EventDTO eventDTO)
      throws NotFoundException, IllegalAccessException
  {
    log.info("saving event with id {}", id);
    if (!id.equals(eventDTO.id()))
    {
      throw new IllegalArgumentException(
          "id in path (" + id + ") does not match the id of the object (" + eventDTO.id() + ").");
    }

    Event updateEvent = eventService.updateEvent(eventMapper.toEntity(eventDTO));
    return ResponseEntity.ok(eventMapper.toDTO(updateEvent));
  }

  @PatchMapping("/{id}/{property}")
  public ResponseEntity<EventDTO> patchEventTitle(
      @PathVariable Long id,
      @PathVariable String property,
      @RequestBody PatchEventDTO value)
      throws NotFoundException, IllegalAccessException
  {
    log.info("Saving property {} of event with id {} with value {}", property, id, value);
    Event updateEvent = eventService.updateEventProperty(id, property, value.value());
    return ResponseEntity.ok(eventMapper.toDTO(updateEvent));
  }

  @PutMapping("/{id}/publish")
  public ResponseEntity<Void> publishEvent(@PathVariable Long id)
      throws NotFoundException, IllegalAccessException
  {
    log.info("Publishing event with id {}", id);

    eventService.publishEvent(id);

    return ResponseEntity.ok().build();
  }
}
