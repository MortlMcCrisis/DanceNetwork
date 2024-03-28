package com.mortl.dancenetwork.dto;

import java.util.Map;

public record PaymentRequestDTO(Map<Long, PersonalTicketDataDTO> personalData) {
}