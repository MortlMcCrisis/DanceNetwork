package com.mortl.dancenetwork.entity;

import com.mortl.dancenetwork.dto.Gender;
import java.util.UUID;
import lombok.Builder;

@Builder
public record User(UUID uuid, String photoPath, String username, String firstName, String lastName, Gender gender, String phone) {

}
