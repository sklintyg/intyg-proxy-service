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
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedHsaPerson.Speciality;

@ExtendWith(MockitoExtension.class)
class SpecialitiesConverterTest {

  @InjectMocks private SpecialitiesConverter specialitiesConverter;

  private static final String SPECIALITY_NAME = "specialityName";
  private static final String SPECIALITY_CODE = "specialityCode";
  private static final Speciality SPECIALITY =
      Speciality.builder().specialityName(SPECIALITY_NAME).specialityCode(SPECIALITY_CODE).build();

  @Test
  void shouldConvertName() {
    final var result = specialitiesConverter.convert(SPECIALITY);
    assertEquals(SPECIALITY_NAME, result.getSpecialityName());
  }

  @Test
  void shouldConvertCode() {
    final var result = specialitiesConverter.convert(SPECIALITY);
    assertEquals(SPECIALITY_CODE, result.getSpecialityCode());
  }
}
