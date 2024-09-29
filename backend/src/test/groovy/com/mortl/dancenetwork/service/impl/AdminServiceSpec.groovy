package com.mortl.dancenetwork.service.impl

import com.mortl.dancenetwork.entity.Event
import com.mortl.dancenetwork.enums.DancingRole
import com.mortl.dancenetwork.enums.Gender
import com.mortl.dancenetwork.repository.EventRepository
import com.mortl.dancenetwork.repository.TicketRepository
import com.mortl.dancenetwork.repository.TicketTypeRepository
import com.mortl.dancenetwork.testutil.Util
import org.springframework.data.util.Pair
import spock.lang.Specification

import java.time.LocalDateTime
import java.time.YearMonth

class AdminServiceSpec extends Specification {

    EventRepository eventRepository = Mock()

    TicketRepository ticketRepository = Mock()


    TicketTypeRepository ticketTypeRepository = Mock()

    AdminServiceImpl adminService

    def setup() {
        adminService = new AdminServiceImpl(ticketTypeRepository, ticketRepository, eventRepository)
    }

    def "test getTicketsPerMonth"() {
        given:
        long eventId = 0
        LocalDateTime createdAt = LocalDateTime.of(2023, 6, 1, 0, 0)

        List<Object[]> ticketCounts = [
                [LocalDateTime.of(2023, 6, 1, 0, 0), 5L] as Object[],
                [LocalDateTime.of(2023, 7, 1, 0, 0), 3L] as Object[],
                [LocalDateTime.of(2023, 8, 1, 0, 0), 2L] as Object[]
        ]
        ticketRepository.countTicketsByEventIdAndMonth(eventId, createdAt, _) >> ticketCounts

        when:
        Map<YearMonth, Long> result = adminService.getTicketsPerMonth(createdAt, eventId)

        then:
        result[YearMonth.of(2023, 6)] == 5L
        result[YearMonth.of(2023, 7)] == 3L
        result[YearMonth.of(2023, 8)] == 2L
    }

    def "test getTicketsPerMonth not ticket in one month should be shown as 0"() {
        given:
        long eventId = 0
        LocalDateTime createdAt = LocalDateTime.of(2023, 6, 1, 0, 0)

        List<Object[]> ticketCounts = [
                [LocalDateTime.of(2023, 6, 1, 0, 0), 5L] as Object[],
                [LocalDateTime.of(2023, 8, 1, 0, 0), 2L] as Object[]
        ]
        ticketRepository.countTicketsByEventIdAndMonth(eventId, createdAt, _) >> ticketCounts

        when:
        Map<YearMonth, Long> result = adminService.getTicketsPerMonth(createdAt, eventId)

        then:
        result[YearMonth.of(2023, 6)] == 5L
        result[YearMonth.of(2023, 7)] == 0L
        result[YearMonth.of(2023, 8)] == 2L
    }

    def "test getTicketsPerMonth sorted ascending"() {
        given:
        long eventId = 0
        LocalDateTime createdAt = LocalDateTime.of(2023, 6, 1, 0, 0)

        List<Object[]> ticketCounts = [
                [LocalDateTime.of(2023, 6, 1, 0, 0), 5L] as Object[],
                [LocalDateTime.of(2023, 8, 1, 0, 0), 2L] as Object[]
        ]
        ticketRepository.countTicketsByEventIdAndMonth(eventId, createdAt, _) >> ticketCounts

        when:
        Map<YearMonth, Long> result = adminService.getTicketsPerMonth(createdAt, eventId)

        then:
        YearMonth previousYearMonth = YearMonth.of(2023, 05)
        result.each { key, value ->
            assert previousYearMonth < key: "Months are not sorted ascending"
            previousYearMonth = key
        }
    }

    def "test getTicketsByGender"() {
        given:
        long eventId = 0

        List<Object[]> ticketCounts = [
                [Gender.MALE, 2L] as Object[],
                [Gender.FEMALE, 5L] as Object[],
                [Gender.OTHER, 1L] as Object[]
        ]
        ticketRepository.countTicketsByGender(eventId) >> ticketCounts

        when:
        Map<YearMonth, Long> result = adminService.getTicketsByGender(eventId)

        then:
        result.size() == 3
        result[Gender.MALE] == 2L
        result[Gender.FEMALE] == 5L
        result[Gender.OTHER] == 1L
    }

    def "test getTicketsByGender no ticket with gender should be returned as zero"() {
        given:
        long eventId = 0

        List<Object[]> ticketCounts = [
                [Gender.MALE, 2L] as Object[]
        ]
        ticketRepository.countTicketsByGender(eventId) >> ticketCounts

        when:
        Map<YearMonth, Long> result = adminService.getTicketsByGender(eventId)

        then:
        result.size() == 3
        result[Gender.MALE] == 2L
        result[Gender.FEMALE] == 0L
        result[Gender.OTHER] == 0L
    }

    def "test getTicketsByRole"() {
        given:
        long eventId = 0

        List<Object[]> ticketCounts = [
                [DancingRole.LEADER, 1L] as Object[],
                [DancingRole.FOLLOWER, 2L] as Object[],
                [DancingRole.BOTH, 3L] as Object[]
        ]
        ticketRepository.countTicketsByDancingRole(eventId) >> ticketCounts

        when:
        Map<YearMonth, Long> result = adminService.getTicketsByDancingRole(eventId)

        then:
        result.size() == 3
        result[DancingRole.LEADER] == 1L
        result[DancingRole.FOLLOWER] == 2L
        result[DancingRole.BOTH] == 3L
    }

    def "test getTicketsByRole when no ticket with role should be returned as zero"() {
        given:
        long eventId = 0

        List<Object[]> ticketCounts = [
                [DancingRole.LEADER, 1L] as Object[]
        ]
        ticketRepository.countTicketsByDancingRole(eventId) >> ticketCounts

        when:
        Map<YearMonth, Long> result = adminService.getTicketsByDancingRole(eventId)

        then:
        result.size() == 3
        result[DancingRole.LEADER] == 1L
        result[DancingRole.FOLLOWER] == 0L
        result[DancingRole.BOTH] == 0L
    }

    //TODO test when no ticket with ticketType (should be returned as zero in the result, not null)
    def "test getTicketsByTicketType"() {
        given:
        long eventId = 0

        List<Object[]> ticketCounts = [
                [1, 4L] as Object[],
                [2, 5L] as Object[],
                [3, 6L] as Object[]
        ]
        ticketRepository.countTicketsByTicketTypeId(eventId) >> ticketCounts
        ticketTypeRepository.findByEventId(eventId) >> [
                Util.createTestTicketType(1L, "first", 100, Mock(Event)),
                Util.createTestTicketType(2L, "second", 100, Mock(Event)),
                Util.createTestTicketType(3L, "third", 100, Mock(Event))
        ]

        when:
        List<Pair<String, Pair<Long, Long>>> result = adminService.getTicketsByTicketType(eventId)

        then:
        result.size() == 3
        result[0].getFirst() == "first"
        result[0].getSecond().getFirst() == 4L
        result[0].getSecond().getSecond() == 100L
        result[1].getFirst() == "second"
        result[1].getSecond().getFirst() == 5L
        result[1].getSecond().getSecond() == 100L
        result[2].getFirst() == "third"
        result[2].getSecond().getFirst() == 6L
        result[2].getSecond().getSecond() == 100L
    }

    def "test getTicketsByTicketType when no ticket with ticketType should be returned as zero"() {
        given:
        long eventId = 0

        List<Object[]> ticketCounts = [
                [1, 4L] as Object[]
        ]
        ticketRepository.countTicketsByTicketTypeId(eventId) >> ticketCounts
        ticketTypeRepository.findByEventId(eventId) >> [
                Util.createTestTicketType(1L, "first", 100L, Mock(Event)),
                Util.createTestTicketType(2L, "second", 100L, Mock(Event))
        ]

        when:
        List<Pair<String, Pair<Long, Long>>> result = adminService.getTicketsByTicketType(eventId)

        then:
        result.size() == 2
        result[0].getSecond().getFirst() == 4L
        result[1].getSecond().getFirst() == 0L
    }
}
