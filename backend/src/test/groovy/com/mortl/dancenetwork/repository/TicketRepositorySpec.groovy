package com.mortl.dancenetwork.repository

import com.mortl.dancenetwork.entity.Event
import com.mortl.dancenetwork.entity.Ticket
import com.mortl.dancenetwork.entity.TicketOrder
import com.mortl.dancenetwork.entity.TicketType
import com.mortl.dancenetwork.enums.DancingRole
import com.mortl.dancenetwork.enums.Gender
import com.mortl.dancenetwork.testutil.Util
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import spock.lang.Specification

import java.time.LocalDate
import java.time.LocalDateTime

@DataJpaTest
class TicketRepositorySpec extends Specification {

    @Autowired
    EventRepository eventRepository

    @Autowired
    TicketOrderRepository orderRepository

    @Autowired
    TicketTypeRepository ticketTypeRepository

    @Autowired
    TicketRepository ticketRepository

    def "test findByOwnerOrderByEventStartDateAsc no content"() {
        expect:
        ticketRepository.findByOwnerOrderByEventStartDateAsc(UUID.randomUUID()).size() == 0
    }

    def "test findByOwnerOrderByEventStartDateAsc wrong uid"() {
        given:
        UUID ownerUuid = UUID.randomUUID()
        Event event = createTestEvent()
        TicketOrder ticketOrder = createTestOrder()
        TicketType ticketType = ticketTypeRepository.saveAndFlush(Util.createTestTicketType(event))

        createTestTicket(ownerUuid, ticketType, ticketOrder)

        expect:
        ticketRepository.findByOwnerOrderByEventStartDateAsc(UUID.randomUUID()).size() == 0
    }

    def "test findByOwnerOrderByEventStartDateAsc only tickets of owner selected"() {
        given:
        UUID ownerUuid = UUID.randomUUID()
        Event event = createTestEvent()
        TicketOrder ticketOrder = createTestOrder()
        TicketType ticketType = ticketTypeRepository.saveAndFlush(Util.createTestTicketType(event))

        Ticket ticket1 = createTestTicket(ownerUuid, ticketType, ticketOrder)
        Ticket ticket2 = createTestTicket(ownerUuid, ticketType, ticketOrder)
        createTestTicket(UUID.randomUUID(), ticketType, ticketOrder)

        when:
        List<Ticket> tickets = ticketRepository.findByOwnerOrderByEventStartDateAsc(ownerUuid)

        then:
        tickets.size() == 2
        ticket1.owner == ownerUuid
        ticket2.owner == ownerUuid
    }

    def "test findByOwnerOrderByEventStartDateAsc correct order"() {
        given:
        UUID ownerUuid = UUID.randomUUID()
        Event event1 = createTestEvent(LocalDate.now().plusDays(2))
        TicketOrder ticketOrder1 = createTestOrder()
        TicketType ticketType1 = ticketTypeRepository.saveAndFlush(Util.createTestTicketType(event1))
        Event event2 = createTestEvent(LocalDate.now().plusDays(1))
        TicketType ticketType2 = ticketTypeRepository.saveAndFlush(Util.createTestTicketType(event2))
        TicketOrder ticketOrder2 = createTestOrder()

        Ticket ticket1 = createTestTicket(ownerUuid, ticketType1, ticketOrder1)
        Ticket ticket2 = createTestTicket(ownerUuid, ticketType2, ticketOrder2)

        when:
        List<Ticket> tickets = ticketRepository.findByOwnerOrderByEventStartDateAsc(ownerUuid)

        then:
        tickets[0].getId() == ticket2.getId()
        tickets[1].getId() == ticket1.getId()
    }

    def "test countTicketsByEventIdAndMonth test counts"() {
        given:
        Event event = createTestEvent(LocalDate.now())
        TicketType ticketType = ticketTypeRepository.saveAndFlush(Util.createTestTicketType(event))
        createTestTicket(UUID.randomUUID(), ticketType, createTestOrder(LocalDateTime.now().minusMonths(4)))
        createTestTicket(UUID.randomUUID(), ticketType, createTestOrder(LocalDateTime.now().minusMonths(2)))
        createTestTicket(UUID.randomUUID(), ticketType, createTestOrder(LocalDateTime.now().minusMonths(2)))
        createTestTicket(UUID.randomUUID(), ticketType, createTestOrder(LocalDateTime.now().minusMonths(1)))

        when:
        List<Object[]> result = ticketRepository.countTicketsByEventIdAndMonth(event.id, LocalDateTime.now().minusMonths(3), LocalDateTime.now())

        then:
        result.size() == 2
    }

    def "test countTicketsByGender"() {
        given:
        Event event = createTestEvent(LocalDate.now())
        TicketOrder ticketOrder = createTestOrder()
        TicketType ticketType = ticketTypeRepository.saveAndFlush(Util.createTestTicketType(event))
        createTestTicket(UUID.randomUUID(), ticketType, Gender.MALE, ticketOrder)
        createTestTicket(UUID.randomUUID(), ticketType, Gender.FEMALE, ticketOrder)
        createTestTicket(UUID.randomUUID(), ticketType, Gender.FEMALE, ticketOrder)

        when:
        List<Object[]> result = ticketRepository.countTicketsByGender(event.id)

        then:
        result.size() == 2
    }

    def "test countTicketsByRole"() {
        given:
        Event event = createTestEvent(LocalDate.now())
        TicketOrder ticketOrder = createTestOrder()
        TicketType ticketType = ticketTypeRepository.saveAndFlush(Util.createTestTicketType(event))
        createTestTicket(UUID.randomUUID(), ticketType, DancingRole.LEADER, ticketOrder)
        createTestTicket(UUID.randomUUID(), ticketType, DancingRole.FOLLOWER, ticketOrder)
        createTestTicket(UUID.randomUUID(), ticketType, DancingRole.BOTH, ticketOrder)

        when:
        List<Object[]> result = ticketRepository.countTicketsByDancingRole(event.id)

        then:
        result.size() == 3
    }

    def "test countTicketsByTicketTypeName"() {
        given:
        Event event = createTestEvent(LocalDate.now())
        TicketOrder ticketOrder = createTestOrder()
        TicketType ticketType = ticketTypeRepository.saveAndFlush(Util.createTestTicketType(event))
        TicketType ticketType2 = ticketTypeRepository.saveAndFlush(Util.createTestTicketType(event))
        createTestTicket(UUID.randomUUID(), ticketType, ticketOrder)
        createTestTicket(UUID.randomUUID(), ticketType, ticketOrder)
        createTestTicket(UUID.randomUUID(), ticketType2, ticketOrder)

        when:
        List<Object[]> result = ticketRepository.countTicketsByTicketTypeId(event.id)

        then:
        result.size() == 2
    }

    def "should find tickets by event id"() {
        given: "An event and associated tickets in the database"
        Event event = createTestEvent()
        TicketOrder ticketOrder = createTestOrder()
        TicketType ticketType = ticketTypeRepository.saveAndFlush(Util.createTestTicketType(event))
        Ticket ticket1 = createTestTicket(UUID.randomUUID(), ticketType, ticketOrder)
        Ticket ticket2 = createTestTicket(UUID.randomUUID(), ticketType, ticketOrder)

        when: "findByEventId is called"
        List<Ticket> tickets = ticketRepository.findByEventId(event.id)

        then: "The correct tickets are returned"
        tickets.size() == 2
        tickets.stream().anyMatch { it.firstName == ticket1.getFirstName() }
        tickets.stream().anyMatch { it.firstName == ticket2.getFirstName() }
    }

    def "should find tickets by event id, with also other tickets in the repository"() {
        given: "An event and associated tickets in the database"
        Event event = createTestEvent()
        TicketOrder ticketOrder1 = createTestOrder()
        TicketType ticketType = ticketTypeRepository.saveAndFlush(Util.createTestTicketType(event))
        Ticket ticket1 = createTestTicket(UUID.randomUUID(), ticketType, ticketOrder1)
        Ticket ticket2 = createTestTicket(UUID.randomUUID(), ticketType, ticketOrder1)

        and: "The other tickets which should not be touched"
        Event event2 = createTestEvent()
        TicketOrder ticketOrder2 = createTestOrder()
        TicketType ticketType2 = ticketTypeRepository.saveAndFlush(Util.createTestTicketType(event2))
        createTestTicket(UUID.randomUUID(), ticketType2, ticketOrder2)
        createTestTicket(UUID.randomUUID(), ticketType2, ticketOrder2)

        when: "findByEventId is called"
        List<Ticket> tickets = ticketRepository.findByEventId(event.id)

        then: "The correct tickets are returned"
        tickets.size() == 2
        tickets.stream().anyMatch { it.firstName == ticket1.getFirstName() }
        tickets.stream().anyMatch { it.firstName == ticket2.getFirstName() }
    }

    def "test findByOrderId"() {
        given:
        Event event = createTestEvent()
        TicketOrder ticketOrder1 = createTestOrder()
        TicketOrder ticketOrder2 = createTestOrder()
        TicketType ticketType = ticketTypeRepository.saveAndFlush(Util.createTestTicketType(event))
        Ticket ticket1 = createTestTicket(UUID.randomUUID(), ticketType, ticketOrder1)
        createTestTicket(UUID.randomUUID(), ticketType, ticketOrder2)

        when:
        List<Ticket> tickets = ticketRepository.findByOrderId(ticketOrder1.getId())

        then:
        tickets.size() == 1
        tickets[0].getId() == ticket1.getId()
    }


    def createTestEvent(LocalDate startDate = LocalDate.now()) {
        eventRepository.saveAndFlush(Util.createTestEvent(true, null, startDate))
    }

    def createTestOrder() {
        createTestOrder(LocalDateTime.now())
    }

    def createTestOrder(LocalDateTime buyDate) {
        orderRepository.saveAndFlush(new TicketOrder(null, buyDate))
    }

    def createTestTicket(UUID ownerUuid, TicketType ticketType, Gender gender, TicketOrder ticketOrder) {
        ticketRepository.saveAndFlush(Util.createTestTicket(ownerUuid, ticketType, gender, ticketOrder))
    }

    def createTestTicket(UUID ownerUuid, TicketType ticketType, DancingRole role, TicketOrder ticketOrder) {
        ticketRepository.saveAndFlush(Util.createTestTicket(ownerUuid, ticketType, role, ticketOrder))
    }

    def createTestTicket(UUID ownerUuid, TicketType ticketType, TicketOrder ticketOrder) {
        ticketRepository.saveAndFlush(Util.createTestTicket(ownerUuid, ticketType, Gender.OTHER, ticketOrder))
    }
}
