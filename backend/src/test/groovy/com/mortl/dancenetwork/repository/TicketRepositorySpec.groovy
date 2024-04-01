package com.mortl.dancenetwork.repository

import com.mortl.dancenetwork.model.Event
import com.mortl.dancenetwork.model.Ticket
import com.mortl.dancenetwork.model.TicketType
import com.mortl.dancenetwork.testutil.Util
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

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

    def "test findByOwner no content"(){
        expect:
        ticketRepository.findByOwner(UUID.randomUUID()).size() == 0
    }

    def "test findByOwner"(){
        when:
        UUID ownerUuid = UUID.randomUUID()
        Event event = createTestEvent()
        TicketType ticketType = ticketTypeRepository.saveAndFlush(Util.createTestTicketType(event))

        Ticket ticket1 = createTestTicket(ownerUuid, ticketType)
        Ticket ticket2 = createTestTicket(ownerUuid, ticketType)
        createTestTicket(UUID.randomUUID(), ticketType)

        List<Ticket> tickets = ticketRepository.findByOwner(ownerUuid)

        then:
        tickets.size() == 2
        tickets.contains(ticket1)
        tickets.contains(ticket2)
        ticketRepository.findByOwner(UUID.randomUUID()).isEmpty()
    }

    def createTestEvent() {
        eventRepository.saveAndFlush(Util.createTestEvent(true))
    }

    def createTestTicket(UUID ownerUuid, TicketType ticketType){
        ticketRepository.saveAndFlush(Util.createTestTicket(ownerUuid, ticketType))
    }
}
