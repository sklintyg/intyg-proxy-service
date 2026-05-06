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
package se.inera.intyg.intygproxyservice.integration.organization.client.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.riv.infrastructure.directory.organization.getunitresponder.v4.UnitType;
import se.riv.infrastructure.directory.organization.v2.AddressType;

@ExtendWith(MockitoExtension.class)
class StructuredAddressConverterTest {

  @Mock AddressTypeConverter addressTypeConverter;

  @InjectMocks StructuredAddressConverter structuredAddressConverter;

  @Nested
  class ConvertV2 {

    @Nested
    class Address {

      @Test
      void shouldReturnNullAddressWhenAddressLinesEmpty() {
        when(addressTypeConverter.convertV2(any())).thenReturn(Collections.emptyList());

        final var result = structuredAddressConverter.convertV2(new AddressType(), null);

        assertNull(result.address());
      }

      @Test
      void shouldReturnStreetFromAllLinesExceptLast() {
        when(addressTypeConverter.convertV2(any()))
            .thenReturn(List.of("Street 1", "Street 2", "12345 City"));

        final var result = structuredAddressConverter.convertV2(new AddressType(), null);

        assertEquals("Street 1 Street 2", result.address());
      }

      @Test
      void shouldReturnEmptyStringWhenOnlyOneAddressLine() {
        when(addressTypeConverter.convertV2(any())).thenReturn(List.of("12345 City"));

        final var result = structuredAddressConverter.convertV2(new AddressType(), null);

        assertEquals("", result.address());
      }

      @Test
      void shouldFilterNullLinesFromStreet() {
        when(addressTypeConverter.convertV2(any()))
            .thenReturn(List.of("Street 1", null, "12345 City"));

        final var result = structuredAddressConverter.convertV2(new AddressType(), null);

        assertEquals("Street 1", result.address());
      }
    }

    @Nested
    class ZipCode {

      @ParameterizedTest
      @CsvSource({
        "54321, 54321",
        "'  54321  ', 54321",
      })
      void shouldReturnTrimmedPostalCodeParameterWhenProvided(String postalCode, String expected) {
        when(addressTypeConverter.convertV2(any())).thenReturn(Collections.emptyList());

        final var result = structuredAddressConverter.convertV2(new AddressType(), postalCode);

        assertEquals(expected, result.zipCode());
      }

      @ParameterizedTest
      @NullAndEmptySource
      void shouldExtractZipCodeFromLastLineWhenPostalCodeIs(String postalCode) {
        when(addressTypeConverter.convertV2(any())).thenReturn(List.of("Street 1", "12345 City"));

        final var result = structuredAddressConverter.convertV2(new AddressType(), postalCode);

        assertEquals("12345", result.zipCode());
      }

      @Test
      void shouldReturnNullZipCodeWhenLastLineDoesNotStartWithDigit() {
        when(addressTypeConverter.convertV2(any())).thenReturn(List.of("Street 1", "City"));

        final var result = structuredAddressConverter.convertV2(new AddressType(), null);

        assertNull(result.zipCode());
      }

      @Test
      void shouldReturnNullZipCodeWhenAddressLinesEmpty() {
        when(addressTypeConverter.convertV2(any())).thenReturn(Collections.emptyList());

        final var result = structuredAddressConverter.convertV2(new AddressType(), null);

        assertNull(result.zipCode());
      }
    }

    @Nested
    class City {

      @Test
      void shouldExtractCityFromLastLineWhenItStartsWithDigits() {
        when(addressTypeConverter.convertV2(any()))
            .thenReturn(List.of("Street 1", "12345 Stockholm"));

        final var result = structuredAddressConverter.convertV2(new AddressType(), null);

        assertEquals("Stockholm", result.city());
      }

      @Test
      void shouldReturnLastLineAsCityWhenItDoesNotStartWithDigit() {
        when(addressTypeConverter.convertV2(any())).thenReturn(List.of("Street 1", "Stockholm"));

        final var result = structuredAddressConverter.convertV2(new AddressType(), null);

        assertEquals("Stockholm", result.city());
      }

      @Test
      void shouldTrimCityValue() {
        when(addressTypeConverter.convertV2(any())).thenReturn(List.of("  Stockholm  "));

        final var result = structuredAddressConverter.convertV2(new AddressType(), null);

        assertEquals("Stockholm", result.city());
      }

      @Test
      void shouldReturnNullCityWhenAddressLinesEmpty() {
        when(addressTypeConverter.convertV2(any())).thenReturn(Collections.emptyList());

        final var result = structuredAddressConverter.convertV2(new AddressType(), null);

        assertNull(result.city());
      }
    }
  }

  @Nested
  class ConvertV3 {

    @Test
    void shouldThrowIllegalArgumentExceptionWhenUnitTypeIsNull() {
      assertThrows(
          IllegalArgumentException.class, () -> structuredAddressConverter.convertV3(null));
    }

    @Test
    void shouldExtractStreetFromAllLinesExceptLast() {
      final var unitType = new UnitType();
      when(addressTypeConverter.convertV3(any()))
          .thenReturn(List.of("Street 1", "Street 2", "12345 City"));

      final var result = structuredAddressConverter.convertV3(unitType);

      assertEquals("Street 1 Street 2", result.address());
    }

    @Test
    void shouldExtractZipCodeFromUnitPostalCode() {
      final var unitType = new UnitType();
      unitType.setPostalCode("12345");
      when(addressTypeConverter.convertV3(any())).thenReturn(List.of("Street 1", "67890 City"));

      final var result = structuredAddressConverter.convertV3(unitType);

      assertEquals("12345", result.zipCode());
    }

    @Test
    void shouldExtractZipCodeFromLastLineWhenUnitPostalCodeIsNull() {
      final var unitType = new UnitType();
      when(addressTypeConverter.convertV3(any())).thenReturn(List.of("Street 1", "12345 City"));

      final var result = structuredAddressConverter.convertV3(unitType);

      assertEquals("12345", result.zipCode());
    }

    @Test
    void shouldExtractCityFromLastLine() {
      final var unitType = new UnitType();
      when(addressTypeConverter.convertV3(any()))
          .thenReturn(List.of("Street 1", "12345 Stockholm"));

      final var result = structuredAddressConverter.convertV3(unitType);

      assertEquals("Stockholm", result.city());
    }
  }
}
