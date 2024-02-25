package com.mortl.dancenetwork.service.impl;

import com.mortl.dancenetwork.dto.NewsfeedEntryDTO;
import com.mortl.dancenetwork.dto.UserDTO;
import com.mortl.dancenetwork.model.NewsfeedEntry;
import com.mortl.dancenetwork.repository.NewsfeedEntryRepository;
import com.mortl.dancenetwork.entity.User;
import com.mortl.dancenetwork.service.INewsfeedEntryService;
import java.util.List;
import java.util.UUID;
import javax.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NewsfeedEntryServiceImpl implements INewsfeedEntryService {

  private final NewsfeedEntryRepository newsfeedEntryRepository;

  private final UserServiceImpl userService;

  @Override
  public List<Long> getNewsfeedEntries() {
    //TODO sort by date
    return newsfeedEntryRepository.findAllIds();
  }

  public NewsfeedEntryDTO getNewsfeedEntry(Long id) throws NotFoundException {
    NewsfeedEntry newsfeedEntry = newsfeedEntryRepository.findById(id)
        .orElseThrow(NotFoundException::new);
    //TODO refactor to be able to get the real user. This here is wrong and totally shit
    User user = userService.getUser(newsfeedEntry.getCreator()).toEntity(userService.getCurrentUser().uuid());
    return NewsfeedEntryDTO.fromModel(
        newsfeedEntry,
        user
    );
  }

  @Override
  public List<Long> getNewsfeedEntriesForUser(UUID userUUID){
    //TODO respect uuid
    return newsfeedEntryRepository.findAllIds();
  }

  @Override
  public NewsfeedEntryDTO createNewsfeedEntry(NewsfeedEntryDTO newsfeedEntry) {
    User currentUser = userService.getCurrentUser();
    NewsfeedEntry savedNewsfeedEntry = newsfeedEntryRepository.saveAndFlush(
        newsfeedEntry.toModel(
            currentUser.uuid()));

    return NewsfeedEntryDTO.fromModel(
        savedNewsfeedEntry,
        currentUser);
  }

  @Override
  public NewsfeedEntryDTO updateNewsfeedEntry(NewsfeedEntryDTO newsfeedEntry) throws NotFoundException {
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

  @Override
  public void deleteNewsfeedEntry(Long id) {
    newsfeedEntryRepository.deleteById(id);
  }
}
