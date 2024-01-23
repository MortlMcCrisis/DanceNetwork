package com.mortl.dancenetwork.service;

import com.mortl.dancenetwork.dto.NewsfeedEntryDTO;
import com.mortl.dancenetwork.model.NewsfeedEntry;
import com.mortl.dancenetwork.repository.NewsfeedEntryRepository;
import com.mortl.dancenetwork.user.User;
import com.mortl.dancenetwork.user.UserUtil;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NewsfeedEntryService {

  private final NewsfeedEntryRepository newsfeedEntryRepository;

  private final UserUtil userUtil;

  public List<NewsfeedEntryDTO> getNewsfeedEntries() {
    return newsfeedEntryRepository.findAll().stream()
        .map(newsfeedEntry ->
        {
          User user = userUtil.fetchUser(newsfeedEntry.getCreator());
          return NewsfeedEntryDTO.fromModel(
              newsfeedEntry,
              user.name(),
              user.sex()
          );
        })
        .toList();
  }

  public NewsfeedEntryDTO createNewsfeedEntry(NewsfeedEntryDTO newsfeedEntry) {
    User currentUser = userUtil.getCurrentUser();
    NewsfeedEntry savedNewsfeedEntry = newsfeedEntryRepository.save(
        newsfeedEntry.toModel(
            currentUser.uuid()));

    userUtil.addUserAttribute("sex", "MALE");

    return NewsfeedEntryDTO.fromModel(
        savedNewsfeedEntry,
        currentUser.name(),
        currentUser.sex());
  }

  public NewsfeedEntryDTO updateNewsfeedEntry(NewsfeedEntryDTO newsfeedEntry) throws NotFoundException{
    //TODO only the owner of a user should be able to update an entry
    NewsfeedEntry currentNewsfeedEntry = newsfeedEntryRepository.findById(newsfeedEntry.id())
        .orElseThrow(NotFoundException::new);
    User currentUser = userUtil.getCurrentUser();
    currentNewsfeedEntry.setCreator(currentUser.uuid());
    currentNewsfeedEntry.setTextField(newsfeedEntry.textField());
    currentNewsfeedEntry.setCreationDate(newsfeedEntry.creationDate());
    currentNewsfeedEntry = newsfeedEntryRepository.save(currentNewsfeedEntry);

    return NewsfeedEntryDTO.fromModel(
        currentNewsfeedEntry,
        currentUser.name(),
        currentUser.sex());
  }

  public void deleteNewsfeedEntry(Long id) {
    newsfeedEntryRepository.deleteById(id);
  }
}
