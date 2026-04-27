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
import se.inera.intyg.intygproxyservice.integration.api.authorization.model.HsaSystemRole;
import se.riv.infrastructure.directory.authorizationmanagement.v2.HsaSystemRoleType;

class HsaSystemRoleTypeConverterTest {

  private static final HsaSystemRoleTypeConverter converter = new HsaSystemRoleTypeConverter();

  @Test
  void shouldConvertNull() {
    final var response = converter.convert(null);

    assertEquals(HsaSystemRole.builder().build(), response);
  }

  @Test
  void shouldConvertRole() {
    final var type = new HsaSystemRoleType();
    type.setRole("ROLE");

    final var response = converter.convert(type);

    assertEquals(type.getRole(), response.getRole());
  }

  @Test
  void shouldConvertSystemId() {
    final var type = new HsaSystemRoleType();
    type.setSystemId("SYSTEM ID");

    final var response = converter.convert(type);

    assertEquals(type.getSystemId(), response.getSystemId());
  }
}
