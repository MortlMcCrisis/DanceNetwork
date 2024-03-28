package com.mortl.dancenetwork.dto;

public enum Gender {
  MALE, FEMALE, OTHER;

  public static Gender getIfNotNull(String gender){
    if(gender == null){
      return null;
    }

    return Gender.valueOf(gender);
  }
}
