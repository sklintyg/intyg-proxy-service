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
package se.inera.intyg.intygproxyservice.integration.api.elva77;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class Elva77ResponseTest {

  @Nested
  class ErrorTests {

    @Test
    void shouldReturnResponseWithResultError() {
      assertEquals(Result.ERROR, Elva77Response.error().getResult());
    }

    @Test
    void shouldReturnResponseWithoutCitizen() {
      assertNull(Elva77Response.error().getCitizen());
    }
  }

  @Nested
  class InactiveTests {

    @Test
    void shouldReturnResponseWithResultInfo() {
      assertEquals(Result.INFO, Elva77Response.inactive(null).getResult());
    }

    @Test
    void shouldReturnResponseWithInactiveCitizen() {
      final var subjectOfCareId = "personId";
      final var expectedCitizen =
          Citizen.builder().subjectOfCareId(subjectOfCareId).isActive(false).build();

      assertEquals(expectedCitizen, Elva77Response.inactive(subjectOfCareId).getCitizen());
    }
  }
}
