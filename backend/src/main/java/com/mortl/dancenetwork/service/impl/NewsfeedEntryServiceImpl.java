package com.mortl.dancenetwork.service.impl;

import com.mortl.dancenetwork.entity.NewsfeedEntry;
import com.mortl.dancenetwork.model.User;
import com.mortl.dancenetwork.repository.NewsfeedEntryRepository;
import com.mortl.dancenetwork.service.INewsfeedEntryService;
import com.mortl.dancenetwork.service.IUserService;
import java.util.List;
import javax.ws.rs.NotFoundException;
import org.springframework.stereotype.Component;

@Component
public class NewsfeedEntryServiceImpl implements INewsfeedEntryService
{

  private final NewsfeedEntryRepository newsfeedEntryRepository;

  private final IUserService userService;

  public NewsfeedEntryServiceImpl(NewsfeedEntryRepository newsfeedEntryRepository,
      IUserService userService)
  {
    this.newsfeedEntryRepository = newsfeedEntryRepository;
    this.userService = userService;
  }

  @Override
  public List<NewsfeedEntry> getNewsfeedEntries()
  {
    return newsfeedEntryRepository.findAllOrderByCreationDateDesc();
  }

  @Override
  public NewsfeedEntry createNewsfeedEntry(NewsfeedEntry newsfeedEntry)
  {
    newsfeedEntry.setCreator(userService.getNonNullCurrentUser().getUuid());
    NewsfeedEntry savedNewsfeedEntry = newsfeedEntryRepository.saveAndFlush(
        newsfeedEntry);

    return savedNewsfeedEntry;
  }

  @Override
  public NewsfeedEntry updateNewsfeedEntry(NewsfeedEntry newsfeedEntry)
      throws NotFoundException, IllegalAccessException
  {
    NewsfeedEntry currentNewsfeedEntry = newsfeedEntryRepository.findById(newsfeedEntry.getId())
        .orElseThrow(NotFoundException::new);

    if (currentNewsfeedEntry.getCreator() != userService.getCurrentUser().get().getUuid())
    {
      throw new IllegalAccessException();
    }

    User currentUser = userService.getNonNullCurrentUser();
    currentNewsfeedEntry.setCreator(currentUser.getUuid());
    currentNewsfeedEntry.setType(newsfeedEntry.getType());
    currentNewsfeedEntry.setTextField(newsfeedEntry.getTextField());
    currentNewsfeedEntry.setCreationDate(newsfeedEntry.getCreationDate());
    currentNewsfeedEntry = newsfeedEntryRepository.save(currentNewsfeedEntry);

    return currentNewsfeedEntry;
  }

  @Override
  public void deleteNewsfeedEntry(Long id)
  {
    newsfeedEntryRepository.deleteById(id);
  }
}
