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
package se.inera.intyg.intygproxyservice.common;

import java.util.Objects;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import se.inera.intyg.intygproxyservice.config.RedisConfig;
import tools.jackson.databind.json.JsonMapper;

@Slf4j
public class CacheUtility {

  private CacheUtility() {
    throw new IllegalStateException("Utility class");
  }

  public static <T> Optional<T> get(
      CacheManager cacheManager, JsonMapper jsonMapper, String objectKey, Class<T> objectClass) {
    try {
      final var cacheValue =
          Objects.requireNonNull(cacheManager.getCache(RedisConfig.PERSON_CACHE))
              .get(objectKey, String.class);

      if (cacheValue == null || cacheValue.isEmpty()) {
        return Optional.empty();
      }

      return Optional.of(jsonMapper.readValue(cacheValue, objectClass));
    } catch (Exception e) {
      log.error("Error retrieving person from personCache", e);
      return Optional.empty();
    }
  }

  public static <T> void save(
      CacheManager cacheManager,
      JsonMapper jsonMapper,
      T object,
      String objectKey,
      String cacheKey) {
    try {
      Objects.requireNonNull(cacheManager.getCache(cacheKey))
          .put(objectKey, jsonMapper.writeValueAsString(object));
    } catch (Exception e) {
      log.error("Error saving person to personCache", e);
    }
  }
}
