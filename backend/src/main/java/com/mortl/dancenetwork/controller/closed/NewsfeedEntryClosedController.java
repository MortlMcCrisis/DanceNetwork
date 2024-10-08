package com.mortl.dancenetwork.controller.closed;

import com.mortl.dancenetwork.dto.NewsfeedEntryDTO;
import com.mortl.dancenetwork.entity.NewsfeedEntry;
import com.mortl.dancenetwork.mapper.NewsfeedEntryMapper;
import com.mortl.dancenetwork.service.INewsfeedEntryService;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import javax.ws.rs.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping("/api/closed/v1/newsfeed-entries")
public class NewsfeedEntryClosedController
{

  private static final Logger log = LoggerFactory.getLogger(NewsfeedEntryClosedController.class);

  private final INewsfeedEntryService newsfeedEntryService;

  private final NewsfeedEntryMapper newsfeedEntryMapper;

  public NewsfeedEntryClosedController(INewsfeedEntryService newsfeedEntryService,
      NewsfeedEntryMapper newsfeedEntryMapper)
  {
    this.newsfeedEntryService = newsfeedEntryService;
    this.newsfeedEntryMapper = newsfeedEntryMapper;
  }

  @GetMapping
  public ResponseEntity<List<NewsfeedEntryDTO>> getNewsfeedEntries()
  {
    log.info("getting closed newsfeed entries");
    return ResponseEntity.ok(newsfeedEntryService.getNewsfeedEntries().stream()
        .map(newsfeedEntryMapper::toDTO)
        .toList());
  }

  @PostMapping
  public ResponseEntity<NewsfeedEntryDTO> createNewsfeedEntry(
      @RequestBody NewsfeedEntryDTO newsfeedEntryDTO)
      throws URISyntaxException
  {
    NewsfeedEntry savedNewsfeedEntry = newsfeedEntryService.createNewsfeedEntry(
        newsfeedEntryMapper.toEntity(newsfeedEntryDTO));
    return ResponseEntity.created(new URI("/newsfeedentries/" + savedNewsfeedEntry.getId()))
        .body(newsfeedEntryMapper.toDTO(savedNewsfeedEntry));
  }

  @PutMapping("/{id}")
  public ResponseEntity<NewsfeedEntryDTO> updateNewsfeedEntry(@PathVariable Long id,
      @RequestBody NewsfeedEntryDTO newsfeedEntryDTO)
      throws NotFoundException, IllegalAccessException
  {
    if (id != newsfeedEntryDTO.id())
    {
      throw new IllegalArgumentException(
          "id in path (" + id + ") does not match the id of the object (" + newsfeedEntryDTO.id()
              + ").");
    }
    NewsfeedEntry newsfeedEntry = newsfeedEntryService.updateNewsfeedEntry(
        newsfeedEntryMapper.toEntity(newsfeedEntryDTO));
    return ResponseEntity.ok(newsfeedEntryMapper.toDTO(newsfeedEntry));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteNewsfeedEntry(@PathVariable Long id)
  {
    newsfeedEntryService.deleteNewsfeedEntry(id);
    return ResponseEntity.ok().build();
  }
}