package com.mortl.dancenetwork.repository

import com.mortl.dancenetwork.entity.Event
import com.mortl.dancenetwork.entity.Ticket
import com.mortl.dancenetwork.entity.TicketType
import com.mortl.dancenetwork.enums.Gender
import com.mortl.dancenetwork.enums.Role
import com.mortl.dancenetwork.testutil.Util
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cglib.core.Local
import spock.lang.Specification

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.YearMonth

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
        given:
        UUID ownerUuid = UUID.randomUUID()
        Event event = createTestEvent()
        TicketType ticketType = ticketTypeRepository.saveAndFlush(Util.createTestTicketType(event))

        createTestTicket(ownerUuid, ticketType)

        expect:
        ticketRepository.findByOwnerOrderByEventStartDateAsc(UUID.randomUUID()).size() == 0
    }

    def "test findByOwnerOrderByEventStartDateAsc only tickets of owner selected"(){
        given:
        UUID ownerUuid = UUID.randomUUID()
        Event event = createTestEvent()
        TicketType ticketType = ticketTypeRepository.saveAndFlush(Util.createTestTicketType(event))

        Ticket ticket1 = createTestTicket(ownerUuid, ticketType)
        Ticket ticket2 = createTestTicket(ownerUuid, ticketType)
        createTestTicket(UUID.randomUUID(), ticketType)

        when:
        List<Ticket> tickets = ticketRepository.findByOwnerOrderByEventStartDateAsc(ownerUuid)

        then:
        tickets.size() == 2
        ticket1.owner == ownerUuid
        ticket2.owner == ownerUuid
    }

    def "test findByOwnerOrderByEventStartDateAsc correct order"(){
        given:
        UUID ownerUuid = UUID.randomUUID()
        Event event1 = createTestEvent(LocalDate.now().plusDays(2))
        TicketType ticketType1 = ticketTypeRepository.saveAndFlush(Util.createTestTicketType(event1))
        Event event2 = createTestEvent(LocalDate.now().plusDays(1))
        TicketType ticketType2 = ticketTypeRepository.saveAndFlush(Util.createTestTicketType(event2))

        Ticket ticket1 = createTestTicket(ownerUuid, ticketType1)
        Ticket ticket2 = createTestTicket(ownerUuid, ticketType2)

        when:
        List<Ticket> tickets = ticketRepository.findByOwnerOrderByEventStartDateAsc(ownerUuid)

        then:
        tickets[0].getId() == ticket2.getId()
        tickets[1].getId() == ticket1.getId()
    }

    def "test countTicketsByEventIdAndMonth test counts"(){
        given:
        Event event = createTestEvent(LocalDate.now())
        TicketType ticketType = ticketTypeRepository.saveAndFlush(Util.createTestTicketType(event))
        createTestTicket(UUID.randomUUID(), ticketType, LocalDateTime.now().minusMonths(4))
        createTestTicket(UUID.randomUUID(), ticketType, LocalDateTime.now().minusMonths(2))
        createTestTicket(UUID.randomUUID(), ticketType, LocalDateTime.now().minusMonths(2))
        createTestTicket(UUID.randomUUID(), ticketType, LocalDateTime.now().minusMonths(1))

        when:
        List<Object[]> result = ticketRepository.countTicketsByEventIdAndMonth(event.id, LocalDateTime.now().minusMonths(3), LocalDateTime.now())

        then:
        result.size() == 2
    }

    def "test countTicketsByGender"(){
        given:
        Event event = createTestEvent(LocalDate.now())
        TicketType ticketType = ticketTypeRepository.saveAndFlush(Util.createTestTicketType(event))
        createTestTicket(UUID.randomUUID(), ticketType, Gender.MALE)
        createTestTicket(UUID.randomUUID(), ticketType, Gender.FEMALE)
        createTestTicket(UUID.randomUUID(), ticketType, Gender.FEMALE)

        when:
        List<Object[]> result = ticketRepository.countTicketsByGender(event.id)

        then:
        result.size() == 2
    }

    def "test countTicketsByRole"(){
        given:
        Event event = createTestEvent(LocalDate.now())
        TicketType ticketType = ticketTypeRepository.saveAndFlush(Util.createTestTicketType(event))
        createTestTicket(UUID.randomUUID(), ticketType, Role.LEADER)
        createTestTicket(UUID.randomUUID(), ticketType, Role.FOLLOWER)
        createTestTicket(UUID.randomUUID(), ticketType, Role.BOTH)

        when:
        List<Object[]> result = ticketRepository.countTicketsByRole(event.id)

        then:
        result.size() == 3
    }

    def "test countTicketsByTicketTypeName"(){
        given:
        Event event = createTestEvent(LocalDate.now())
        TicketType ticketType = ticketTypeRepository.saveAndFlush(Util.createTestTicketType(event))
        TicketType ticketType2 = ticketTypeRepository.saveAndFlush(Util.createTestTicketType(event))
        createTestTicket(UUID.randomUUID(), ticketType)
        createTestTicket(UUID.randomUUID(), ticketType)
        createTestTicket(UUID.randomUUID(), ticketType2)

        when:
        List<Object[]> result = ticketRepository.countTicketsByTicketTypeId(event.id)

        then:
        result.size() == 2
    }

    def createTestEvent(LocalDate startDate = LocalDate.now()) {
        eventRepository.saveAndFlush(Util.createTestEvent(true, startDate))
    }

    def createTestTicket(UUID ownerUuid, TicketType ticketType){
        ticketRepository.saveAndFlush(Util.createTestTicket(ownerUuid, ticketType))
    }

    def createTestTicket(UUID ownerUuid, TicketType ticketType, Gender gender){
        ticketRepository.saveAndFlush(Util.createTestTicket(ownerUuid, ticketType, gender))
    }

    def createTestTicket(UUID ownerUuid, TicketType ticketType, Role role){
        ticketRepository.saveAndFlush(Util.createTestTicket(ownerUuid, ticketType, role))
    }

    def createTestTicket(UUID ownerUuid, TicketType ticketType, LocalDateTime buyDate){
        ticketRepository.saveAndFlush(Util.createTestTicket(ownerUuid, ticketType, buyDate))
    }
}
