package com.mortl.dancenetwork.dto;

public record TicketTypeDTO(
    Long id,
    String name,
    String description,
    Float price,
    Integer contingent,
    Long eventId){
}
