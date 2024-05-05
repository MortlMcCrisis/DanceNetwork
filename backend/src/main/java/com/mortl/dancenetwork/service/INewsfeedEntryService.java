package com.mortl.dancenetwork.service;

import com.mortl.dancenetwork.dto.NewsfeedEntryDTO;
import java.util.List;
import javax.ws.rs.NotFoundException;

public interface INewsfeedEntryService {

  List<NewsfeedEntryDTO> getNewsfeedEntries();

  NewsfeedEntryDTO createNewsfeedEntry(NewsfeedEntryDTO newsfeedEntry);

  NewsfeedEntryDTO updateNewsfeedEntry(NewsfeedEntryDTO newsfeedEntry) throws NotFoundException, IllegalAccessException;

  void deleteNewsfeedEntry(Long id);
}
