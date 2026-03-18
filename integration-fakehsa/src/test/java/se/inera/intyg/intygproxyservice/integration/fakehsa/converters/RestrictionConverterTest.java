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

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedHsaPerson.Restrictions;

@ExtendWith(MockitoExtension.class)
class RestrictionConverterTest {

  private static final String RESTRICTION_NAME = "restrictionName";
  private static final String RESTRICTION_CODE = "restrictionCode";
  private static final Restrictions RESTRICTION =
      Restrictions.builder()
          .restrictionName(RESTRICTION_NAME)
          .restrictionCode(RESTRICTION_CODE)
          .build();
  @InjectMocks private RestrictionConverter restrictionConverter;

  @Test
  void shouldConvertName() {
    final var result = restrictionConverter.convert(RESTRICTION);
    assertEquals(RESTRICTION_NAME, result.getRestrictionName());
  }

  @Test
  void shouldConvertCode() {
    final var result = restrictionConverter.convert(RESTRICTION);
    assertEquals(RESTRICTION_CODE, result.getRestrictionCode());
  }
}
