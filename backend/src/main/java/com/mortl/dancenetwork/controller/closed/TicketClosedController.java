package com.mortl.dancenetwork.controller.closed;

import com.mortl.dancenetwork.dto.TicketInfoDTO;
import com.mortl.dancenetwork.service.ITicketService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/closed/v1/tickets")
@Slf4j
@RequiredArgsConstructor
public class TicketClosedController {

  private final ITicketService ticketService;
  @GetMapping("/infos")
  public ResponseEntity<List<TicketInfoDTO>> getTicketInfosForUser() {
    return ResponseEntity.ok(ticketService.getTicketInfosForUser());
  }

  @GetMapping
  public ResponseEntity<List<TicketInfoDTO>> getTicketInfosForEvent(@RequestParam long eventId) {
    return ResponseEntity.ok(ticketService.getTicketInfosForEvent(eventId));
  }
}
