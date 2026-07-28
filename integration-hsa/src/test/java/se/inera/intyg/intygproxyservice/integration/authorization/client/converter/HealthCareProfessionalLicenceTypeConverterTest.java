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
package se.inera.intyg.intygproxyservice.integration.authorization.client.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.intygproxyservice.integration.api.authorization.model.HealthCareProfessionalLicence;
import se.riv.infrastructure.directory.authorizationmanagement.v2.HealthCareProfessionalLicenceType;

@ExtendWith(MockitoExtension.class)
class HealthCareProfessionalLicenceTypeConverterTest {

  @InjectMocks private HealthCareProfessionalLicenceTypeConverter converter;

  @Test
  void shouldConvertCode() {
    final var type = new HealthCareProfessionalLicenceType();
    type.setHealthCareProfessionalLicenceCode("CODE");

    final var response = converter.convert(type);

    assertEquals(
        type.getHealthCareProfessionalLicenceCode(),
        response.getHealthCareProfessionalLicenceCode());
  }

  @Test
  void shouldConvertName() {
    final var type = new HealthCareProfessionalLicenceType();
    type.setHealthCareProfessionalLicenceName("NAME");

    final var response = converter.convert(type);

    assertEquals(
        type.getHealthCareProfessionalLicenceName(),
        response.getHealthCareProfessionalLicenceName());
  }

  @Test
  void shouldReturnEmptyIfNull() {
    final var response = converter.convert(null);

    assertEquals(HealthCareProfessionalLicence.builder().build(), response);
  }
}
