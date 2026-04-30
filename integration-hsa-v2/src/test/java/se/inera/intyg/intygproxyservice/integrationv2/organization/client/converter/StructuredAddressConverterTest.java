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
package se.inera.intyg.intygproxyservice.integrationv2.organization.client.converter;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import riv.infrastructure.directory.organization._5.AddressType;
import riv.infrastructure.directory.organization._5.StructuredPostalAddressType;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.Address;
import se.riv.infrastructure.directory.organization.getunitresponder.v5.UnitType;

@ExtendWith(MockitoExtension.class)
class StructuredAddressConverterTest {

  @Mock private AddressTypeConverter addressTypeConverter;
  @InjectMocks private StructuredAddressConverter addressConverter;

  @Nested
  class ConvertV5 {

    @Test
    void shouldConvertAddressWhenStructuredPostalAddressExists() {
      final var type = mock(UnitType.class);
      final var structuredPostalAddress = createStructuredPostalAddressType();
      final var expected =
          Address.builder().address("Test Street 1 A").zipCode("12345").city("Test town").build();
      when(type.getStructuredPostalAddress()).thenReturn(structuredPostalAddress);

      final var result = addressConverter.convertV5(type);

      assertEquals(expected, result);
    }

    @Test
    void shouldConvertAddressWhenStructuredPostalAddressIsMissing() {
      final var type = mock(UnitType.class);
      final var postalAddress = mock(AddressType.class);
      when(type.getStructuredPostalAddress()).thenReturn(null);
      when(type.getPostalAddress()).thenReturn(postalAddress);
      when(type.getPostalCode()).thenReturn(null);
      when(addressTypeConverter.convertV5(postalAddress))
          .thenReturn(List.of("Test Street 1", "12345 Test town"));

      final var result = addressConverter.convertV5(type);

      assertAll(
          () -> assertEquals("Test Street 1", result.address()),
          () -> assertEquals("12345", result.zipCode()),
          () -> assertEquals("Test town", result.city()));
    }

    @Test
    void shouldUsePostalCodeWhenPresentAndTrimmed() {
      final var type = mock(UnitType.class);
      final var postalAddress = mock(AddressType.class);
      when(type.getStructuredPostalAddress()).thenReturn(null);
      when(type.getPostalAddress()).thenReturn(postalAddress);
      when(type.getPostalCode()).thenReturn(" 54321 ");
      when(addressTypeConverter.convertV5(postalAddress))
          .thenReturn(List.of("Test Street 1", "11111 Test town"));

      final var result = addressConverter.convertV5(type);

      assertEquals("54321", result.zipCode());
    }

    @Test
    void shouldIgnoreBlankPostalCodeAndExtractZipFromAddressLines() {
      final var type = mock(UnitType.class);
      final var postalAddress = mock(AddressType.class);
      when(type.getStructuredPostalAddress()).thenReturn(null);
      when(type.getPostalAddress()).thenReturn(postalAddress);
      when(type.getPostalCode()).thenReturn("   ");
      when(addressTypeConverter.convertV5(postalAddress))
          .thenReturn(List.of("Test Street 1", "12345 Test town"));

      final var result = addressConverter.convertV5(type);

      assertEquals("12345", result.zipCode());
    }

    @Test
    void shouldHandleMissingAddressLines() {
      final var type = mock(UnitType.class);
      final var postalAddress = mock(AddressType.class);
      when(type.getStructuredPostalAddress()).thenReturn(null);
      when(type.getPostalAddress()).thenReturn(postalAddress);
      when(type.getPostalCode()).thenReturn(null);
      when(addressTypeConverter.convertV5(postalAddress)).thenReturn(List.of());

      final var result = addressConverter.convertV5(type);

      assertNull(result.address());
      assertNull(result.zipCode());
      assertNull(result.city());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void shouldReturnNullAddressWhenStreetIsMissing(String street) {
      final var type = mock(UnitType.class);
      final var structuredAddress = createStructuredPostalAddressType();
      structuredAddress.setStreet(street);
      when(type.getStructuredPostalAddress()).thenReturn(structuredAddress);

      final var result = addressConverter.convertV5(type);

      assertNull(result.address());
    }

    @Test
    void shouldThrowExceptionWhenTypeIsNull() {
      assertThrows(IllegalArgumentException.class, () -> addressConverter.convertV5(null));
    }

    @Test
    void shouldExtractCityFromLastLineWhenNoZipPrefix() {
      final var type = mock(UnitType.class);
      final var postalAddress = mock(AddressType.class);
      when(type.getStructuredPostalAddress()).thenReturn(null);
      when(type.getPostalAddress()).thenReturn(postalAddress);
      when(type.getPostalCode()).thenReturn(null);
      when(addressTypeConverter.convertV5(postalAddress))
          .thenReturn(List.of("Test Street 1", "Test town"));

      final var result = addressConverter.convertV5(type);

      assertAll(
          () -> assertEquals("Test Street 1", result.address()),
          () -> assertNull(result.zipCode()),
          () -> assertEquals("Test town", result.city()));
    }

    @Test
    void shouldReturnEmptyAddressWhenOnlyLastLinePresent() {
      final var type = mock(UnitType.class);
      final var postalAddress = mock(AddressType.class);
      when(type.getStructuredPostalAddress()).thenReturn(null);
      when(type.getPostalAddress()).thenReturn(postalAddress);
      when(type.getPostalCode()).thenReturn(null);
      when(addressTypeConverter.convertV5(postalAddress)).thenReturn(List.of("12345 Test town"));

      final var result = addressConverter.convertV5(type);

      assertAll(
          () -> assertEquals("", result.address()),
          () -> assertEquals("12345", result.zipCode()),
          () -> assertEquals("Test town", result.city()));
    }

    @Test
    void shouldJoinMultipleStreetLines() {
      final var type = mock(UnitType.class);
      final var postalAddress = mock(AddressType.class);
      when(type.getStructuredPostalAddress()).thenReturn(null);
      when(type.getPostalAddress()).thenReturn(postalAddress);
      when(type.getPostalCode()).thenReturn(null);
      when(addressTypeConverter.convertV5(postalAddress))
          .thenReturn(List.of("Line 1", "Line 2", "12345 Test town"));

      final var result = addressConverter.convertV5(type);

      assertEquals("Line 1 Line 2", result.address());
    }

    @Test
    void shouldConvertStructuredAddressWithStreetOnly() {
      final var type = mock(UnitType.class);
      final var structuredAddress = new StructuredPostalAddressType();
      structuredAddress.setStreet("Only Street");
      structuredAddress.setPostCode("11111");
      structuredAddress.setTown("Only Town");
      when(type.getStructuredPostalAddress()).thenReturn(structuredAddress);

      final var result = addressConverter.convertV5(type);

      assertEquals("Only Street", result.address());
    }

    @Test
    void shouldConvertStructuredAddressWithStreetAndPremisesNumberOnly() {
      final var type = mock(UnitType.class);
      final var structuredAddress = new StructuredPostalAddressType();
      structuredAddress.setStreet("Test Street");
      structuredAddress.setPremisesNumber("5");
      structuredAddress.setPostCode("12345");
      structuredAddress.setTown("Test town");
      when(type.getStructuredPostalAddress()).thenReturn(structuredAddress);

      final var result = addressConverter.convertV5(type);

      assertEquals("Test Street 5", result.address());
    }

    private StructuredPostalAddressType createStructuredPostalAddressType() {
      final var type = new StructuredPostalAddressType();
      type.setStreet("Test Street");
      type.setPremisesNumber("1");
      type.setPremisesLetter("A");
      type.setPostCode("12345");
      type.setTown("Test town");
      return type;
    }
  }

  @Nested
  class ConvertV2 {

    @Test
    void shouldConvertAddressWhenStructuredPostalAddressExists() {
      final var structuredPostalAddress = createV2StructuredPostalAddressType();
      final var expected =
          Address.builder().address("Test Street 1 A").zipCode("12345").city("Test town").build();

      final var result = addressConverter.convertV2(null, null, structuredPostalAddress);

      assertEquals(expected, result);
    }

    @Test
    void shouldConvertAddressWhenStructuredPostalAddressIsMissing() {
      final var postalAddress =
          mock(se.riv.infrastructure.directory.organization.v2.AddressType.class);
      when(addressTypeConverter.convertV2(postalAddress))
          .thenReturn(List.of("Test Street 1", "12345 Test town"));

      final var result = addressConverter.convertV2(postalAddress, null, null);

      assertAll(
          () -> assertEquals("Test Street 1", result.address()),
          () -> assertEquals("12345", result.zipCode()),
          () -> assertEquals("Test town", result.city()));
    }

    @Test
    void shouldUsePostalCodeWhenPresentAndTrimmed() {
      final var postalAddress =
          mock(se.riv.infrastructure.directory.organization.v2.AddressType.class);
      when(addressTypeConverter.convertV2(postalAddress))
          .thenReturn(List.of("Test Street 1", "11111 Test town"));

      final var result = addressConverter.convertV2(postalAddress, " 54321 ", null);

      assertEquals("54321", result.zipCode());
    }

    @Test
    void shouldHandleMissingAddressLines() {
      final var postalAddress =
          mock(se.riv.infrastructure.directory.organization.v2.AddressType.class);
      when(addressTypeConverter.convertV2(postalAddress)).thenReturn(List.of());

      final var result = addressConverter.convertV2(postalAddress, null, null);

      assertNull(result.address());
      assertNull(result.zipCode());
      assertNull(result.city());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void shouldReturnNullAddressWhenStreetIsMissing(String street) {
      final var structuredAddress = createV2StructuredPostalAddressType();
      structuredAddress.setStreet(street);

      final var result = addressConverter.convertV2(null, null, structuredAddress);

      assertNull(result.address());
    }

    @Test
    void shouldExtractCityFromLastLineWhenNoZipPrefix() {
      final var postalAddress =
          mock(se.riv.infrastructure.directory.organization.v2.AddressType.class);
      when(addressTypeConverter.convertV2(postalAddress))
          .thenReturn(List.of("Test Street 1", "Test town"));

      final var result = addressConverter.convertV2(postalAddress, null, null);

      assertAll(
          () -> assertEquals("Test Street 1", result.address()),
          () -> assertNull(result.zipCode()),
          () -> assertEquals("Test town", result.city()));
    }

    @Test
    void shouldReturnEmptyAddressWhenOnlyLastLinePresent() {
      final var postalAddress =
          mock(se.riv.infrastructure.directory.organization.v2.AddressType.class);
      when(addressTypeConverter.convertV2(postalAddress)).thenReturn(List.of("12345 Test town"));

      final var result = addressConverter.convertV2(postalAddress, null, null);

      assertAll(
          () -> assertEquals("", result.address()),
          () -> assertEquals("12345", result.zipCode()),
          () -> assertEquals("Test town", result.city()));
    }

    @Test
    void shouldJoinMultipleStreetLines() {
      final var postalAddress =
          mock(se.riv.infrastructure.directory.organization.v2.AddressType.class);
      when(addressTypeConverter.convertV2(postalAddress))
          .thenReturn(List.of("Line 1", "Line 2", "12345 Test town"));

      final var result = addressConverter.convertV2(postalAddress, null, null);

      assertEquals("Line 1 Line 2", result.address());
    }

    @Test
    void shouldIgnoreBlankPostalCodeAndExtractZipFromAddressLines() {
      final var postalAddress =
          mock(se.riv.infrastructure.directory.organization.v2.AddressType.class);
      when(addressTypeConverter.convertV2(postalAddress))
          .thenReturn(List.of("Test Street 1", "12345 Test town"));

      final var result = addressConverter.convertV2(postalAddress, "   ", null);

      assertEquals("12345", result.zipCode());
    }

    @Test
    void shouldConvertStructuredAddressWithStreetOnly() {
      final var structuredAddress =
          new riv.infrastructure.directory.organization._2.StructuredPostalAddressType();
      structuredAddress.setStreet("Only Street");
      structuredAddress.setPostCode("11111");
      structuredAddress.setTown("Only Town");

      final var result = addressConverter.convertV2(null, null, structuredAddress);

      assertEquals("Only Street", result.address());
    }

    @Test
    void shouldConvertStructuredAddressWithStreetAndPremisesNumberOnly() {
      final var structuredAddress =
          new riv.infrastructure.directory.organization._2.StructuredPostalAddressType();
      structuredAddress.setStreet("Test Street");
      structuredAddress.setPremisesNumber("5");
      structuredAddress.setPostCode("12345");
      structuredAddress.setTown("Test town");

      final var result = addressConverter.convertV2(null, null, structuredAddress);

      assertEquals("Test Street 5", result.address());
    }

    private riv.infrastructure.directory.organization._2.StructuredPostalAddressType
        createV2StructuredPostalAddressType() {
      final var type =
          new riv.infrastructure.directory.organization._2.StructuredPostalAddressType();
      type.setStreet("Test Street");
      type.setPremisesNumber("1");
      type.setPremisesLetter("A");
      type.setPostCode("12345");
      type.setTown("Test town");
      return type;
    }
  }
}
