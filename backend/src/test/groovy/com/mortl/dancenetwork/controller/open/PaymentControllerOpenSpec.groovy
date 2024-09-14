package com.mortl.dancenetwork.controller.open

import com.mortl.dancenetwork.entity.Event
import com.mortl.dancenetwork.repository.EventRepository
import com.mortl.dancenetwork.repository.TicketTypeRepository
import com.mortl.dancenetwork.testutil.Util
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import spock.lang.Specification


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@AutoConfigureMockMvc
@SpringBootTest
class PaymentControllerOpenSpec extends Specification {

    @Autowired
    EventRepository eventRepository

    @Autowired
    TicketTypeRepository ticketTypeRepository

    @Autowired
    MockMvc mvc

    def "test do payment"() {
        given:
        Event event = eventRepository.saveAndFlush(Util.createTestEvent(true))
        int ticketTypeId = ticketTypeRepository.saveAndFlush(Util.createTestTicketType(event)).getId()
        String body = """{
                          "tickets": [
                            {
                              "ticketTypeId": "${ticketTypeId}",
                              "firstName": "asdf@adfg.de",
                              "lastName": "asdf@adfg.de",
                              "address": "asdf@adfg.de",
                              "country": "asdf@adfg.de",
                              "gender": "MALE",
                              "role": "LEADER",
                              "email": "asdf@adfg.de",
                              "phone": ""
                            }
                          ]
                        }"""

        expect:
        mvc.perform(MockMvcRequestBuilders.post("/api/open/v1/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
        .andExpect (status().isOk())
    }
}
