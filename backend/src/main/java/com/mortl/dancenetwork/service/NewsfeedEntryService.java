package com.mortl.dancenetwork.service;

import com.mortl.dancenetwork.dto.NewsfeedEntryDTO;
import com.mortl.dancenetwork.model.NewsfeedEntry;
import com.mortl.dancenetwork.repository.NewsfeedEntryRepository;
import com.mortl.dancenetwork.entity.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NewsfeedEntryService {
  //TODO introduce interfaces for all services

  private final NewsfeedEntryRepository newsfeedEntryRepository;

  private final UserService userService;

  public List<NewsfeedEntryDTO> getNewsfeedEntries() {
    return newsfeedEntryRepository.findAllByOrderByCreationDateDesc().stream()
        .map(newsfeedEntry ->
        {
          User user = userService.getUser(newsfeedEntry.getCreator());
          return NewsfeedEntryDTO.fromModel(
              newsfeedEntry,
              user
          );
        })
        .toList();
  }

  public NewsfeedEntryDTO createNewsfeedEntry(NewsfeedEntryDTO newsfeedEntry) {
    User currentUser = userService.getCurrentUser();
    NewsfeedEntry savedNewsfeedEntry = newsfeedEntryRepository.saveAndFlush(
        newsfeedEntry.toModel(
            currentUser.uuid()));

    return NewsfeedEntryDTO.fromModel(
        savedNewsfeedEntry,
        currentUser);
  }

  public NewsfeedEntryDTO updateNewsfeedEntry(NewsfeedEntryDTO newsfeedEntry) throws NotFoundException{
    //TODO only the owner of a user should be able to update an entry
    NewsfeedEntry currentNewsfeedEntry = newsfeedEntryRepository.findById(newsfeedEntry.id())
        .orElseThrow(NotFoundException::new);
    User currentUser = userService.getCurrentUser();
    currentNewsfeedEntry.setCreator(currentUser.uuid());
    currentNewsfeedEntry.setTextField(newsfeedEntry.textField());
    currentNewsfeedEntry.setCreationDate(newsfeedEntry.creationDate());
    currentNewsfeedEntry = newsfeedEntryRepository.save(currentNewsfeedEntry);

    return NewsfeedEntryDTO.fromModel(
        currentNewsfeedEntry,
        currentUser);
  }

  public void deleteNewsfeedEntry(Long id) {
    newsfeedEntryRepository.deleteById(id);
  }
}
