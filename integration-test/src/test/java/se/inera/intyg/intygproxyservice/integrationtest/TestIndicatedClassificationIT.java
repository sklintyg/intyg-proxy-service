package se.inera.intyg.intygproxyservice.integrationtest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.intygproxyservice.integrationtest.TestDataPatient.DECEASED_TEST_INDICATED_PERSON;
import static se.inera.intyg.intygproxyservice.integrationtest.TestDataPatient.PROTECTED_PERSON_DTO;

import java.io.IOException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import se.inera.intyg.intygproxyservice.integrationtest.util.ApiUtil;
import se.inera.intyg.intygproxyservice.integrationtest.util.Containers;
import se.inera.intyg.intygproxyservice.person.dto.PersonRequest;

@ActiveProfiles({"integration-test", "dev"})
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class TestIndicatedClassificationIT {

  private static final GenericContainer<?> redisContainer = Containers.getRedisContainer();

  @LocalServerPort
  private int port;

  private final TestRestTemplate restTemplate;
  private ApiUtil api;

  @Autowired
  public TestIndicatedClassificationIT(TestRestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @DynamicPropertySource
  static void configureProperties(DynamicPropertyRegistry registry) {
    Containers.configurePuProperties(registry);
    registry.add("putjanst.testindicated.reclassify.active.except.ssn",
        () -> String.format("191212121212, %s", PROTECTED_PERSON_DTO.getPersonnummer()));
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

  @Nested
  class ClassifyTestIndicated {

    @Test
    void shallSwapTestIndicatedFlagToFalseFromTrueToIfReclassifyIsSetToNotIncludeId() {
      final var request = PersonRequest.builder()
          .personId(DECEASED_TEST_INDICATED_PERSON.getPersonnummer())
          .build();

      final var response = api.person(request);

      assertAll(
          () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
          () -> assertFalse(response.getBody().getPerson().isTestIndicator())
      );
    }

    @Test
    void shallSwapTestIndicatedFlagToTrueFromFalseIfReclassifyIsSetToIncludeId() {
      final var request = PersonRequest.builder()
          .personId(PROTECTED_PERSON_DTO.getPersonnummer())
          .build();

      final var response = api.person(request);

      assertAll(
          () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
          () -> assertTrue(response.getBody().getPerson().isTestIndicator())
      );
    }
  }
}
