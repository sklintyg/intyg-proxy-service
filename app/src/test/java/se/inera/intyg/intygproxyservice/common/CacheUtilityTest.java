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

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.CacheManager;
import se.inera.intyg.intygproxyservice.config.RedisConfig;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuResponse;
import tools.jackson.databind.json.JsonMapper;

@ExtendWith(MockitoExtension.class)
class CacheUtilityTest {

  @Mock private CacheManager cacheManager;
  @Mock private JsonMapper jsonMapper;
  @Mock private PuResponse puResponse;

  private static final String OBJECT_KEY = "objectKey";

  @BeforeEach
  void setUp() {
    when(cacheManager.getCache(RedisConfig.PERSON_CACHE)).thenThrow(new RuntimeException());
  }

  @Test
  void shouldReturnOptionalEmptyIfExceptionOnCacheGetRequest() {
    final var objectClass = PuResponse.class;
    final var result = CacheUtility.get(cacheManager, jsonMapper, OBJECT_KEY, objectClass);
    assertEquals(Optional.empty(), result);
  }

  @Test
  void shouldNotThrowIfExceptionOnCacheSaveRequest() {
    assertDoesNotThrow(
        () ->
            CacheUtility.save(
                cacheManager, jsonMapper, puResponse, OBJECT_KEY, RedisConfig.PERSON_CACHE));
  }
}
