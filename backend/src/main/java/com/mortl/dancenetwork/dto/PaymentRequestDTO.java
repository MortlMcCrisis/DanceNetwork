package com.mortl.dancenetwork.dto;

import java.util.List;

public record PaymentRequestDTO(
    List<TicketDTO> tickets) {
}