package com.mortl.dancenetwork.repository

import com.mortl.dancenetwork.entity.Event
import com.mortl.dancenetwork.entity.Ticket
import com.mortl.dancenetwork.entity.TicketType
import com.mortl.dancenetwork.testutil.Util
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import java.time.LocalDate

@SpringBootTest
class TicketRepositorySpec extends Specification{

    @Autowired
    EventRepository eventRepository

    @Autowired
    TicketTypeRepository ticketTypeRepository

    @Autowired
    TicketRepository ticketRepository

    def setup() {
        cleanup()
    }

    def cleanup() {
        ticketRepository.deleteAll()
        ticketTypeRepository.deleteAll()
        eventRepository.deleteAll()
    }

    def "test findByOwnerOrderByEventStartDateAsc no content"(){
        expect:
        ticketRepository.findByOwnerOrderByEventStartDateAsc(UUID.randomUUID()).size() == 0
    }

    def "test findByOwnerOrderByEventStartDateAsc wrong uid"(){
        when:
        UUID ownerUuid = UUID.randomUUID()
        Event event = createTestEvent()
        TicketType ticketType = ticketTypeRepository.saveAndFlush(Util.createTestTicketType(event))

        createTestTicket(ownerUuid, ticketType)

        then:
        ticketRepository.findByOwnerOrderByEventStartDateAsc(UUID.randomUUID()).size() == 0
    }

    def "test findByOwnerOrderByEventStartDateAsc only tickets of owner selected"(){
        when:
        UUID ownerUuid = UUID.randomUUID()
        Event event = createTestEvent()
        TicketType ticketType = ticketTypeRepository.saveAndFlush(Util.createTestTicketType(event))

        Ticket ticket1 = createTestTicket(ownerUuid, ticketType)
        Ticket ticket2 = createTestTicket(ownerUuid, ticketType)
        createTestTicket(UUID.randomUUID(), ticketType)

        List<Ticket> tickets = ticketRepository.findByOwnerOrderByEventStartDateAsc(ownerUuid)

        then:
        tickets.size() == 2
        tickets.contains(ticket1)
        tickets.contains(ticket2)
    }

    def "test findByOwnerOrderByEventStartDateAsc correct order"(){
        when:
        UUID ownerUuid = UUID.randomUUID()
        Event event1 = createTestEvent(LocalDate.now().plusDays(2))
        TicketType ticketType1 = ticketTypeRepository.saveAndFlush(Util.createTestTicketType(event1))
        Event event2 = createTestEvent(LocalDate.now().plusDays(1))
        TicketType ticketType2 = ticketTypeRepository.saveAndFlush(Util.createTestTicketType(event2))

        Ticket ticket1 = createTestTicket(ownerUuid, ticketType1)
        Ticket ticket2 = createTestTicket(ownerUuid, ticketType2)

        List<Ticket> tickets = ticketRepository.findByOwnerOrderByEventStartDateAsc(ownerUuid)

        then:
        tickets.get(0).getId() == ticket2.getId()
        tickets.get(1).getId() == ticket1.getId()
    }

    def createTestEvent(LocalDate startDate = LocalDate.now()) {
        eventRepository.saveAndFlush(Util.createTestEvent(true, startDate))
    }

    def createTestTicket(UUID ownerUuid, TicketType ticketType){
        ticketRepository.saveAndFlush(Util.createTestTicket(ownerUuid, ticketType))
    }
}
