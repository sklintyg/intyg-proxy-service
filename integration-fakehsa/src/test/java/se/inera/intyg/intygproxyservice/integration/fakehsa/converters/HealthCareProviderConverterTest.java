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
package se.inera.intyg.intygproxyservice.integration.fakehsa.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedCareProvider;

class HealthCareProviderConverterTest {

  private HealthCareProviderConverter healthCareProviderConverter;

  private static final String VALUE = "value";

  @BeforeEach
  void setUp() {
    healthCareProviderConverter = new HealthCareProviderConverter();
  }

  @Nested
  class ConvertId {

    @Test
    void shouldConvertId() {
      final var parsedCareProvider = ParsedCareProvider.builder().id(VALUE).build();
      final var result = healthCareProviderConverter.convert(parsedCareProvider);
      assertEquals(parsedCareProvider.getId(), result.getHealthCareProviderHsaId());
    }

    @Test
    void shouldNotConvertId() {
      final var parsedCareProvider = ParsedCareProvider.builder().build();
      final var result = healthCareProviderConverter.convert(parsedCareProvider);
      assertNull(result.getHealthCareProviderHsaId());
    }
  }

  @Nested
  class ConvertName {

    @Test
    void shouldConvertName() {
      final var parsedCareProvider = ParsedCareProvider.builder().name(VALUE).build();
      final var result = healthCareProviderConverter.convert(parsedCareProvider);
      assertEquals(parsedCareProvider.getName(), result.getHealthCareProviderName());
    }

    @Test
    void shouldNotConvertName() {
      final var parsedCareProvider = ParsedCareProvider.builder().build();
      final var result = healthCareProviderConverter.convert(parsedCareProvider);
      assertNull(result.getHealthCareProviderName());
    }
  }
}
