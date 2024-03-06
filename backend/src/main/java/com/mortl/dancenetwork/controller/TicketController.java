package com.mortl.dancenetwork.controller;

import com.mortl.dancenetwork.dto.EventDTO;
import com.mortl.dancenetwork.dto.TicketTypeDTO;
import com.mortl.dancenetwork.service.ITicketTypeService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/ticketTypes")
@Slf4j
@RequiredArgsConstructor
public class TicketController {

  private final ITicketTypeService ticketTypeService;

  @GetMapping
  public List<TicketTypeDTO> getTicketsForEvent(@RequestParam long eventId) {
    log.info("Fetching ticket types for event  " + eventId);

    return ticketTypeService.getTicketTypesForEvent(eventId);
  }

  @PostMapping
  public ResponseEntity addTicketType(@RequestBody TicketTypeDTO ticketTypeDTO) {
    log.info("Add ticket type for event  " + ticketTypeDTO.eventId());

    ticketTypeService.addTicketType(ticketTypeDTO);

    return ResponseEntity.ok().build();
  }
}
