package com.mortl.dancenetwork.service;

import com.mortl.dancenetwork.dto.NewsfeedEntryDTO;
import java.util.List;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

public interface INewsfeedEntryService {

  List<NewsfeedEntryDTO> getNewsfeedEntries();

  NewsfeedEntryDTO createNewsfeedEntry(NewsfeedEntryDTO newsfeedEntry);

  NewsfeedEntryDTO updateNewsfeedEntry(NewsfeedEntryDTO newsfeedEntry) throws NotFoundException;

  void deleteNewsfeedEntry(Long id);
}
