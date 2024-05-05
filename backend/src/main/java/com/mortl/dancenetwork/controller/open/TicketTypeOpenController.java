package com.mortl.dancenetwork.controller.open;

import com.mortl.dancenetwork.dto.TicketTypeDTO;
import com.mortl.dancenetwork.service.ITicketTypeService;
import java.util.List;
import javax.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/open/v1/ticket-types")
@Slf4j
@RequiredArgsConstructor
public class TicketTypeOpenController {

  private final ITicketTypeService ticketTypeService;

  @GetMapping
  public ResponseEntity<List<TicketTypeDTO>> getTicketTypesForEvent(@RequestParam long eventId) {
    log.info("Fetching ticket types for event  " + eventId);

    return ResponseEntity.ok(ticketTypeService.getTicketTypesForEvent(eventId));
  }
}
