package com.mortl.dancenetwork.service.impl

import com.mortl.dancenetwork.entity.TicketType
import com.mortl.dancenetwork.repository.TicketTypeRepository
import com.mortl.dancenetwork.service.IStripeService
import com.mortl.dancenetwork.testutil.Util
import spock.lang.Specification

class TicketTypeServiceSpec extends Specification {

    TicketTypeRepository ticketTypeRepository = Mock()

    IStripeService stripeService = Mock()

    TicketTypeServiceImpl ticketTypeService

    def setup() {
        ticketTypeService = new TicketTypeServiceImpl(ticketTypeRepository, stripeService)
    }

    def "test getTicketTypesForEvent no events"() {
        given:
        long eventId = 1

        ticketTypeRepository.findByEventId(eventId) >> List.of()

        expect:
        ticketTypeService.getTicketTypesForEvent(eventId).isEmpty()
    }

    def "test getTicketTypesForEvent"() {
        given:
        long eventId = 1

        TicketType ticketType1 = Mock(TicketType)
        TicketType ticketType2 = Mock(TicketType)

        ticketTypeRepository.findByEventId(eventId) >> List.of(ticketType1, ticketType2)

        when:
        List<TicketType> result = ticketTypeService.getTicketTypesForEvent(eventId)

        then:
        result.contains(ticketType1)
        result.contains(ticketType2)
    }

    def "test updateTicketTypes"() {
        given:
        long eventId = 1

        TicketType ticketType1 = Util.createTestTicketType(1L, eventId)
        TicketType ticketType2 = Util.createTestTicketType(2L, eventId)
        List<TicketType> ticketTypes = [ticketType1, ticketType2]
        ticketTypeRepository.findByEventId(eventId) >> ticketTypes
        ticketTypeRepository.saveAllAndFlush(ticketTypes) >> ticketTypes

        when:
        ticketTypeService.updateTicketTypes(eventId, ticketTypes)

        then:
        1 * ticketTypeRepository.deleteAllById([]);
    }

    def "test updateTicketTypes update 1 ticket and delete the other"() {
        given:
        long eventId = 1

        TicketType ticketType1 = Util.createTestTicketType(1L, eventId)
        TicketType ticketType2 = Util.createTestTicketType(2L, eventId)
        ticketTypeRepository.findByEventId(eventId) >> [ticketType1, ticketType2]
        ticketTypeRepository.saveAllAndFlush([ticketType1]) >> [ticketType1]

        when:
        ticketTypeService.updateTicketTypes(eventId, [ticketType1])

        then:
        1 * ticketTypeRepository.deleteAllById([2])
    }
}
