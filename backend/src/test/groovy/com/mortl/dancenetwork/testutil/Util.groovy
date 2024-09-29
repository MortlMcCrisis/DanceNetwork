package com.mortl.dancenetwork.testutil

import com.mortl.dancenetwork.dto.TicketTypeDTO
import com.mortl.dancenetwork.entity.Event
import com.mortl.dancenetwork.entity.Ticket
import com.mortl.dancenetwork.entity.TicketType
import com.mortl.dancenetwork.enums.DancingRole
import com.mortl.dancenetwork.enums.Gender

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class Util {

    //TODO rewrite and make this more elegant
    static def createTestEvent(boolean published, Long id = null, LocalDate startDate = LocalDate.now(), String name = "test") {
        new Event(
                id,
                name,
                UUID.randomUUID(),
                "test@test",
                startDate,
                LocalTime.NOON,
                startDate.plusDays(2),
                null,
                null,
                "test",
                "test",
                published,
                LocalDateTime.now()
        )
    }

    static def createTestTicketType(long id, long eventId) {
        new TicketType(
                id,
                "name",
                "description",
                100.00f,
                100,
                createTestEvent(true, eventId)
        )
    }

    static def createTestTicketTypeDto(long id, long eventId) {
        new TicketTypeDTO(
                id,
                "name",
                "description",
                100.00f,
                100,
                eventId
        )
    }

    static def createTestTicketType(Event event) {
        createTestTicketType(null, "mock name", 100L, event)
    }

    static def createTestTicketType(Long id, String name, long contingent, Event event) {
        new TicketType(id, name, "Cranc ticket", 100.00, contingent, event)
    }

    static def createTestTicket(UUID ownerUuid, TicketType ticketType, Gender gender = Gender.MALE) {
        createTestTicket(ownerUuid, ticketType, gender, DancingRole.BOTH, LocalDateTime.now())
    }

    static def createTestTicket(UUID ownerUuid, TicketType ticketType, DancingRole role) {
        createTestTicket(ownerUuid, ticketType, Gender.MALE, role, LocalDateTime.now())
    }

    static def createTestTicket(UUID ownerUuid, TicketType ticketType, LocalDateTime buyDate) {
        createTestTicket(ownerUuid, ticketType, Gender.MALE, DancingRole.BOTH, buyDate)
    }

    static def createTestTicket(UUID ownerUuid, TicketType ticketType, Gender gender, DancingRole role, LocalDateTime buyDate) {
        new Ticket(
                null,
                ownerUuid,
                ticketType,
                "Jan",
                "Mortensen",
                "Augustinerweg 18, 79098 Freiburg",
                "Germany",
                "janmorti@gmx.de",
                "0160 7574886",
                role,
                gender,
                buyDate)
    }
}
