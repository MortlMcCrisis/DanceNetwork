package com.mortl.dancenetwork.controller.open

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.mortl.dancenetwork.entity.Event
import com.mortl.dancenetwork.repository.EventRepository
import com.mortl.dancenetwork.testutil.Util
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import spock.lang.Specification

import java.time.LocalDate

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@AutoConfigureMockMvc
@SpringBootTest
class EventControllerOpenSpec extends Specification {

    @Autowired
    EventRepository eventRepository

    @Autowired
    MockMvc mvc

    @Autowired
    ObjectMapper objectMapper

    LocalDate event1Date = LocalDate.now().plusDays(1)
    LocalDate event2Date = LocalDate.now().plusDays(2)
    LocalDate event3Date = LocalDate.now().plusDays(5)

    String event1Name = 'event1'
    String event2Name = 'event2'
    String event3Name = 'event3'

    def setup() {
        eventRepository.deleteAll()

        eventRepository.saveAndFlush(Util.createTestEvent(true, null, event1Date, event1Name))
        eventRepository.saveAndFlush(Util.createTestEvent(true, null, event2Date, event2Name))
        eventRepository.saveAndFlush(Util.createTestEvent(true, null, event3Date, event3Name))
    }

    def cleanup() {
        eventRepository.deleteAll()
    }

    def 'test getEvents'() {
        when:
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get('/api/open/v1/events'))
                .andExpect(status().isOk())
                .andReturn()

        List<Event> events = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<Event>>() {
        })

        then:
        events.size() == 3
        events.find { it -> it.getName() == event1Name }.getStartDate() == event1Date
        events.find { it -> it.getName() == event2Name }.getStartDate() == event2Date
        events.find { it -> it.getName() == event3Name }.getStartDate() == event3Date
    }

    def 'test getEvents maxEntries'() {
        when:
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get('/api/open/v1/events?maxEntries=2'))
                .andExpect(status().isOk())
                .andReturn()

        List<Event> events = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<Event>>() {
        })

        then:
        events.size() == 2
    }

    def 'test getEvents from'() {
        when:
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get('/api/open/v1/events?from=' + LocalDate.now().plusDays(4).toString()))
                .andExpect(status().isOk())
                .andReturn()

        List<Event> events = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<Event>>() {
        })

        then:
        events.size() == 1
    }

    def 'test getEvent'() {
        given:
        Event givenEvent = eventRepository.findAll().get(0)
        long id = givenEvent.getId()

        when:
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/api/open/v1/events/${id}"))
                .andExpect(status().isOk())
                .andReturn()

        Event event = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<Event>() {
        })

        then:
        event.getId() == givenEvent.getId()
        event.getName() == givenEvent.getName()
        event.getStartDate() == givenEvent.getStartDate()
        event.getCreator() == givenEvent.getCreator()
        event.getEndDate() == givenEvent.getEndDate()
        event.getEmail() == givenEvent.getEmail()
        event.getLocation() == givenEvent.getLocation()
        event.getWebsite() == givenEvent.getWebsite()
    }
}
