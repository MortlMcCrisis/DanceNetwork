package com.mortl.dancenetwork.repository

import com.mortl.dancenetwork.entity.Event
import com.mortl.dancenetwork.entity.TicketType
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
        ticketTypeRepository.saveAndFlush(Util.createTestTicketType(event))
        ticketTypeRepository.saveAndFlush(Util.createTestTicketType(event))
        ticketTypeRepository.saveAndFlush(Util.createTestTicketType(createTestEvent()))

        List<TicketType> ticketTypes = ticketTypeRepository.findByEventId(event.getId())

        then:
        ticketTypes.size() == 2
        ticketTypes[0].event.id == event.id
        ticketTypes[1].event.id == event.id
    }

    def createTestEvent() {
        eventRepository.saveAndFlush(Util.createTestEvent(true))
    }
}
