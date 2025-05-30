package com.mortl.dancenetwork.mapper;

import com.mortl.dancenetwork.dto.NewsfeedEntryDTO;
import com.mortl.dancenetwork.entity.NewsfeedEntry;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NewsfeedEntryMapper
{

  NewsfeedEntryDTO toDTO(NewsfeedEntry newsfeedEntry);

  NewsfeedEntry toEntity(NewsfeedEntryDTO newsfeedEntryDTO);
}