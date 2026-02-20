package se.inera.intyg.intygproxyservice.integrationtest.util;

import io.github.microcks.testcontainers.MicrocksContainer;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.utility.DockerImageName;

public final class Containers {

  private static final GenericContainer<?> REDIS_CONTAINER;
  private static final MicrocksContainer PU_GET_PERSON_V5_CONTAINER;

  static {
    REDIS_CONTAINER = new GenericContainer<>(
        DockerImageName.parse("redis:8.6.0-alpine")
    ).withExposedPorts(6379).withReuse(true);
    REDIS_CONTAINER.start();

    PU_GET_PERSON_V5_CONTAINER = new MicrocksContainer(
        DockerImageName.parse("quay.io/microcks/microcks-uber:1.13.2"))
        .withMainArtifacts("soapui/GetPersonsForProfileResponder-5.0.xml")
        .withLogConsumer(new Slf4jLogConsumer(LoggerFactory.getLogger("MicrocksContainerLogs")))
        .withReuse(true);
    PU_GET_PERSON_V5_CONTAINER.start();

    // Register shutdown hook to stop containers when JVM exits
    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      if (REDIS_CONTAINER.isRunning()) {
        REDIS_CONTAINER.stop();
      }
      if (PU_GET_PERSON_V5_CONTAINER.isRunning()) {
        PU_GET_PERSON_V5_CONTAINER.stop();
      }
    }));
  }

  private Containers() {
    throw new IllegalStateException("Utility class");
  }

  public static GenericContainer<?> getRedisContainer() {
    return REDIS_CONTAINER;
  }

  public static String getRedisHost() {
    return REDIS_CONTAINER.getHost();
  }

  public static Integer getRedisPort() {
    return REDIS_CONTAINER.getMappedPort(6379);
  }

  public static MicrocksContainer getPuGetPersonV5Container() {
    return PU_GET_PERSON_V5_CONTAINER;
  }

  public static String getGetPersonsForProfileEndpoint() {
    return PU_GET_PERSON_V5_CONTAINER.getSoapMockEndpoint("GetPersonsForProfile", "5.0");
  }

  /**
   * Configure Redis properties for Spring Boot tests. Call this from @DynamicPropertySource methods
   * in test classes.
   *
   * @param registry the Spring dynamic property registry
   */
  public static void configureRedisProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.data.redis.host", Containers::getRedisHost);
    registry.add("spring.data.redis.port", () -> Containers.getRedisPort().toString());
  }

  /**
   * Configure PU GetPersonsForProfile endpoint properties for Spring Boot tests. Call this from
   *
   * @param registry the Spring dynamic property registry
   * @DynamicPropertySource methods in test classes.
   */
  public static void configurePuProperties(DynamicPropertyRegistry registry) {
    registry.add("integration.pu.getpersonsforprofile.endpoint",
        Containers::getGetPersonsForProfileEndpoint);
    registry.add("integration.pu.cache.seconds", () -> "86400");
  }
}
