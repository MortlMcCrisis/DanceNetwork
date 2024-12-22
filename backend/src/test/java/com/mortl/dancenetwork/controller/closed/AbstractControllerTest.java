package com.mortl.dancenetwork.controller.closed;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@SpringBootTest
@AutoConfigureMockMvc
abstract class AbstractControllerTest
{

  @Autowired
  protected MockMvc mockMvc;

  @MockBean
  private JwtDecoder jwtDecoder;

  @Autowired
  private WebApplicationContext context;

  private String jwt;

  private String sub = UUID.randomUUID().toString();

  private Instant now = Instant.now();

  private Instant exp = Instant.now().plusSeconds(60);

  @BeforeEach
  void setup()
  {
    mockMvc = MockMvcBuilders
        .webAppContextSetup(context)
        .apply(SecurityMockMvcConfigurers.springSecurity())
        .build();

    jwt = generateJwtToken();

    MockHttpServletRequest mockRequest = new MockHttpServletRequest();
    mockRequest.addHeader("Authorization", jwt);

    ServletRequestAttributes servletRequestAttributes = new ServletRequestAttributes(mockRequest);
    RequestContextHolder.setRequestAttributes(servletRequestAttributes);

    Jwt jwt = new Jwt("token", now, exp, Map.of("alg", "HS256"),
        Map.of("sub", sub));
    when(jwtDecoder.decode(anyString())).thenReturn(jwt);
  }

  protected String getJwt()
  {
    return jwt;
  }

  private String generateJwtToken()
  {
    return Jwts.builder()
        .setSubject(sub)
        .setIssuedAt(Date.from(now))
        .setExpiration(Date.from(exp))
        .signWith(SignatureAlgorithm.HS256,
            "mySecretKeyadsasdasdasdasdasdascasfasfasfasfdasfasdfasfadfasdfasdf")
        .claim("sub", sub)
        .compact();
  }
}
