package se.inera.intyg.intygproxyservice.integrationtest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.intygproxyservice.config.RedisConfig.PERSON_CACHE;
import static se.inera.intyg.intygproxyservice.integrationtest.TestDataPatient.ATHENA_REACT_ANDERSSON;
import static se.inera.intyg.intygproxyservice.integrationtest.TestDataPatient.ATHENA_REACT_ANDERSSON_DTO;
import static se.inera.intyg.intygproxyservice.integrationtest.TestDataPatient.DECEASED_TEST_INDICATED_PERSON;
import static se.inera.intyg.intygproxyservice.integrationtest.TestDataPatient.LILLTOLVAN;
import static se.inera.intyg.intygproxyservice.integrationtest.TestDataPatient.PROTECTED_PERSON;
import static se.inera.intyg.intygproxyservice.integrationtest.TestDataPatient.PROTECTED_PERSON_DTO;
import static se.inera.intyg.intygproxyservice.integrationtest.TestDataPatient.TOLVAN;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.util.List;
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
import se.inera.intyg.intygproxyservice.common.HashUtility;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuResponse;
import se.inera.intyg.intygproxyservice.integrationtest.util.ApiUtil;
import se.inera.intyg.intygproxyservice.integrationtest.util.Containers;
import se.inera.intyg.intygproxyservice.person.dto.PersonDTO;
import se.inera.intyg.intygproxyservice.person.dto.PersonRequest;
import se.inera.intyg.intygproxyservice.person.dto.PersonsRequest;
import se.inera.intyg.intygproxyservice.person.dto.StatusDTOType;

@ActiveProfiles({"integration-test", "dev"})
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class GetPersonsForProfileV5IT {

  private static final GenericContainer<?> redisContainer = Containers.getRedisContainer();

  @LocalServerPort
  private int port;

  private final TestRestTemplate restTemplate;
  private ApiUtil api;

  @Autowired
  public GetPersonsForProfileV5IT(TestRestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @DynamicPropertySource
  static void configureProperties(DynamicPropertyRegistry registry) {
    Containers.configurePuProperties(registry);
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
  class GetPerson {

    @Test
    void shallReturnTestPerson() {
      final var request = PersonRequest.builder()
          .personId("195401782395")
          .build();

      final var response = api.person(request);

      assertAll(
          () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
          () -> assertEquals(PROTECTED_PERSON_DTO, response.getBody().getPerson())
      );
    }

    @Test
    void shallReturnDeceasedTestIndicatedPerson() {
      final var request = PersonRequest.builder()
          .personId(DECEASED_TEST_INDICATED_PERSON.getPersonnummer())
          .build();

      final var response = api.person(request);

      assertAll(
          () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
          () -> assertEquals(DECEASED_TEST_INDICATED_PERSON, response.getBody().getPerson())
      );
    }

    @Test
    void shallNotSwapTestIndicatedFlagTrueToFalseIfReclassifyIdsAreNotSet() {
      final var request = PersonRequest.builder()
          .personId(DECEASED_TEST_INDICATED_PERSON.getPersonnummer())
          .build();

      final var response = api.person(request);

      assertAll(
          () -> assertEquals(
              HttpStatus.OK,
              response.getStatusCode()
          ),
          () -> assertEquals(
              DECEASED_TEST_INDICATED_PERSON.isTestIndicator(),
              response.getBody().getPerson().isTestIndicator()
          )
      );
    }

    @Test
    void shallNotSwapTestIndicatedFlagFalseToIfReclassifyIsNotSet() {
      final var request = PersonRequest.builder()
          .personId(PROTECTED_PERSON_DTO.getPersonnummer())
          .build();

      final var response = api.person(request);

      assertAll(
          () -> assertEquals(
              HttpStatus.OK,
              response.getStatusCode()
          ),
          () -> assertEquals(
              PROTECTED_PERSON_DTO.isTestIndicator(),
              response.getBody().getPerson().isTestIndicator()
          )
      );
    }

    @Test
    void shallReturnPatientFromCache() throws IOException, InterruptedException {
      final var objectMapper = new ObjectMapper();
      objectMapper.registerModule(new JavaTimeModule());
      final var cachedPuResponse = PuResponse.found(ATHENA_REACT_ANDERSSON);
      final var cacheString = objectMapper.writeValueAsString(cachedPuResponse)
          .replace("\"", "\\\"");

      redisContainer.execInContainer(
          "redis-cli",
          "set",
          String.format("%s::%s", PERSON_CACHE,
              HashUtility.hash(ATHENA_REACT_ANDERSSON.getPersonnummer().id())),
          String.format("\"%s\"", cacheString)
      );

      final var request = PersonRequest.builder()
          .personId(ATHENA_REACT_ANDERSSON.getPersonnummer().id())
          .build();

      final var response = api.person(request);

      assertAll(
          () -> assertEquals(
              HttpStatus.OK,
              response.getStatusCode()
          ),
          () -> assertEquals(
              ATHENA_REACT_ANDERSSON_DTO,
              response.getBody().getPerson()
          ),
          () -> assertEquals(
              StatusDTOType.FOUND,
              response.getBody().getStatus()
          )
      );
    }
  }

  @Nested
  class GetPersons {

    @Test
    void shallReturnTestPersons() {
      final var request = PersonsRequest.builder()
          .personIds(List.of(LILLTOLVAN.getPersonnummer(), TOLVAN.getPersonnummer()))
          .build();

      final var response = api.persons(request);

      assertAll(
          () -> assertEquals(
              HttpStatus.OK,
              response.getStatusCode()
          ),
          () -> assertEquals(
              LILLTOLVAN,
              response.getBody().getPersons().getFirst().getPerson()
          ),
          () -> assertEquals(
              StatusDTOType.FOUND,
              response.getBody().getPersons().getFirst().getStatus()
          ),
          () -> assertEquals(
              TOLVAN,
              response.getBody().getPersons().get(1).getPerson()
          ),
          () -> assertEquals(
              StatusDTOType.FOUND,
              response.getBody().getPersons().get(1).getStatus()
          )
      );
    }

    @Test
    void shallReturnNotFoundIfNoResponseForPerson() {
      final var request = PersonsRequest.builder()
          .personIds(List.of(LILLTOLVAN.getPersonnummer(), TOLVAN.getPersonnummer(),
              PROTECTED_PERSON_DTO.getPersonnummer()))
          .build();

      final var response = api.persons(request);

      assertAll(
          () -> assertEquals(
              HttpStatus.OK,
              response.getStatusCode()
          ),
          () -> assertEquals(
              LILLTOLVAN,
              response.getBody().getPersons().getFirst().getPerson()
          ),
          () -> assertEquals(
              StatusDTOType.FOUND,
              response.getBody().getPersons().getFirst().getStatus()
          ),
          () -> assertEquals(
              TOLVAN,
              response.getBody().getPersons().get(1).getPerson()
          ),
          () -> assertEquals(
              StatusDTOType.FOUND,
              response.getBody().getPersons().get(1).getStatus()
          ),
          () -> assertEquals(
              PersonDTO.builder().personnummer(PROTECTED_PERSON_DTO.getPersonnummer()).build(),
              response.getBody().getPersons().get(2).getPerson()
          ),
          () -> assertEquals(
              StatusDTOType.NOT_FOUND,
              response.getBody().getPersons().get(2).getStatus())
      );
    }

    @Test
    void shallReturnPatientInCacheAndFromPu() throws IOException, InterruptedException {
      final var objectMapper = new ObjectMapper();
      objectMapper.registerModule(new JavaTimeModule());
      final var cachedPuResponse = PuResponse.found(PROTECTED_PERSON);
      final var cacheString = objectMapper.writeValueAsString(cachedPuResponse)
          .replace("\"", "\\\"");

      redisContainer.execInContainer(
          "redis-cli",
          "set",
          String.format("%s::%s", PERSON_CACHE,
              HashUtility.hash(PROTECTED_PERSON_DTO.getPersonnummer())),
          String.format("\"%s\"", cacheString)
      );

      final var request = PersonsRequest.builder()
          .personIds(
              List.of(
                  LILLTOLVAN.getPersonnummer(),
                  TOLVAN.getPersonnummer(),
                  PROTECTED_PERSON_DTO.getPersonnummer()
              )
          ).build();

      final var response = api.persons(request);

      assertAll(
          () -> assertEquals(
              HttpStatus.OK,
              response.getStatusCode()
          ),
          () -> assertEquals(
              LILLTOLVAN,
              response.getBody().getPersons().getFirst().getPerson()
          ),
          () -> assertEquals(
              StatusDTOType.FOUND,
              response.getBody().getPersons().getFirst().getStatus()
          ),
          () -> assertEquals(
              TOLVAN,
              response.getBody().getPersons().get(1).getPerson()
          ),
          () -> assertEquals(
              StatusDTOType.FOUND,
              response.getBody().getPersons().get(1).getStatus()
          ),
          () -> assertEquals(
              PROTECTED_PERSON_DTO,
              response.getBody().getPersons().get(2).getPerson()
          ),
          () -> assertEquals(
              StatusDTOType.FOUND,
              response.getBody().getPersons().get(2).getStatus()
          )
      );
    }

    @Test
    void shallReturnErrorIfSeveralResponsesForPerson() {
      final var request = PersonsRequest.builder()
          .personIds(List.of(TOLVAN.getPersonnummer()))
          .build();

      final var response = api.persons(request);

      assertAll(
          () -> assertEquals(
              HttpStatus.OK,
              response.getStatusCode()
          ),
          () -> assertEquals(
              PersonDTO.builder().personnummer(TOLVAN.getPersonnummer()).build(),
              response.getBody().getPersons().getFirst().getPerson()
          ),
          () -> assertEquals(
              StatusDTOType.ERROR,
              response.getBody().getPersons().getFirst().getStatus()
          )
      );
    }
  }
}