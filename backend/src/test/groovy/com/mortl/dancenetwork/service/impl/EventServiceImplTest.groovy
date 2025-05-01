package com.mortl.dancenetwork.service.impl

import com.mortl.dancenetwork.entity.Event
import com.mortl.dancenetwork.mapper.NewsfeedEntryMapper
import com.mortl.dancenetwork.model.User
import com.mortl.dancenetwork.repository.EventRepository
import com.mortl.dancenetwork.repository.TicketTypeRepository
import com.mortl.dancenetwork.service.INewsfeedEntryService
import com.mortl.dancenetwork.service.IStripeService
import com.mortl.dancenetwork.service.IUserService
import com.mortl.dancenetwork.util.NewsfeedFactory
import spock.lang.Specification
import spock.lang.Subject

class EventServiceImplTest extends Specification {

    def eventRepository = Mock(EventRepository)
    def userService = Mock(IUserService)
    def newsfeedEntryService = Mock(INewsfeedEntryService)
    def stripeService = Mock(IStripeService)
    def newsfeedFactory = Mock(NewsfeedFactory)
    def newsfeedEntryMapper = Mock(NewsfeedEntryMapper)
    def ticketTypeRepository = Mock(TicketTypeRepository)

    @Subject
    def eventService = new EventServiceImpl(
            eventRepository,
            userService,
            newsfeedEntryService,
            stripeService,
            newsfeedFactory,
            newsfeedEntryMapper,
            ticketTypeRepository
    )

    def "should cover all Event properties in updateEventProperty"() {
        given: "an event and current user"
        def creatorId = UUID.randomUUID()
        def event = new Event(creator: creatorId)

        eventRepository.findById(_) >> Optional.of(event)
        userService.getCurrentUser() >> Optional.of(new User(
                creatorId, null, null, null, null, null, null, null
        ))

        and: "expected properties from the switch"
        def expectedProperties = [
                "name", "profileImage", "bannerImage", "location", "website",
                "email", "startDate", "startTime", "endDate", "published"
        ] as Set

        and: "actual properties from Event class via reflection"
        def actualProperties = Event.declaredFields
                .findAll { !it.synthetic }
                .collect { it.name }
                .findAll { it != "id" && it != "creator" && it != "createdAt" }
                .toSet()

        expect: "the properties match exactly"
        expectedProperties == actualProperties

        when: "updateEventProperty is called for each property"
        def testValues = [
                startDate: "2025-05-20",
                endDate  : "2025-05-20",
                startTime: "18:30",
                published: "true"
        ]

        expectedProperties.each { property ->
            def testValue = testValues.get(property, "TestValue")
            eventService.updateEventProperty(1L, property, testValue)
        }

        then: "event is saved for each property"
        expectedProperties.size() * eventRepository.save(_) >> event
    }
}
