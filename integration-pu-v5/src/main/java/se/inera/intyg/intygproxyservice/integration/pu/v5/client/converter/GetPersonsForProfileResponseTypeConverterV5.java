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

import static se.inera.intyg.intygproxyservice.integration.pu.v5.client.converter.PersonalIdentityTypeConverter.personId;

import org.springframework.stereotype.Component;
import se.inera.intyg.intygproxyservice.integration.api.pu.Person;
import se.riv.strategicresourcemanagement.persons.person.v5.RequestedPersonRecordType;

@Component
public class GetPersonsForProfileResponseTypeConverterV5 {

  public Person convert(RequestedPersonRecordType requestedPersonRecordType) {
    final var personRecord = requestedPersonRecordType.getPersonRecord();
    return Person.builder()
        .personnummer(personId(personRecord.getPersonalIdentity()))
        .fornamn(NameTypeConverter.givenName(personRecord.getName()))
        .mellannamn(NameTypeConverter.middleName(personRecord.getName()))
        .efternamn(NameTypeConverter.surname(personRecord.getName()))
        .postadress(
            ResidentialAddressTypeConverter.postalAddress(personRecord.getAddressInformation()))
        .postnummer(
            ResidentialAddressTypeConverter.postalCode(personRecord.getAddressInformation()))
        .postort(ResidentialAddressTypeConverter.city(personRecord.getAddressInformation()))
        .testIndicator(personRecord.isTestIndicator())
        .sekretessmarkering(ProtectedPersonConverter.protectedPerson(personRecord))
        .avliden(DeregistrationTypeConverter.deceased(personRecord.getDeregistration()))
        .build();
  }
}
