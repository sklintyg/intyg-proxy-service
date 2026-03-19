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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static se.inera.intyg.intygproxyservice.integration.fakepu.TestData.PERSON_ID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.intygproxyservice.integration.api.pu.Person;
import se.inera.intyg.intygproxyservice.integration.api.pu.PersonId;

class FakePuRepositoryTest {

  private FakePuRepository fakePuRepository;

  @BeforeEach
  void setUp() {
    fakePuRepository = new FakePuRepository();
  }

  @Test
  void shallAddPersonToRepository() {
    final var expectedPerson = Person.builder().personnummer(PersonId.of(PERSON_ID)).build();
    fakePuRepository.addPerson(expectedPerson);

    assertEquals(expectedPerson, fakePuRepository.getPerson(PERSON_ID));
  }

  @Test
  void shallThrowExceptionWhenAddingNullPerson() {
    assertThrows(IllegalArgumentException.class, () -> fakePuRepository.addPerson(null));
  }

  @Test
  void shallThrowExceptionWhenAddingPersonWithNullPersonnummer() {
    final var person = Person.builder().build();

    assertThrows(IllegalArgumentException.class, () -> fakePuRepository.addPerson(person));
  }

  @Test
  void shallThrowExceptionWhenAddingPersonWithEmptyPersonnummer() {
    final var person = Person.builder().personnummer(PersonId.of("")).build();

    assertThrows(IllegalArgumentException.class, () -> fakePuRepository.addPerson(person));
  }

  @Test
  void shallThrowExceptionWhenAddingPersonWithExistingPersonnummer() {
    final var person = Person.builder().personnummer(PersonId.of(PERSON_ID)).build();

    fakePuRepository.addPerson(person);

    assertThrows(IllegalArgumentException.class, () -> fakePuRepository.addPerson(person));
  }

  @Test
  void shallTrowExceptionIfPassingNullPersonId() {
    assertThrows(IllegalArgumentException.class, () -> fakePuRepository.getPerson(null));
  }

  @Test
  void shallTrowExceptionIfPassingEmptyPersonId() {
    assertThrows(IllegalArgumentException.class, () -> fakePuRepository.getPerson(""));
  }
}
