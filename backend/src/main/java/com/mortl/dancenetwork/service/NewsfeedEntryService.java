package com.mortl.dancenetwork.service;

import com.mortl.dancenetwork.controller.NewsfeedEntryController;
import com.mortl.dancenetwork.dto.NewsfeedEntryDTO;
import com.mortl.dancenetwork.model.NewsfeedEntry;
import com.mortl.dancenetwork.repository.NewsfeedEntryRepository;
import com.mortl.dancenetwork.util.AuthUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@RequiredArgsConstructor
public class NewsfeedEntryService {

  private final NewsfeedEntryRepository newsfeedEntryRepository;

  private final AuthUtil authUtil;

  public List<NewsfeedEntryDTO> getNewsfeedEntries() {
    return newsfeedEntryRepository.findAll().stream()
        .map(newsfeedEntry -> NewsfeedEntryDTO.fromModel(newsfeedEntry))
        .toList();
  }

  public NewsfeedEntryDTO getNewsfeedEntry(Long id) throws NotFoundException{
    NewsfeedEntry newsfeedEntry = newsfeedEntryRepository.findById(id)
        .orElseThrow(NotFoundException::new);

    return NewsfeedEntryDTO.fromModel(newsfeedEntry);
  }

  public NewsfeedEntryDTO createNewsfeedEntry(NewsfeedEntryDTO newsfeedEntry) {
    NewsfeedEntry savedNewsfeedEntry = newsfeedEntryRepository.save(newsfeedEntry.toModel(authUtil.getUsername()));
    return NewsfeedEntryDTO.fromModel(savedNewsfeedEntry);
  }

  public NewsfeedEntryDTO updateNewsfeedEntry(Long id, NewsfeedEntryDTO newsfeedEntry) throws NotFoundException{
    NewsfeedEntry currentNewsfeedEntry = newsfeedEntryRepository.findById(id)
        .orElseThrow(NotFoundException::new);
    currentNewsfeedEntry.setUserName(newsfeedEntry.userName());
    currentNewsfeedEntry.setTextField(newsfeedEntry.textField());
    currentNewsfeedEntry.setCreationDate(newsfeedEntry.creationDate());
    currentNewsfeedEntry = newsfeedEntryRepository.save(currentNewsfeedEntry);

    return NewsfeedEntryDTO.fromModel(currentNewsfeedEntry);
  }

  public void deleteNewsfeedEntry(Long id) {
    newsfeedEntryRepository.deleteById(id);
  }
}
