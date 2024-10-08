package com.mortl.dancenetwork.mapper;

import com.mortl.dancenetwork.dto.UserDTO;
import com.mortl.dancenetwork.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper
{

  UserDTO toDTO(User user);

  @Mapping(target = "email", ignore = true)
  User toModel(UserDTO userDTO);
}