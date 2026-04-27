package se.inera.intyg.intygproxyservice.integrationv2.organization.client.converter;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.Address;
import se.riv.infrastructure.directory.organization.getunitresponder.v5.UnitType;

@Component
@RequiredArgsConstructor
public class StructuredAddressConverter {

  private static final int CITY_START_INDEX = 6;
  private final AddressTypeConverter addressTypeConverter;

  public Address convert(UnitType type) {
    if (type == null) {
      throw new IllegalArgumentException("Unit type is null");
    }

    return type.getStructuredPostalAddress() == null
        ? convertAddress(type)
        : Address.builder()
            .street(type.getStructuredPostalAddress().getStreet())
            .streetNumber(type.getStructuredPostalAddress().getPremisesNumber())
            .streetLetter(type.getStructuredPostalAddress().getPremisesLetter())
            .zipCode(type.getStructuredPostalAddress().getPostCode())
            .city(type.getStructuredPostalAddress().getTown())
            .build();
  }

  private Address convertAddress(UnitType type) {
    final var addressLines = addressTypeConverter.convertV5(type.getPostalAddress());
    return Address.builder()
        .street(convertStreet(addressLines))
        .zipCode(convertZipCode(addressLines, type.getPostalCode()))
        .city(convertCity(addressLines))
        .build();
  }

  private static String convertStreet(List<String> addressLines) {
    if (addressLines == null) {
      return null;
    }

    if (addressLines.isEmpty()) {
      return "";
    }

    final var streetLines = addressLines.subList(0, addressLines.size() - 1);
    final var street = streetLines.stream()
        .filter(Objects::nonNull)
        .collect(Collectors.joining(" "));

    return street.isEmpty() ? "" : street;
  }

  private static String convertZipCode(List<String> addressLines, String postalCode) {
    if (postalCode != null && !postalCode.trim().isEmpty()) {
      return postalCode.trim();
    }

    final var lastLine = getLastLine(addressLines);
    if (hasZipAndCity(lastLine)) {
      return lastLine.substring(0, CITY_START_INDEX).trim();
    }
    return null;
  }

  private static String convertCity(List<String> addressLines) {
    final var lastLine = getLastLine(addressLines);
    if (hasZipAndCity(lastLine)) {
      return lastLine.substring(CITY_START_INDEX).trim();
    }
    return lastLine != null ? lastLine.trim() : null;
  }

  private static boolean hasZipAndCity(String lastLine) {
    return lastLine != null
        && lastLine.length() > CITY_START_INDEX + 1
        && Character.isDigit(lastLine.charAt(0));
  }

  private static String getLastLine(List<String> addressLines) {
    return addressLines != null && !addressLines.isEmpty()
        ? addressLines.getLast()
        : null;
  }
}
