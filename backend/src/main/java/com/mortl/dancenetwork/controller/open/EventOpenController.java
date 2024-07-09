package com.mortl.dancenetwork.controller.open;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mortl.dancenetwork.dto.EventDTO;
import com.mortl.dancenetwork.dto.PaymentRequestDTO;
import com.mortl.dancenetwork.model.Event;
import com.mortl.dancenetwork.service.IEventService;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
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
@RequestMapping("/api/open/v1/events")
@Slf4j
@RequiredArgsConstructor
public class EventOpenController {

  private final IEventService eventService;

  @GetMapping
  public ResponseEntity<List<EventDTO>> getEvents() {
      return ResponseEntity.ok(eventService.getAllPublishedEvents());
  }

  @GetMapping("/{id}")
  public ResponseEntity<EventDTO> getEvent(@PathVariable Long id) {
    EventDTO event = eventService.getEvent(id);
    return ResponseEntity.ok(event);
  }
}
