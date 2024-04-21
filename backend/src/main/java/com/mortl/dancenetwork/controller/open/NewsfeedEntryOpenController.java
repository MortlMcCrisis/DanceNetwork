package com.mortl.dancenetwork.controller.open;

import com.mortl.dancenetwork.dto.NewsfeedEntryDTO;
import com.mortl.dancenetwork.service.INewsfeedEntryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/open/v1/newsfeed-entries")
@Slf4j
@RequiredArgsConstructor
public class NewsfeedEntryOpenController {

  private final INewsfeedEntryService newsfeedEntryService;

  @GetMapping
  public List<NewsfeedEntryDTO> getNewsfeedEntries(){
    log.info("getting newsfeed entries");
    return newsfeedEntryService.getNewsfeedEntries();
  }
}