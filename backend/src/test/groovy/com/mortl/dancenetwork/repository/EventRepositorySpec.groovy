package com.mortl.dancenetwork.repository

import com.mortl.dancenetwork.entity.Event
import com.mortl.dancenetwork.testutil.Util
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDate

@DataJpaTest
class EventRepositorySpec extends Specification {

    @Autowired
    EventRepository eventRepository

    def "test findByPublishedTrueAndStartDateAfter no content"() {
        expect:
        eventRepository.findByPublishedTrueAndStartDateAfter(LocalDate.now(), Pageable.unpaged()).size() == 0
    }

    @Unroll
    def "test findByPublishedTrueAndStartDateAfter test published state"(boolean published) {
        when:
        createTestEvent(published)

        then:
        eventRepository.findByPublishedTrueAndStartDateAfter(LocalDate.now().minusDays(1), Pageable.unpaged()).isEmpty() != published

        where:
        published << [true, false]
    }

    def "test findByPublishedTrueAndStartDateAfter test order"() {
        when:
        Event event2 = createTestEvent(true, LocalDate.now().minusDays(1), "name2")
        Event event1 = createTestEvent(true, LocalDate.now(), "name1")
        Event event3 = createTestEvent(true, LocalDate.now().plusDays(1), "name3")

        List<Event> events = eventRepository.findByPublishedTrueAndStartDateAfter(
                LocalDate.now().minusDays(2),
                Pageable.unpaged(Sort.by(Sort.Direction.ASC, "startDate")))

        then:
        events.size() == 3

        events.get(0).getName() == event2.getName()
        events.get(1).getName() == event1.getName()
        events.get(2).getName() == event3.getName()
    }

    def "test findByPublishedTrueAndStartDateAfter test order and published state"() {
        when:
        Event event5 = createTestEvent(true, LocalDate.now().minusDays(2), "name5")
        Event event2 = createTestEvent(true, LocalDate.now().minusDays(1), "name2")
        Event event1 = createTestEvent(true, LocalDate.now(), "name1")
        Event event6 = createTestEvent(true, LocalDate.now().plusDays(56), "name6")
        createTestEvent(false, LocalDate.now().plusDays(3), "name3")
        createTestEvent(false, LocalDate.now(), "name4")

        List<Event> events = eventRepository.findByPublishedTrueAndStartDateAfter(
                LocalDate.now().minusDays(3),
                Pageable.unpaged(Sort.by(Sort.Direction.ASC, "startDate"))
        )

        then:
        events.size() == 4

        events.get(0).getName() == event5.getName()
        events.get(1).getName() == event2.getName()
        events.get(2).getName() == event1.getName()
        events.get(3).getName() == event6.getName()
    }

    def createTestEvent(boolean published, LocalDate startDate = LocalDate.now(), String name = "test") {
        eventRepository.saveAndFlush(Util.createTestEvent(published, null, startDate, name))
    }
}
