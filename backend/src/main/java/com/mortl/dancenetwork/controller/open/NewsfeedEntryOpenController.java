package com.mortl.dancenetwork.controller.open;

import com.mortl.dancenetwork.controller.closed.EventClosedController;
import com.mortl.dancenetwork.dto.NewsfeedEntryDTO;
import com.mortl.dancenetwork.service.INewsfeedEntryService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/open/v1/newsfeed-entries")
public class NewsfeedEntryOpenController {

  private static final Logger log = LoggerFactory.getLogger(NewsfeedEntryOpenController.class);

  private final INewsfeedEntryService newsfeedEntryService;

  public NewsfeedEntryOpenController(INewsfeedEntryService newsfeedEntryService) {
    this.newsfeedEntryService = newsfeedEntryService;
  }

  @GetMapping
  public ResponseEntity<List<NewsfeedEntryDTO>> getNewsfeedEntries(){
    log.info("getting open newsfeed entries");
    return ResponseEntity.ok(newsfeedEntryService.getNewsfeedEntries());
  }
}