package com.mortl.dancenetwork.controller.closed;

import com.mortl.dancenetwork.dto.EventStatisticsDTO;
import com.mortl.dancenetwork.service.IAdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/closed/v1/admin")
public class AdminController {

  private IAdminService adminService;

  public AdminController(IAdminService adminService){
    this.adminService = adminService;
  }

  @GetMapping("/event-statistics/{eventId}")
  public ResponseEntity<EventStatisticsDTO> getEventStats(@PathVariable long eventId){
    return ResponseEntity.ok(adminService.getEventStats(eventId));
  }

}
