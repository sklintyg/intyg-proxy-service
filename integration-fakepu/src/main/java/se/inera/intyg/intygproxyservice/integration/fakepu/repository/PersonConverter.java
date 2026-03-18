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
package se.inera.intyg.intygproxyservice.integration.fakepu.repository;

import se.inera.intyg.intygproxyservice.integration.api.pu.Person;
import se.inera.intyg.intygproxyservice.integration.api.pu.PersonId;
import se.inera.intyg.intygproxyservice.integration.fakepu.repository.model.ParsedAddress;
import se.inera.intyg.intygproxyservice.integration.fakepu.repository.model.ParsedAddressInformation;
import se.inera.intyg.intygproxyservice.integration.fakepu.repository.model.ParsedName;
import se.inera.intyg.intygproxyservice.integration.fakepu.repository.model.ParsedPerson;

public class PersonConverter {

  private PersonConverter() {
    throw new IllegalStateException("Utility class");
  }

  public static Person convert(ParsedPerson parsedPerson) {
    return Person.builder()
        .personnummer(PersonId.of(parsedPerson.getPersonalIdentity().getExtension()))
        .fornamn(name(parsedPerson.getName().getGivenName()))
        .mellannamn(name(parsedPerson.getName().getMiddleName()))
        .efternamn(name(parsedPerson.getName().getSurname()))
        .postadress(
            AddressConverter.convert(residentialAddress(parsedPerson.getAddressInformation())))
        .postnummer(residentialAddress(parsedPerson.getAddressInformation()).getPostalCode())
        .postort(residentialAddress(parsedPerson.getAddressInformation()).getCity())
        .avliden(DeceasedConverter.convert(parsedPerson.getDeregistration()))
        .sekretessmarkering(
            ProtectedConverter.convert(
                parsedPerson.isProtectedPersonIndicator(),
                parsedPerson.isProtectedPopulationRecord()))
        .testIndicator(parsedPerson.isTestIndicator())
        .isActive(parsedPerson.getIsActive() != null && parsedPerson.getIsActive())
        .build();
  }

  private static ParsedAddress residentialAddress(ParsedAddressInformation addressInformation) {
    return addressInformation != null && addressInformation.getResidentialAddress() != null
        ? addressInformation.getResidentialAddress()
        : ParsedAddress.builder().build();
  }

  private static String name(ParsedName parsedName) {
    return parsedName != null ? parsedName.getName() : null;
  }
}
