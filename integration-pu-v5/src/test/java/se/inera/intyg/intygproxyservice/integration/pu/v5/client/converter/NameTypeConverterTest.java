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
package se.inera.intyg.intygproxyservice.integration.pu.v5.client.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static se.inera.intyg.intygproxyservice.integration.pu.v5.TestData.GIVEN_NAME;
import static se.inera.intyg.intygproxyservice.integration.pu.v5.TestData.MIDDLE_NAME;
import static se.inera.intyg.intygproxyservice.integration.pu.v5.TestData.SURNAME;
import static se.inera.intyg.intygproxyservice.integration.pu.v5.TestData.nameType;
import static se.inera.intyg.intygproxyservice.integration.pu.v5.client.converter.NameTypeConverter.givenName;
import static se.inera.intyg.intygproxyservice.integration.pu.v5.client.converter.NameTypeConverter.middleName;
import static se.inera.intyg.intygproxyservice.integration.pu.v5.client.converter.NameTypeConverter.surname;

import org.junit.jupiter.api.Test;
import se.riv.strategicresourcemanagement.persons.person.v5.NameType;

class NameTypeConverterTest {

  @Test
  void shallReturnGivenNameIfExists() {
    assertEquals(GIVEN_NAME, givenName(nameType()));
  }

  @Test
  void shallReturnNullGivenNameIfNameTypeNull() {
    assertNull(givenName(null));
  }

  @Test
  void shallReturnNullGivenNameIfGivenNameNull() {
    assertNull(givenName(new NameType()));
  }

  @Test
  void shallReturnMiddleNameIfExists() {
    assertEquals(MIDDLE_NAME, middleName(nameType()));
  }

  @Test
  void shallReturnNullMiddleNameIfNameTypeNull() {
    assertNull(middleName(null));
  }

  @Test
  void shallReturnNullMiddleNameIfMiddleNameNull() {
    assertNull(middleName(new NameType()));
  }

  @Test
  void shallReturnSurnameIfExists() {
    assertEquals(SURNAME, surname(nameType()));
  }

  @Test
  void shallReturnNullSurnameIfNameTypeNull() {
    assertNull(surname(null));
  }

  @Test
  void shallReturnNullSurnameIfSurnameNull() {
    assertNull(surname(new NameType()));
  }
}
