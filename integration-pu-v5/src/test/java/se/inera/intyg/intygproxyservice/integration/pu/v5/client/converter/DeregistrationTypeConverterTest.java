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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.intygproxyservice.integration.pu.v5.client.converter.DeregistrationTypeConverter.deceased;
import static se.inera.intyg.intygproxyservice.integration.pu.v5.configuration.configuration.PuConstants.DEREGISTRATION_REASON_CODE_FOR_DECEASED;

import org.junit.jupiter.api.Test;
import se.riv.strategicresourcemanagement.persons.person.v5.DeregistrationType;

class DeregistrationTypeConverterTest {

  @Test
  void shallReturnTrueIfDeregistrationCodeIsAV() {
    final var deregistrationType = new DeregistrationType();
    deregistrationType.setDeregistrationReasonCode(DEREGISTRATION_REASON_CODE_FOR_DECEASED);
    assertTrue(deceased(deregistrationType));
  }

  @Test
  void shallReturnFalseIfDeregistrationCodeIsNotAV() {
    final var deregistrationType = new DeregistrationType();
    deregistrationType.setDeregistrationReasonCode("NOT_AV");
    assertFalse(deceased(deregistrationType));
  }

  @Test
  void shallReturnFalseIfDeregistrationCodeIsNull() {
    final var deregistrationType = new DeregistrationType();
    assertFalse(deceased(deregistrationType));
  }

  @Test
  void shallReturnFalseIfDeregistrationIsNull() {
    assertFalse(deceased(null));
  }
}
