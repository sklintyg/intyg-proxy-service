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
package se.inera.intyg.intygproxyservice.integrationtest.util;

import io.github.microcks.testcontainers.MicrocksContainer;
import java.io.IOException;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.utility.DockerImageName;

public final class Containers {

  private static GenericContainer<?> redisContainer;
  private static MicrocksContainer puGetPersonV5Container;

  static {
    Runtime.getRuntime().addShutdownHook(new Thread(Containers::stopContainers));
  }

  private Containers() {
    throw new IllegalStateException("Utility class");
  }

  public static synchronized GenericContainer<?> getRedisContainer() {
    if (redisContainer == null) {
      redisContainer =
          new GenericContainer<>(DockerImageName.parse("redis:8.6.0-alpine"))
              .withExposedPorts(6379)
              .withReuse(true);
      redisContainer.start();
    }
    return redisContainer;
  }

  public static String getRedisHost() {
    return getRedisContainer().getHost();
  }

  public static Integer getRedisPort() {
    return getRedisContainer().getMappedPort(6379);
  }

  public static synchronized MicrocksContainer getPuGetPersonV5Container() {
    if (puGetPersonV5Container == null) {
      puGetPersonV5Container =
          new MicrocksContainer(DockerImageName.parse("quay.io/microcks/microcks-uber:1.13.2"))
              .withMainArtifacts("soapui/GetPersonsForProfileResponder-5.0.xml")
              .withLogConsumer(
                  new Slf4jLogConsumer(LoggerFactory.getLogger("MicrocksContainerLogs")))
              .withReuse(true);
      puGetPersonV5Container.start();
    }
    return puGetPersonV5Container;
  }

  public static String getGetPersonsForProfileEndpoint() {
    return getPuGetPersonV5Container().getSoapMockEndpoint("GetPersonsForProfile", "5.0");
  }

  public static synchronized void flushRedis() throws IOException, InterruptedException {
    getRedisContainer().execInContainer("redis-cli", "flushall");
  }

  private static synchronized void stopContainers() {
    if (redisContainer != null && redisContainer.isRunning()) {
      redisContainer.stop();
    }
    if (puGetPersonV5Container != null && puGetPersonV5Container.isRunning()) {
      puGetPersonV5Container.stop();
    }
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
   * @param registry the Spring dynamic property registry @DynamicPropertySource methods in test
   *     classes.
   */
  public static void configurePuProperties(DynamicPropertyRegistry registry) {
    registry.add(
        "integration.pu.getpersonsforprofile.endpoint",
        Containers::getGetPersonsForProfileEndpoint);
    registry.add("integration.pu.cache.seconds", () -> "86400");
  }
}
