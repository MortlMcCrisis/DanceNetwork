package com.mortl.dancenetwork.repository

import com.mortl.dancenetwork.entity.Event
import com.mortl.dancenetwork.testutil.Util
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDate

@SpringBootTest
class EventRepositorySpec extends Specification{

    @Autowired
    EventRepository eventRepository

    def setup() {
        eventRepository.deleteAll()
    }

    def cleanup() {
        eventRepository.deleteAll()
    }

    def "test findByPublishedTrueOrderByStartDateAsc no content"(){
        expect:
        eventRepository.findByPublishedTrueOrderByStartDateAsc().size() == 0
    }

    @Unroll
    def "test findByPublishedTrueOrderByStartDateAsc test published state"(boolean published){
        when:
        createTestEvent(published)

        then:
        eventRepository.findByPublishedTrueOrderByStartDateAsc().isEmpty() != published

        where:
        published << [true, false]
    }

    def "test findByPublishedTrueOrderByStartDateAsc test order"(){
        when:
        Event event2 = createTestEvent(true, LocalDate.now().minusDays(1), "name2")
        Event event1 = createTestEvent(true, LocalDate.now(), "name1")
        Event event3 = createTestEvent(true, LocalDate.now().plusDays(1), "name3")

        List<Event> events = eventRepository.findByPublishedTrueOrderByStartDateAsc()

        then:
        events.size() == 3

        events.get(0).getName() == event2.getName()
        events.get(1).getName() == event1.getName()
        events.get(2).getName() == event3.getName()
    }

    def "test findByPublishedTrueOrderByStartDateAsc test order and publised state"(){
        when:
        Event event5 = createTestEvent(true, LocalDate.now().minusDays(2), "name5")
        Event event2 = createTestEvent(true, LocalDate.now().minusDays(1), "name2")
        Event event1 = createTestEvent(true, LocalDate.now(), "name1")
        Event event6 = createTestEvent(true, LocalDate.now().plusDays(56), "name6")
        createTestEvent(false, LocalDate.now().plusDays(3), "name3")
        createTestEvent(false, LocalDate.now(), "name4")

        List<Event> events = eventRepository.findByPublishedTrueOrderByStartDateAsc()

        then:
        events.size() == 4

        events.get(0).getName() == event5.getName()
        events.get(1).getName() == event2.getName()
        events.get(2).getName() == event1.getName()
        events.get(3).getName() == event6.getName()
    }

    def createTestEvent(boolean published, LocalDate startDate = LocalDate.now(), String name = "test") {
        eventRepository.saveAndFlush(Util.createTestEvent(published, startDate, name))
    }
}
