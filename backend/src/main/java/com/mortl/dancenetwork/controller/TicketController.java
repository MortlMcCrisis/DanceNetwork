package com.mortl.dancenetwork.controller;

import com.mortl.dancenetwork.dto.TicketInfoDTO;
import com.mortl.dancenetwork.service.ITicketService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tickets")
@Slf4j
@RequiredArgsConstructor
public class TicketController {

  private final ITicketService ticketService;
  @GetMapping("/infos")
  public List<TicketInfoDTO> getTicketInfosForUser() {
    return ticketService.getTicketInfosForUser();
  }
}
