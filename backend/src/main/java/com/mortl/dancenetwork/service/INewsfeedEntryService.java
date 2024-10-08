package com.mortl.dancenetwork.service;

import com.mortl.dancenetwork.entity.NewsfeedEntry;
import java.util.List;
import javax.ws.rs.NotFoundException;

public interface INewsfeedEntryService
{

  List<NewsfeedEntry> getNewsfeedEntries();

  NewsfeedEntry createNewsfeedEntry(NewsfeedEntry newsfeedEntry);

  NewsfeedEntry updateNewsfeedEntry(NewsfeedEntry newsfeedEntry)
      throws NotFoundException, IllegalAccessException;

  void deleteNewsfeedEntry(Long id);
}
