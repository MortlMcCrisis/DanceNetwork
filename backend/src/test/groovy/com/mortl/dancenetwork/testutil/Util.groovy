package com.mortl.dancenetwork.testutil

import com.mortl.dancenetwork.enums.Gender
import com.mortl.dancenetwork.enums.Role
import com.mortl.dancenetwork.entity.Event
import com.mortl.dancenetwork.entity.Ticket
import com.mortl.dancenetwork.entity.TicketType

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class Util {

    static def createTestEvent(boolean published, LocalDate startDate = LocalDate.now(), String name = "test") {
        new Event(
                null,
                UUID.randomUUID(),
                null,
                null,
                startDate,
                LocalTime.NOON,
                startDate.plusDays(2),
                name,
                "test",
                "test",
                "test@test",
                published,
                LocalDateTime.now()
        )
    }

    static def createTestTicketType(Event event){
        createTestTicketType(null, "mock name", 100L, event)
    }

    static def createTestTicketType(Long id, String name, long contingent, Event event){
        new TicketType(id, name, "Cranc ticket", 100.00, contingent, event)
    }

    static def createTestTicket(UUID ownerUuid, TicketType ticketType, Gender gender = Gender.MALE){
        createTestTicket(ownerUuid, ticketType, gender, Role.BOTH, LocalDateTime.now())
    }

    static def createTestTicket(UUID ownerUuid, TicketType ticketType, Role role){
        createTestTicket(ownerUuid, ticketType, Gender.MALE, role, LocalDateTime.now())
    }

    static def createTestTicket(UUID ownerUuid, TicketType ticketType, LocalDateTime buyDate){
        createTestTicket(ownerUuid, ticketType, Gender.MALE, Role.BOTH, buyDate)
    }

    static def createTestTicket(UUID ownerUuid, TicketType ticketType, Gender gender, Role role, LocalDateTime buyDate){
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
