package com.mortl.dancenetwork.model;

import com.mortl.dancenetwork.enums.Gender;
import java.util.UUID;
import lombok.Builder;

//TODO enforce uniqueness of username?
@Builder
public record User(UUID uuid, String email, String photoPath, String username, String firstName, String lastName, Gender gender, String phone) {

}
