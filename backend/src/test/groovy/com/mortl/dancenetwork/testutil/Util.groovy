package com.mortl.dancenetwork.testutil


import com.mortl.dancenetwork.entity.Event
import com.mortl.dancenetwork.entity.Ticket
import com.mortl.dancenetwork.entity.TicketOrder
import com.mortl.dancenetwork.entity.TicketType
import com.mortl.dancenetwork.enums.DancingRole
import com.mortl.dancenetwork.enums.Gender
import org.javamoney.moneta.Money

import javax.money.Monetary
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
                Money.of(100, Monetary.getCurrency("EUR")),
                100,
                createTestEvent(true, eventId)
        )
    }

    static def createTestTicketType(Event event) {
        createTestTicketType(null, "mock name", 100L, event)
    }

    static def createTestTicketType(Long id, String name, long contingent, Event event) {
        new TicketType(id, name, "Cranc ticket", Money.of(100, Monetary.getCurrency("EUR")), contingent, event)
    }

    static def createTestTicket(UUID ownerUuid, TicketType ticketType, Gender gender, TicketOrder order) {
        createTestTicket(ownerUuid, ticketType, gender, DancingRole.BOTH, order)
    }

    static def createTestTicket(UUID ownerUuid, TicketType ticketType, DancingRole role, TicketOrder order) {
        createTestTicket(ownerUuid, ticketType, Gender.MALE, role, order)
    }

    static def createTestTicket(UUID ownerUuid, TicketType ticketType, Gender gender, DancingRole role, TicketOrder order) {
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
                order)
    }
}
