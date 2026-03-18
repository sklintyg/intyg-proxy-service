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
package se.inera.intyg.intygproxyservice.integration.pu.v5.client.converter;

import se.riv.strategicresourcemanagement.persons.person.v5.AddressInformationType;

public class ResidentialAddressTypeConverter {

  private ResidentialAddressTypeConverter() {
    throw new IllegalStateException("Utility class");
  }

  public static String postalAddress(AddressInformationType addressInformationType) {
    if (isResidentialAddressNull(addressInformationType)) {
      return null;
    }

    final var residentialAddress = addressInformationType.getResidentialAddress();
    final var addressBuilder = new StringBuilder();
    if (containsAddress(residentialAddress.getCareOf())) {
      addressBuilder.append(residentialAddress.getCareOf());
    }
    if (containsAddress(residentialAddress.getPostalAddress1())) {
      addDelimiterIfNeeded(addressBuilder);
      addressBuilder.append(residentialAddress.getPostalAddress1());
    }
    if (containsAddress(residentialAddress.getPostalAddress2())) {
      addDelimiterIfNeeded(addressBuilder);
      addressBuilder.append(residentialAddress.getPostalAddress2());
    }
    return addressBuilder.toString();
  }

  public static String postalCode(AddressInformationType addressInformationType) {
    if (isResidentialAddressNull(addressInformationType)) {
      return null;
    }

    final var residentialAddress = addressInformationType.getResidentialAddress();
    if (residentialAddress.getPostalCode() == null || residentialAddress.getPostalCode() == 0) {
      return null;
    }
    return Integer.toString(residentialAddress.getPostalCode());
  }

  public static String city(AddressInformationType addressInformationType) {
    if (isResidentialAddressNull(addressInformationType)) {
      return null;
    }

    return addressInformationType.getResidentialAddress().getCity();
  }

  private static boolean containsAddress(String addressString) {
    return addressString != null;
  }

  private static void addDelimiterIfNeeded(StringBuilder addressBuilder) {
    if (!addressBuilder.isEmpty()) {
      addressBuilder.append(", ");
    }
  }

  private static boolean isResidentialAddressNull(AddressInformationType addressInformationType) {
    return addressInformationType == null || addressInformationType.getResidentialAddress() == null;
  }
}
