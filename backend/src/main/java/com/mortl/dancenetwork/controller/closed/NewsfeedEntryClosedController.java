package com.mortl.dancenetwork.controller.closed;

import com.mortl.dancenetwork.dto.NewsfeedEntryDTO;
import com.mortl.dancenetwork.service.INewsfeedEntryService;
import java.net.URI;
import java.net.URISyntaxException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/closed/v1/newsfeed-entries")
@Slf4j
@RequiredArgsConstructor
public class NewsfeedEntryClosedController {

  private final INewsfeedEntryService newsfeedEntryService;

  @PostMapping
  public ResponseEntity createNewsfeedEntry(@RequestBody NewsfeedEntryDTO newsfeedEntryDTO) throws URISyntaxException {
    NewsfeedEntryDTO savedNewsfeedEntry = newsfeedEntryService.createNewsfeedEntry(newsfeedEntryDTO);
    return ResponseEntity.created(new URI("/newsfeedentries/" + savedNewsfeedEntry.id())).body(savedNewsfeedEntry);
  }

  //TODO gleichziehen: ResponseEntity oder direkt das objekt überall zurück geben
  @PutMapping("/{id}")
  public ResponseEntity updateNewsfeedEntry(@PathVariable Long id, @RequestBody NewsfeedEntryDTO newsfeedEntry) throws NotFoundException {
    if( id != newsfeedEntry.id()){
      throw new IllegalArgumentException("id in path (" + id + ") does not match the id of the object (" + newsfeedEntry.id() + ").");
    }
    return ResponseEntity.ok(newsfeedEntryService.updateNewsfeedEntry(newsfeedEntry));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity deleteNewsfeedEntry(@PathVariable Long id) {
    newsfeedEntryService.deleteNewsfeedEntry(id);
    return ResponseEntity.ok().build();
  }
}