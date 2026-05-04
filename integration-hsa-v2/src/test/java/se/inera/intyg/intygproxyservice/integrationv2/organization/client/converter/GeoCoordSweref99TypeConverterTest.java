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
package se.inera.intyg.intygproxyservice.integrationv2.organization.client.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.GeoCoordSweref99;
import se.inera.intyg.intygproxyservice.se.riv.infrastructure.directory.organization.getunitresponder.v5.GeoCoordSWEREF99Type;

class GeoCoordSweref99TypeConverterTest {

  private final GeoCoordSweref99TypeConverter converter = new GeoCoordSweref99TypeConverter();

  private static GeoCoordSWEREF99Type type;

  @BeforeEach
  void setup() {
    type = new GeoCoordSWEREF99Type();
    type.setECoordinate("E");
    type.setNCoordinate("N");
  }

  @Test
  void shouldConvertNull() {
    final var response = converter.convert(null);

    assertEquals(GeoCoordSweref99.builder().build(), response);
  }

  @Test
  void shouldConvertE() {
    final var response = converter.convert(type);

    assertEquals(type.getECoordinate(), response.getECoordinate());
  }

  @Test
  void shouldConvertN() {
    final var response = converter.convert(type);

    assertEquals(type.getNCoordinate(), response.getNCoordinate());
  }
}
