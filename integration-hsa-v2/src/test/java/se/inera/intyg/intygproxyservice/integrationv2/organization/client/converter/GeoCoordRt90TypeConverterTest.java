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
import se.inera.intyg.intygproxyservice.integration.api.organization.model.GeoCoordRt90;
import se.inera.intyg.intygproxyservice.se.riv.infrastructure.directory.organization.getunitresponder.v5.GeoCoordRt90Type;

class GeoCoordRt90TypeConverterTest {

  private final GeoCoordRt90TypeConverter geoCoordRt90TypeConverter =
      new GeoCoordRt90TypeConverter();

  private static GeoCoordRt90Type type;

  @BeforeEach
  void setup() {
    type = new GeoCoordRt90Type();
    type.setXCoordinate("X");
    type.setYCoordinate("Y");
  }

  @Test
  void shouldConvertNull() {
    final var response = geoCoordRt90TypeConverter.convert(null);

    assertEquals(GeoCoordRt90.builder().build(), response);
  }

  @Test
  void shouldConvertX() {
    final var response = geoCoordRt90TypeConverter.convert(type);

    assertEquals(type.getXCoordinate(), response.getXCoordinate());
  }

  @Test
  void shouldConvertY() {
    final var response = geoCoordRt90TypeConverter.convert(type);

    assertEquals(type.getYCoordinate(), response.getYCoordinate());
  }
}
