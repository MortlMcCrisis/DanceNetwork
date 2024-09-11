package com.mortl.dancenetwork.controller.open;

import com.mortl.dancenetwork.dto.EventDTO;
import com.mortl.dancenetwork.service.IEventService;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/open/v1/events")
@Slf4j
@RequiredArgsConstructor
public class EventOpenController {

  private final IEventService eventService;

  @GetMapping
  public ResponseEntity<List<EventDTO>> getEvents(
      @RequestParam Optional<Integer> maxEntries,
      @RequestParam Optional<LocalDate> from) {
      return ResponseEntity.ok(eventService.getPublishedEvents(maxEntries, from));
  }

  @GetMapping("/{id}")
  public ResponseEntity<EventDTO> getEvent(@PathVariable Long id) {
    EventDTO event = eventService.getEvent(id);
    return ResponseEntity.ok(event);
  }
}
