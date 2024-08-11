package com.mortl.dancenetwork.testutil

import com.mortl.dancenetwork.dto.Gender
import com.mortl.dancenetwork.dto.Role
import com.mortl.dancenetwork.model.Event
import com.mortl.dancenetwork.model.Ticket
import com.mortl.dancenetwork.model.TicketType

import java.time.LocalDate
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
                published)
    }

    static def createTestTicketType(Event event){
        new TicketType(null, "Ticket name", "Cranc ticket", 100.00, event)
    }

    static def createTestTicket(UUID ownerUuid, TicketType ticketType){
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
                Role.LEADER,
                Gender.MALE)
    }
}
