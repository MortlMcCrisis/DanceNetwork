package com.mortl.dancenetwork.service.impl

import com.mortl.dancenetwork.dto.TicketTypeDTO
import com.mortl.dancenetwork.mapper.TicketTypeMapper
import com.mortl.dancenetwork.entity.TicketType
import com.mortl.dancenetwork.repository.TicketTypeRepository
import spock.lang.Specification

class TicketTypeServiceSpec extends Specification {

    TicketTypeRepository ticketTypeRepository = Mock(TicketTypeRepository)

    TicketTypeMapper ticketTypeMapper = Mock(TicketTypeMapper)

    TicketTypeServiceImpl ticketTypeService

    def setup(){
        ticketTypeService = new TicketTypeServiceImpl(ticketTypeRepository, ticketTypeMapper)
    }

    def "test getTicketTypesForEvent no events"(){
        given:
        long eventId = 1

        ticketTypeRepository.findByEventId(eventId) >> List.of()

        expect:
        ticketTypeService.getTicketTypesForEvent(eventId).isEmpty()
    }

    def "test getTicketTypesForEvent"(){
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

    def "test addTicketType"(){
        given:
        TicketTypeDTO ticketTypeDTO = Mock(TicketTypeDTO)

        when:
        ticketTypeService.addTicketType(ticketTypeDTO)

        then:
        1 * ticketTypeMapper.toModel(ticketTypeDTO)
        1 * ticketTypeRepository.saveAndFlush(_)
    }
}
