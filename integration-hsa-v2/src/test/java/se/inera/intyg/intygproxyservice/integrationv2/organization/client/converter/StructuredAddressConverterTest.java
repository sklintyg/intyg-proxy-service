package se.inera.intyg.intygproxyservice.integrationv2.organization.client.converter;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import riv.infrastructure.directory.organization._5.AddressType;
import riv.infrastructure.directory.organization._5.StructuredPostalAddressType;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.Address;
import se.inera.intyg.intygproxyservice.integrationv2.organization.client.factory.AddressFactory;
import se.riv.infrastructure.directory.organization.getunitresponder.v5.UnitType;

@ExtendWith(MockitoExtension.class)
class StructuredAddressConverterTest {

  @Mock private AddressTypeConverter addressTypeConverter;
  @Mock private AddressFactory addressFactory;
  @InjectMocks private StructuredAddressConverter addressConverter;

  @Test
  void shouldConvertAddressWhenStructuredPostalAddressExists() {
    final var type = mock(UnitType.class);
    final var structuredPostalAddress = createStructuredPostalAddressType();
    final var expected = Address.builder().street("Test Street").build();
    when(type.getStructuredPostalAddress()).thenReturn(structuredPostalAddress);
    when(addressFactory.create(structuredPostalAddress)).thenReturn(expected);

    final var result = addressConverter.convert(type);

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

    final var result = addressConverter.convert(type);

    assertAll(
        () -> assertEquals("Test Street 1", result.street()),
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

    final var result = addressConverter.convert(type);

    assertEquals("54321", result.zipCode());
  }

  @Test
  void shouldHandleMissingAddressLines() {
    final var type = mock(UnitType.class);
    final var postalAddress = mock(AddressType.class);
    when(type.getStructuredPostalAddress()).thenReturn(null);
    when(type.getPostalAddress()).thenReturn(postalAddress);
    when(type.getPostalCode()).thenReturn(null);
    when(addressTypeConverter.convertV5(postalAddress)).thenReturn(List.of());

    final var result = addressConverter.convert(type);

    assertEquals("", result.street());
    assertNull(result.zipCode());
    assertNull(result.city());
  }

  @Test
  void shouldThrowExceptionWhenTypeIsNull() {
    assertThrows(IllegalArgumentException.class, () -> addressConverter.convert(null));
  }

  private static StructuredPostalAddressType createStructuredPostalAddressType() {
    final var structuredPostalAddressType = new StructuredPostalAddressType();
    structuredPostalAddressType.setStreet("Test Street");
    structuredPostalAddressType.setPremisesNumber("1");
    structuredPostalAddressType.setPremisesLetter("A");
    structuredPostalAddressType.setPostCode("12345");
    structuredPostalAddressType.setTown("Test town");
    return structuredPostalAddressType;
  }
}
