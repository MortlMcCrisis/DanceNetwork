package com.mortl.dancenetwork.service.impl;

import com.mortl.dancenetwork.dto.NewsfeedEntryDTO;
import com.mortl.dancenetwork.mapper.NewsfeedEntryMapper;
import com.mortl.dancenetwork.entity.NewsfeedEntry;
import com.mortl.dancenetwork.repository.NewsfeedEntryRepository;
import com.mortl.dancenetwork.model.User;
import com.mortl.dancenetwork.service.INewsfeedEntryService;
import java.util.List;
import javax.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NewsfeedEntryServiceImpl implements INewsfeedEntryService {

  private final NewsfeedEntryRepository newsfeedEntryRepository;

  private final UserServiceImpl userService;

  private final NewsfeedEntryMapper newsfeedEntryMapper;

  @Override
  public List<NewsfeedEntryDTO> getNewsfeedEntries() {
    return newsfeedEntryRepository.findAllOrderByCreationDateDesc().stream()
        .map(dto -> newsfeedEntryMapper.toDTO(dto))
        .toList();
  }

  @Override
  public NewsfeedEntryDTO createNewsfeedEntry(NewsfeedEntryDTO newsfeedEntryDTO) {
    NewsfeedEntry newsfeedEntry = newsfeedEntryMapper.toModel(newsfeedEntryDTO);
    newsfeedEntry.setCreator(userService.getNonNullCurrentUser().uuid());
    NewsfeedEntry savedNewsfeedEntry = newsfeedEntryRepository.saveAndFlush(
        newsfeedEntry);

    return newsfeedEntryMapper.toDTO(savedNewsfeedEntry);
  }

  @Override
  public NewsfeedEntryDTO updateNewsfeedEntry(NewsfeedEntryDTO newsfeedEntry)
      throws NotFoundException, IllegalAccessException {
    NewsfeedEntry currentNewsfeedEntry = newsfeedEntryRepository.findById(newsfeedEntry.id())
        .orElseThrow(NotFoundException::new);

    if(currentNewsfeedEntry.getCreator() != userService.getCurrentUser().get().uuid()){
      throw new IllegalAccessException();
    }

    User currentUser = userService.getNonNullCurrentUser();
    currentNewsfeedEntry.setCreator(currentUser.uuid());
    currentNewsfeedEntry.setType(newsfeedEntry.type());
    currentNewsfeedEntry.setTextField(newsfeedEntry.textField());
    currentNewsfeedEntry.setCreationDate(newsfeedEntry.creationDate());
    currentNewsfeedEntry = newsfeedEntryRepository.save(currentNewsfeedEntry);

    return newsfeedEntryMapper.toDTO(currentNewsfeedEntry);
  }

  @Override
  public void deleteNewsfeedEntry(Long id) {
    newsfeedEntryRepository.deleteById(id);
  }
}
