package com.mortl.dancenetwork.controller.open;

import com.mortl.dancenetwork.dto.EventDTO;
import com.mortl.dancenetwork.entity.Event;
import com.mortl.dancenetwork.mapper.EventMapper;
import com.mortl.dancenetwork.service.IEventService;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/open/v1/events")
public class EventOpenController
{

  private final IEventService eventService;

  private final EventMapper eventMapper;

  public EventOpenController(IEventService eventService, EventMapper eventMapper)
  {
    this.eventService = eventService;
    this.eventMapper = eventMapper;
  }

  @GetMapping
  public ResponseEntity<List<EventDTO>> getEvents(
      @RequestParam Optional<Integer> maxEntries,
      @RequestParam Optional<LocalDate> from)
  {
    List<Event> publishedEvents = eventService.getPublishedEvents(maxEntries, from);
    return ResponseEntity.ok(publishedEvents.stream().map(eventMapper::toDTO).toList());
  }

  @GetMapping("/{id}")
  public ResponseEntity<EventDTO> getEvent(@PathVariable Long id)
  {
    Event event = eventService.getEvent(id);
    return ResponseEntity.ok(eventMapper.toDTO(event));
  }
}
