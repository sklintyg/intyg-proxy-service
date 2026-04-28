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

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import riv.infrastructure.directory.organization._5.StructuredPostalAddressType;
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
            .address(convertAddressLine(type.getStructuredPostalAddress()))
            .zipCode(type.getStructuredPostalAddress().getPostCode())
            .city(type.getStructuredPostalAddress().getTown())
            .build();
  }

  private String convertAddressLine(StructuredPostalAddressType structuredPostalAddress) {
    if (!StringUtils.hasText(structuredPostalAddress.getStreet())) {
      return null;
    }

    return Stream.of(
            structuredPostalAddress.getStreet(),
            structuredPostalAddress.getPremisesNumber(),
            structuredPostalAddress.getPremisesLetter())
        .filter(StringUtils::hasText)
        .collect(Collectors.joining(" "));
  }

  private Address convertAddress(UnitType type) {
    final var addressLines = addressTypeConverter.convertV5(type.getPostalAddress());
    return Address.builder()
        .address(convertPostalAddress(addressLines))
        .zipCode(convertZipCode(addressLines, type.getPostalCode()))
        .city(convertCity(addressLines))
        .build();
  }

  private static String convertPostalAddress(List<String> addressLines) {
    if (CollectionUtils.isEmpty(addressLines)) {
      return null;
    }

    final var streetLines = addressLines.subList(0, addressLines.size() - 1);
    final var street =
        streetLines.stream().filter(Objects::nonNull).collect(Collectors.joining(" "));

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
    return addressLines != null && !addressLines.isEmpty() ? addressLines.getLast() : null;
  }
}
