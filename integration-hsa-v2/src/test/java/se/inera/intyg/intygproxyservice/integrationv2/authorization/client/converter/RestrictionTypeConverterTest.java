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
package se.inera.intyg.intygproxyservice.integrationv2.authorization.client.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.intygproxyservice.integration.api.authorization.model.Restriction;
import se.inera.intyg.intygproxyservice.se.riv.infrastructure.directory.authorizationmanagement.v2.RestrictionType;

@ExtendWith(MockitoExtension.class)
class RestrictionTypeConverterTest {

  @InjectMocks private RestrictionTypeConverter restrictionTypeConverter;

  @Test
  void shouldConvertCode() {
    final var type = new RestrictionType();
    type.setHealthCareProfessionalLicenceCode("CODE");

    final var response = restrictionTypeConverter.convert(type);

    assertEquals(
        type.getHealthCareProfessionalLicenceCode(),
        response.getHealthCareProfessionalLicenceCode());
  }

  @Test
  void shouldConvertRestrictionCode() {
    final var type = new RestrictionType();
    type.setRestrictionCode("R_CODE");

    final var response = restrictionTypeConverter.convert(type);

    assertEquals(type.getRestrictionCode(), response.getRestrictionCode());
  }

  @Test
  void shouldConvertRestrictionName() {
    final var type = new RestrictionType();
    type.setRestrictionName("R_NAME");

    final var response = restrictionTypeConverter.convert(type);

    assertEquals(type.getRestrictionName(), response.getRestrictionName());
  }

  @Test
  void shouldReturnEmptyIfNull() {
    final var response = restrictionTypeConverter.convert(null);

    assertEquals(Restriction.builder().build(), response);
  }
}
