package com.mortl.dancenetwork.dto;

public enum Sex {
  MALE, FEMALE;

  public static Sex getIfNotNull(String sex){
    if(sex == null){
      return null;
    }

    return Sex.valueOf(sex);
  }
}
