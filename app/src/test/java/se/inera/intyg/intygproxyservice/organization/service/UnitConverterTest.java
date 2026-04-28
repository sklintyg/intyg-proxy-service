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
package se.inera.intyg.intygproxyservice.organization.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.Address;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.Unit;
import se.inera.intyg.intygproxyservice.organization.dto.UnitDTO;

@ExtendWith(MockitoExtension.class)
class UnitConverterTest {

  @InjectMocks private UnitConverter unitConverter;

  @Test
  void shouldConvertUnit() {
    final var expectedUnit = getUnitDTO();

    final var unit = getUnit();

    final var actual = unitConverter.convert(unit);

    assertEquals(expectedUnit, actual);
  }

  @Test
  void shouldReturnNullIfUnitIsNull() {
    assertNull(unitConverter.convert(null));
  }

  private static Unit getUnit() {
    return Unit.builder()
        .unitHsaId("test")
        .unitStartDate(LocalDateTime.MIN)
        .unitEndDate(LocalDateTime.MAX)
        .feignedUnit(false)
        .unitName("name")
        .telephoneNumber(List.of("12345"))
        .address(new Address("Street 123 A", "123456", "city"))
        .mail("mail")
        .build();
  }

  private static UnitDTO getUnitDTO() {
    return UnitDTO.builder()
        .unitHsaId("test")
        .unitStartDate(LocalDateTime.MIN)
        .unitEndDate(LocalDateTime.MAX)
        .feignedUnit(false)
        .unitName("name")
        .telephoneNumber(List.of("12345"))
        .address(new Address("Street 123 A", "123456", "city"))
        .mail("mail")
        .build();
  }
}
