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
package se.inera.intyg.intygproxyservice.integration.fakepu.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.intygproxyservice.integration.fakepu.repository.DeceasedConverter.DEREGISTRATION_REASON_CODE_FOR_DECEASED;

import org.junit.jupiter.api.Test;
import se.inera.intyg.intygproxyservice.integration.fakepu.repository.model.ParsedDeregistration;

class DeceasedConverterTest {

  @Test
  void shallReturnTrueIfDeregistrationCodeIsAV() {
    assertTrue(
        DeceasedConverter.convert(
            ParsedDeregistration.builder()
                .deregistrationReasonCode(DEREGISTRATION_REASON_CODE_FOR_DECEASED)
                .build()));
  }

  @Test
  void shallReturnFalseIfDeregistrationCodeIsNotAV() {
    assertFalse(
        DeceasedConverter.convert(
            ParsedDeregistration.builder().deregistrationReasonCode("NOT_AV").build()));
  }

  @Test
  void shallReturnFalseIfDeregistrationCodeIsNull() {
    assertFalse(
        DeceasedConverter.convert(
            ParsedDeregistration.builder().deregistrationReasonCode(null).build()));
  }

  @Test
  void shallReturnFalseIfDeregistrationIsNull() {
    assertFalse(DeceasedConverter.convert(null));
  }
}
