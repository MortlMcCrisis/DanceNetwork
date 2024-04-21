package com.mortl.dancenetwork.mapper;

import com.mortl.dancenetwork.dto.UserDTO;
import com.mortl.dancenetwork.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

  UserDTO toDTO(User user);

  User toEntity(UserDTO userDTO);
}