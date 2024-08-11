package com.mortl.dancenetwork.controller.closed;

import com.mortl.dancenetwork.dto.TicketTypeDTO;
import com.mortl.dancenetwork.service.ITicketTypeService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/closed/v1/ticket-types")
@Slf4j
@RequiredArgsConstructor
public class TicketTypeClosedController {

  private final ITicketTypeService ticketTypeService;

  @PostMapping
  public ResponseEntity<Void> addTicketType(@RequestBody TicketTypeDTO ticketTypeDTO) {
    log.info("Add ticket type for event  " + ticketTypeDTO.eventId());

    ticketTypeService.addTicketType(ticketTypeDTO);

    return ResponseEntity.ok().build();
  }

  @PutMapping
  public ResponseEntity<Void> updateTicketTypes(@RequestBody List<TicketTypeDTO> ticketTypeDTOs) {
    String ids = ticketTypeDTOs.stream()
        .map(TicketTypeDTO::id)
        .map(String::valueOf)
        .collect(Collectors.joining(", "));

    log.info("Updating ticket types " + ids);

    ticketTypeService.updateTicketTypes(ticketTypeDTOs);

    return ResponseEntity.ok().build();
  }
}