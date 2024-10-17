package com.mortl.dancenetwork.controller.closed;

import com.mortl.dancenetwork.dto.TicketTypeDTO;
import com.mortl.dancenetwork.dto.UpdateTicketTypeRequestDTO;
import com.mortl.dancenetwork.entity.TicketType;
import com.mortl.dancenetwork.mapper.TicketTypeMapper;
import com.mortl.dancenetwork.service.ITicketTypeService;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/closed/v1/ticket-types")
public class TicketTypeClosedController
{

  private static final Logger log = LoggerFactory.getLogger(TicketTypeClosedController.class);

  private final ITicketTypeService ticketTypeService;

  private final TicketTypeMapper ticketTypeMapper;

  public TicketTypeClosedController(ITicketTypeService ticketTypeService,
      TicketTypeMapper ticketTypeMapper)
  {
    this.ticketTypeService = ticketTypeService;
    this.ticketTypeMapper = ticketTypeMapper;
  }

  @PutMapping
  public ResponseEntity<List<TicketTypeDTO>> updateTicketTypes(
      @RequestBody UpdateTicketTypeRequestDTO updateTicketTypeRequestDTO)
  {
    String ids = updateTicketTypeRequestDTO.ticketTypeDTOs().stream()
        .map(TicketTypeDTO::id)
        .map(String::valueOf)
        .collect(Collectors.joining(", "));

    log.info("Updating ticket types " + ids);

    List<TicketType> ticketTypes = ticketTypeService.updateTicketTypes(
        updateTicketTypeRequestDTO.eventId(),
        updateTicketTypeRequestDTO.ticketTypeDTOs().stream()
            .map(ticketTypeMapper::toEntity)
            .toList());
    return ResponseEntity.ok(ticketTypes.stream().map(ticketTypeMapper::toDTO).toList());
  }
}