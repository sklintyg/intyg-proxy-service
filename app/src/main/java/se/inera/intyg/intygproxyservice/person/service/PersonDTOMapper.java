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
package se.inera.intyg.intygproxyservice.person.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import se.inera.intyg.intygproxyservice.integration.api.pu.Person;
import se.inera.intyg.intygproxyservice.person.dto.PersonDTO;

@Component
public class PersonDTOMapper {

  @Value("${putjanst.testindicated.reclassify.active.except.ssn:}")
  private List<String> testIndicatedPersonIds;

  public PersonDTO toDTO(Person person) {
    if (person == null) {
      return null;
    }

    return PersonDTO.builder()
        .personnummer(person.getPersonnummer().id())
        .sekretessmarkering(person.isSekretessmarkering())
        .avliden(person.isAvliden())
        .fornamn(person.getFornamn())
        .mellannamn(person.getMellannamn())
        .efternamn(person.getEfternamn())
        .postadress(person.getPostadress())
        .postnummer(person.getPostnummer())
        .postort(person.getPostort())
        .testIndicator(
            testIndicatedPersonIds != null && !testIndicatedPersonIds.isEmpty()
                ? testIndicatedPersonIds.contains(person.getPersonnummer().id())
                : person.isTestIndicator())
        .build();
  }
}
