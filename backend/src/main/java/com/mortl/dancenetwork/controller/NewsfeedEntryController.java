package com.mortl.dancenetwork.controller;

import com.mortl.dancenetwork.dto.NewsfeedEntryDTO;
import com.mortl.dancenetwork.service.NewsfeedEntryService;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("/api/v1/newsfeed-entries")
@RequestMapping("/newsfeed-entries")
@Slf4j
@RequiredArgsConstructor
public class NewsfeedEntryController {

  private final NewsfeedEntryService newsfeedEntryService;

  @GetMapping
  public List<NewsfeedEntryDTO> getNewsfeedEntries() {
    return newsfeedEntryService.getNewsfeedEntries();
  }

  @GetMapping("/{id}")
  public NewsfeedEntryDTO getNewsfeedEntry(@PathVariable Long id) throws NotFoundException {
    return newsfeedEntryService.getNewsfeedEntry(id);
  }

  @PostMapping
  public ResponseEntity createNewsfeedEntry(@RequestBody NewsfeedEntryDTO newsfeedEntry) throws URISyntaxException {
    NewsfeedEntryDTO savedNewsfeedEntry = newsfeedEntryService.createNewsfeedEntry(newsfeedEntry);
    return ResponseEntity.created(new URI("/newsfeedentries/" + savedNewsfeedEntry.id())).body(savedNewsfeedEntry);
  }

  @PutMapping("/{id}")
  public ResponseEntity updateNewsfeedEntry(@PathVariable Long id, @RequestBody NewsfeedEntryDTO newsfeedEntry) throws NotFoundException {
    if( id != newsfeedEntry.id()){
      throw new IllegalArgumentException("id in path (" + id + ") does not match the id of the object (" + newsfeedEntry.id() + ").");
    }
    return ResponseEntity.ok(newsfeedEntryService.updateNewsfeedEntry(id, newsfeedEntry));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity deleteNewsfeedEntry(@PathVariable Long id) {
    newsfeedEntryService.deleteNewsfeedEntry(id);
    return ResponseEntity.ok().build();
  }
}