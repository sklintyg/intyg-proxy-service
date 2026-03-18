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

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Repository;
import se.inera.intyg.intygproxyservice.integration.api.pu.Person;

@Repository
public class FakePuRepository {

  private final Map<String, Person> personMap = new HashMap<>();

  public void addPerson(Person person) {
    if (person == null
        || person.getPersonnummer() == null
        || person.getPersonnummer().id().isBlank()) {
      throw new IllegalArgumentException(String.format("Invalid person: '%s'", person));
    }

    if (personMap.containsKey(person.getPersonnummer().id())) {
      throw new IllegalArgumentException(
          String.format("Person with id '%s' already exists!", person.getPersonnummer()));
    }

    personMap.put(person.getPersonnummer().id(), person);
  }

  public Person getPerson(String personId) {
    if (personId == null || personId.isBlank()) {
      throw new IllegalArgumentException(String.format("Invalid personId: '%s'", personId));
    }

    return personMap.get(personId);
  }
}
