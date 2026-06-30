/*
 * Copyright (C) 2026 Inera AB (http://www.inera.se)
 *
 * This file is part of sklintyg (https://github.com/sklintyg).
 *
 * sklintyg is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * sklintyg is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package se.inera.intyg.intygproxyservice.integrationtest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.intygproxyservice.integration.api.constants.PuConstants.FAKE_PU_PROFILE;

import java.io.IOException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.GenericContainer;
import se.inera.intyg.intygproxyservice.integrationtest.util.ApiUtil;
import se.inera.intyg.intygproxyservice.integrationtest.util.Containers;
import se.inera.intyg.intygproxyservice.person.dto.PersonRequest;

@ActiveProfiles({"integration-test", FAKE_PU_PROFILE})
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class GetPersonsForProfileFakePuIT {

  private static final GenericContainer<?> redisContainer = Containers.getRedisContainer();

  @LocalServerPort private int port;

  private final RestTemplate restTemplate = new RestTemplate();
  private ApiUtil api;

  @DynamicPropertySource
  static void configureProperties(DynamicPropertyRegistry registry) {
    Containers.configureRedisProperties(registry);
  }

  @AfterEach
  void tearDown() throws IOException, InterruptedException {
    redisContainer.execInContainer("redis-cli", "flushall");
  }

  @BeforeEach
  void setUp() {
    this.api = new ApiUtil(restTemplate, port);
  }

  @Test
  void shallReturnTestPerson() {
    final var request = PersonRequest.builder().personId("194011306125").build();

    final var response = api.person(request);

    assertAll(
        () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
        () -> assertEquals("194011306125", response.getBody().getPerson().getPersonnummer()),
        () -> assertEquals(Boolean.FALSE, response.getBody().getPerson().isTestIndicator()));
  }

  @Test
  void shallReturnTestIndicatedPerson() {
    final var request = PersonRequest.builder().personId("194110299221").build();

    final var response = api.person(request);

    assertAll(
        () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
        () -> assertEquals("194110299221", response.getBody().getPerson().getPersonnummer()),
        () -> assertEquals(Boolean.TRUE, response.getBody().getPerson().isTestIndicator()));
  }
}
