package com.mortl.dancenetwork.mapper;

import com.mortl.dancenetwork.dto.NewsfeedEntryDTO;
import com.mortl.dancenetwork.model.NewsfeedEntry;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NewsfeedEntryMapper {

  NewsfeedEntryDTO toDTO(NewsfeedEntry newsfeedEntry);

  NewsfeedEntry toModel(NewsfeedEntryDTO newsfeedEntryDTO);
}