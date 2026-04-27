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
package se.inera.intyg.intygproxyservice.integration.api.organization;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.Address;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.Unit;

class AddressConverterTest {

  private static final String STREET = "Storgatan";
  private static final String STREET_NUMBER = "10";
  private static final String STREET_LETTER = "B";
  private static final String ZIP_CODE = "12345";
  private static final String CITY = "Stockholm";

  @Nested
  class WhenUnitIsNull {

    @Test
    void shouldThrowNullPointerException() {
      assertThrows(IllegalStateException.class, () -> AddressConverter.convert(null));
    }
  }

  @Nested
  class WhenAddressIsNotNull {

    @Test
    void shouldPopulateAddressFromAddressField() {
      final var address = new Address(STREET, STREET_NUMBER, STREET_LETTER, ZIP_CODE, CITY);
      final var unit = Unit.builder().address(address).build();

      final var result = AddressConverter.convert(unit);

      assertAll(
          () -> assertEquals(STREET, result.getAddress().street()),
          () -> assertEquals(STREET_NUMBER, result.getAddress().streetNumber()),
          () -> assertEquals(STREET_LETTER, result.getAddress().streetLetter()),
          () -> assertEquals(ZIP_CODE, result.getAddress().zipCode()),
          () -> assertEquals(CITY, result.getAddress().city())
      );
    }

    @Test
    void shouldPopulatePostalAddressListFromAddressField() {
      final var address = new Address(STREET, STREET_NUMBER, STREET_LETTER, ZIP_CODE, CITY);
      final var unit = Unit.builder().address(address).build();

      final var result = AddressConverter.convert(unit);

      assertEquals(List.of(STREET, STREET_NUMBER, STREET_LETTER, ZIP_CODE, CITY),
          result.getPostalAddress());
    }

    @Test
    void shouldPreserveOtherUnitFieldsWhenAddressIsPresent() {
      final var address = new Address(STREET, STREET_NUMBER, STREET_LETTER, ZIP_CODE, CITY);
      final var unit = Unit.builder().unitHsaId("HSA-123").unitName("Enheten").address(address)
          .build();

      final var result = AddressConverter.convert(unit);

      assertAll(
          () -> assertEquals("HSA-123", result.getUnitHsaId()),
          () -> assertEquals("Enheten", result.getUnitName())
      );
    }
  }

  @Nested
  class WhenAddressIsNullAndPostalAddressListIsUsed {

    @Test
    void shouldReturnNullFieldsWhenPostalAddressIsEmpty() {
      final var unit = Unit.builder().postalAddress(Collections.emptyList()).build();

      final var result = AddressConverter.convert(unit);

      assertAll(
          () -> assertNull(result.getAddress().street()),
          () -> assertNull(result.getAddress().streetNumber()),
          () -> assertNull(result.getAddress().streetLetter()),
          () -> assertNull(result.getAddress().zipCode()),
          () -> assertNull(result.getAddress().city())
      );
    }

    @Test
    void shouldSetStreetOnlyWhenPostalAddressHasOneElement() {
      final var unit = Unit.builder().postalAddress(List.of(STREET)).build();

      final var result = AddressConverter.convert(unit);

      assertAll(
          () -> assertEquals(STREET, result.getAddress().street()),
          () -> assertNull(result.getAddress().streetNumber()),
          () -> assertNull(result.getAddress().streetLetter()),
          () -> assertNull(result.getAddress().zipCode()),
          () -> assertNull(result.getAddress().city())
      );
    }

    @Test
    void shouldSetStreetAndStreetNumberWhenPostalAddressHasTwoElements() {
      final var unit = Unit.builder().postalAddress(List.of(STREET, STREET_NUMBER)).build();

      final var result = AddressConverter.convert(unit);

      assertAll(
          () -> assertEquals(STREET, result.getAddress().street()),
          () -> assertEquals(STREET_NUMBER, result.getAddress().streetNumber()),
          () -> assertNull(result.getAddress().streetLetter()),
          () -> assertNull(result.getAddress().zipCode()),
          () -> assertNull(result.getAddress().city())
      );
    }

    @Test
    void shouldSetStreetAndStreetNumberAndLetterWhenPostalAddressHasThreeElements() {
      final var unit = Unit.builder()
          .postalAddress(List.of(STREET, STREET_NUMBER, STREET_LETTER)).build();

      final var result = AddressConverter.convert(unit);

      assertAll(
          () -> assertEquals(STREET, result.getAddress().street()),
          () -> assertEquals(STREET_NUMBER, result.getAddress().streetNumber()),
          () -> assertEquals(STREET_LETTER, result.getAddress().streetLetter()),
          () -> assertNull(result.getAddress().zipCode()),
          () -> assertNull(result.getAddress().city())
      );
    }

    @Test
    void shouldSetAllFieldsExceptCityWhenPostalAddressHasFourElements() {
      final var unit = Unit.builder()
          .postalAddress(List.of(STREET, STREET_NUMBER, STREET_LETTER, ZIP_CODE)).build();

      final var result = AddressConverter.convert(unit);

      assertAll(
          () -> assertEquals(STREET, result.getAddress().street()),
          () -> assertEquals(STREET_NUMBER, result.getAddress().streetNumber()),
          () -> assertEquals(STREET_LETTER, result.getAddress().streetLetter()),
          () -> assertEquals(ZIP_CODE, result.getAddress().zipCode()),
          () -> assertNull(result.getAddress().city())
      );
    }

    @Test
    void shouldSetAllFieldsWhenPostalAddressHasFiveElements() {
      final var unit = Unit.builder()
          .postalAddress(List.of(STREET, STREET_NUMBER, STREET_LETTER, ZIP_CODE, CITY)).build();

      final var result = AddressConverter.convert(unit);

      assertAll(
          () -> assertEquals(STREET, result.getAddress().street()),
          () -> assertEquals(STREET_NUMBER, result.getAddress().streetNumber()),
          () -> assertEquals(STREET_LETTER, result.getAddress().streetLetter()),
          () -> assertEquals(ZIP_CODE, result.getAddress().zipCode()),
          () -> assertEquals(CITY, result.getAddress().city())
      );
    }

    @Test
    void shouldPopulatePostalAddressListFromPostalAddressElements() {
      final var unit = Unit.builder()
          .postalAddress(List.of(STREET, STREET_NUMBER, STREET_LETTER, ZIP_CODE, CITY)).build();

      final var result = AddressConverter.convert(unit);

      assertEquals(List.of(STREET, STREET_NUMBER, STREET_LETTER, ZIP_CODE, CITY),
          result.getPostalAddress());
    }
  }
}
