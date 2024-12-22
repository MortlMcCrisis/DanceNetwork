package com.mortl.dancenetwork.controller.closed;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;

class TicketControllerClosedTest extends AbstractControllerTest
{

  @Test
  @WithMockUser
  void testSecuredEndpoint_withMockUser() throws Exception
  {
    mockMvc.perform(get("/api/closed/v1/tickets/infos")
            .header("Authorization", "Bearer " + getJwt())
            .contentType("application/json"))
        .andExpect(status().isOk());
  }
}
