package com.mortl.dancenetwork.service.impl

import com.mortl.dancenetwork.dto.TicketTypeDTO
import com.mortl.dancenetwork.entity.TicketType
import com.mortl.dancenetwork.mapper.TicketTypeMapper
import com.mortl.dancenetwork.repository.TicketTypeRepository
import com.mortl.dancenetwork.service.IStripeService
import com.mortl.dancenetwork.testutil.Util
import spock.lang.Specification

class TicketTypeServiceSpec extends Specification {

    TicketTypeRepository ticketTypeRepository = Mock()

    TicketTypeMapper ticketTypeMapper = Mock()

    IStripeService stripeService = Mock()

    TicketTypeServiceImpl ticketTypeService

    def setup() {
        ticketTypeService = new TicketTypeServiceImpl(ticketTypeRepository, ticketTypeMapper, stripeService)
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

        TicketTypeDTO ticketTypeDTO1 = Mock(TicketTypeDTO)
        TicketTypeDTO ticketTypeDTO2 = Mock(TicketTypeDTO)

        ticketTypeRepository.findByEventId(eventId) >> List.of(ticketType1, ticketType2)
        ticketTypeMapper.toDTO(ticketType1) >> ticketTypeDTO1
        ticketTypeMapper.toDTO(ticketType2) >> ticketTypeDTO2

        when:
        List<TicketTypeDTO> result = ticketTypeService.getTicketTypesForEvent(eventId)

        then:
        result.contains(ticketTypeDTO1)
        result.contains(ticketTypeDTO2)
    }

    def "test updateTicketTypes"() {
        given:
        long eventId = 1

        TicketTypeDTO ticketTypeDto1 = Util.createTestTicketTypeDto(1, eventId)
        TicketTypeDTO ticketTypeDto2 = Util.createTestTicketTypeDto(2, eventId)

        TicketType ticketType1 = Util.createTestTicketType(1, eventId)
        TicketType ticketType2 = Util.createTestTicketType(2, eventId)
        List<TicketType> ticketTypes = [ticketType1, ticketType2]
        ticketTypeRepository.findByEventId(eventId) >> ticketTypes

        ticketTypeMapper.toModel(ticketTypeDto1) >> ticketType1
        ticketTypeMapper.toModel(ticketTypeDto2) >> ticketType2

        when:
        ticketTypeService.updateTicketTypes([ticketTypeDto1, ticketTypeDto2])

        then:
        1 * ticketTypeRepository.saveAllAndFlush(ticketTypes);
        1 * ticketTypeRepository.deleteAllById([]);
    }

    def "test updateTicketTypes update 1 ticket and delete the other"() {
        given:
        long eventId = 1

        TicketTypeDTO ticketTypeDto1 = Util.createTestTicketTypeDto(1, eventId)

        TicketType ticketType1 = Util.createTestTicketType(1, eventId)
        TicketType ticketType2 = Util.createTestTicketType(2, eventId)
        ticketTypeRepository.findByEventId(eventId) >> [ticketType1, ticketType2]

        ticketTypeMapper.toModel(ticketTypeDto1) >> ticketType1

        when:
        ticketTypeService.updateTicketTypes([ticketTypeDto1])

        then:
        1 * ticketTypeRepository.saveAllAndFlush([ticketType1]);
        1 * ticketTypeRepository.deleteAllById([2]);
    }
}
