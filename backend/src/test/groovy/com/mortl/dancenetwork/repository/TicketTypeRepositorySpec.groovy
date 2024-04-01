package com.mortl.dancenetwork.repository

import com.mortl.dancenetwork.model.Event
import com.mortl.dancenetwork.model.TicketType
import com.mortl.dancenetwork.testutil.Util
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class TicketTypeRepositorySpec extends Specification {

    @Autowired
    EventRepository eventRepository

    @Autowired
    TicketTypeRepository ticketTypeRepository

    def setup() {
        cleanup()
    }

    def cleanup() {
        ticketTypeRepository.deleteAll()
        eventRepository.deleteAll()
    }

    def "test findByEventId no content"(){
        expect:
        ticketTypeRepository.findByEventId(123).size() == 0
    }

    def "test findByEventId"(){
        when:
        Event event = createTestEvent()
        TicketType ticketType1 = ticketTypeRepository.saveAndFlush(Util.createTestTicketType(event))
        TicketType ticketType2 = ticketTypeRepository.saveAndFlush(Util.createTestTicketType(event))
        ticketTypeRepository.saveAndFlush(Util.createTestTicketType(createTestEvent()))

        List<TicketType> ticketTypes = ticketTypeRepository.findByEventId(event.getId())

        then:
        ticketTypes.contains(ticketType1)
        ticketTypes.contains(ticketType2)
    }

    def createTestEvent() {
        eventRepository.saveAndFlush(Util.createTestEvent(true))
    }
}
