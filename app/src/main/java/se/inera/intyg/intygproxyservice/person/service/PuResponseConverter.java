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

import static se.inera.intyg.intygproxyservice.integration.api.pu.Status.FOUND;

import se.inera.intyg.intygproxyservice.integration.api.pu.Person;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuResponse;
import se.inera.intyg.intygproxyservice.integration.api.pu.Status;
import se.inera.intyg.intygproxyservice.person.dto.PersonDTO;
import se.inera.intyg.intygproxyservice.person.dto.PersonResponse;
import se.inera.intyg.intygproxyservice.person.dto.StatusDTOType;

public class PuResponseConverter {

  private PuResponseConverter() {
    throw new IllegalStateException("Utility class");
  }

  public static PersonResponse convert(PersonDTOMapper mapper, PuResponse puResponse) {
    return PersonResponse.builder()
        .person(getPerson(puResponse.person(), puResponse.status(), mapper))
        .status(StatusDTOType.valueOf(puResponse.status().name()))
        .build();
  }

  private static PersonDTO getPerson(Person person, Status status, PersonDTOMapper mapper) {
    if (person == null) {
      return null;
    }

    return FOUND.equals(status)
        ? mapper.toDTO(person)
        : PersonDTO.builder().personnummer(person.getPersonnummer().id()).build();
  }
}
