package com.mortl.dancenetwork.controller;

import com.mortl.dancenetwork.model.Client;
import com.mortl.dancenetwork.model.NewsfeedEntry;
import com.mortl.dancenetwork.repository.NewsfeedEntryRepository;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
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
public class NewsfeedEntryController {

  private final NewsfeedEntryRepository newsfeedEntryRepository;

  public NewsfeedEntryController(NewsfeedEntryRepository newsfeedEntryRepository) {
    this.newsfeedEntryRepository = newsfeedEntryRepository;
  }

  @GetMapping
  public List<NewsfeedEntry> getNewsfeedEntries() {
    return newsfeedEntryRepository.findAll();
  }

  @GetMapping("/{id}")
  public NewsfeedEntry getNewsfeedEntry(@PathVariable Long id) {
    return newsfeedEntryRepository.findById(id).orElseThrow(RuntimeException::new);
  }

  @PostMapping
  public ResponseEntity createNewsfeedEntry(@RequestBody NewsfeedEntry newsfeedEntry) throws URISyntaxException {
    NewsfeedEntry savedNewsfeedEntry = newsfeedEntryRepository.save(newsfeedEntry);
    return ResponseEntity.created(new URI("/newsfeedentries/" + savedNewsfeedEntry.getId())).body(savedNewsfeedEntry);
  }

  @PutMapping("/{id}")
  public ResponseEntity updateNewsfeedEntry(@PathVariable Long id, @RequestBody NewsfeedEntry newsfeedEntry) {
    NewsfeedEntry currentNewsfeedEntry = newsfeedEntryRepository.findById(id).orElseThrow(RuntimeException::new);
    currentNewsfeedEntry.setUserName(newsfeedEntry.getUserName());
    currentNewsfeedEntry.setTextField(newsfeedEntry.getTextField());
    currentNewsfeedEntry.setCreationDate(newsfeedEntry.getCreationDate());
    currentNewsfeedEntry = newsfeedEntryRepository.save(newsfeedEntry);

    return ResponseEntity.ok(currentNewsfeedEntry);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity deleteNewsfeedEntry(@PathVariable Long id) {
    newsfeedEntryRepository.deleteById(id);
    return ResponseEntity.ok().build();
  }
}