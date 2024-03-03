package com.mortl.dancenetwork.service;

import com.mortl.dancenetwork.dto.NewsfeedEntryDTO;
import com.mortl.dancenetwork.entity.User;
import com.mortl.dancenetwork.model.Event;
import java.util.List;
import java.util.UUID;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

public interface INewsfeedEntryService {

  List<Long> getNewsfeedEntries();

  NewsfeedEntryDTO getNewsfeedEntry(Long id);

  List<Long> getNewsfeedEntriesForUser(UUID userUUID);

  NewsfeedEntryDTO createNewsfeedEntry(NewsfeedEntryDTO newsfeedEntry);

  NewsfeedEntryDTO updateNewsfeedEntry(NewsfeedEntryDTO newsfeedEntry) throws NotFoundException;

  void deleteNewsfeedEntry(Long id);
}
