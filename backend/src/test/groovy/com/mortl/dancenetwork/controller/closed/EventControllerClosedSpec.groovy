//package com.mortl.dancenetwork.controller.closed
//
//import com.fasterxml.jackson.databind.ObjectMapper
//import com.mortl.dancenetwork.repository.EventRepository
//import com.mortl.dancenetwork.testutil.Util
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
//import org.springframework.boot.test.context.SpringBootTest
//import org.springframework.boot.test.mock.mockito.MockBean
//import org.springframework.http.MediaType
//import org.springframework.security.oauth2.jwt.Jwt
//import org.springframework.security.oauth2.jwt.JwtDecoder
//import org.springframework.test.web.servlet.MockMvc
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
//import spock.lang.Specification
//
//
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
//
//
////TODO make with test jwt work"
//@AutoConfigureMockMvc
//@SpringBootTest
//class EventControllerClosedSpec extends Specification {
//
//    @Autowired
//    EventRepository eventRepository
//
//    @Autowired
//    MockMvc mvc
//
//    @Autowired
//    ObjectMapper objectMapper
//
//    @MockBean
//    JwtDecoder jwtDecoder
//
//    def setup(){
//        eventRepository.deleteAll()
//    }
//
//    def cleanup(){
//        eventRepository.deleteAll()
//    }
//
//    def 'test postEvent'(){
//        //given:
//        //Event event = eventRepository.saveAndFlush(Util.createTestEvent(true))
//
//        Jwt jwtToken = Jwt.withTokenValue("your_token_value")
//                .header("alg", "HS256")
//                .claim("sub", UUID.randomUUID().toString())
//                .claim("iss", "your_issuer")
//                .build();
//
//        jwtDecoder.decode(_) >> jwtToken
//
//        expect:
//        mvc.perform(
//                //MockMvcRequestBuilders.put("/api/closed/v1/events/${event.getId()}")
//                MockMvcRequestBuilders.post("/api/closed/v1/events")
//                        //.with(SecurityMockMvcRequestPostProcessors.jwt())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(Util.createTestEvent(false))))
//                        //.header("Authorization", "Bearer " + jwtToken))
//                .andExpect (status().isForbidden())
//    }
//
//    //TODO test mapped response codes from controller advice
//    //TODO also do it for newsfeedentrycontroller
//}
