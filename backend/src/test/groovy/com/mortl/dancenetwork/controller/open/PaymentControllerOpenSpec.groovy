package com.mortl.dancenetwork.controller.open

import com.mortl.dancenetwork.service.IEventService
import com.mortl.dancenetwork.service.INewsfeedEntryService
import com.mortl.dancenetwork.service.IPaymentService
import com.mortl.dancenetwork.service.IStorageService
import com.mortl.dancenetwork.service.ITicketService
import com.mortl.dancenetwork.service.ITicketTypeService
import com.mortl.dancenetwork.service.IUserService
import org.mockito.Mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest
class PaymentControllerOpenSpec extends Specification{

    @MockBean
    IEventService eventService

    @MockBean
    IStorageService storageService

    @MockBean
    INewsfeedEntryService newsfeedEntryService

    @MockBean
    ITicketService ticketService

    @MockBean
    ITicketTypeService ticketTypeService

    @MockBean
    IUserService userService

    @MockBean
    IPaymentService paymentService

    @Autowired
    MockMvc mvc

    def "test"() {
        expect:
        mvc.perform(MockMvcRequestBuilders.post("/api/open/v1/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content('''{
  "tickets": [
    {
      "ticketTypeId": "1",
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
}'''))
        .andExpect (status().isOk())
    }
}
