package com.mortl.dancenetwork.dto;

//TODO remove
public record PersonalTicketDataDTO(
    String firstName,
    String lastName,
    String address,
    String country,
    String email,
    String phone,
    Role role,
    Gender gender) {

}
