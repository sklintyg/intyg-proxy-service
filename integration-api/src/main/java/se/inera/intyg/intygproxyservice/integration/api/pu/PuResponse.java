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
package se.inera.intyg.intygproxyservice.integration.api.pu;

public record PuResponse(Person person, Status status) {

  public static PuResponse found(final Person person) {
    return new PuResponse(person, Status.FOUND);
  }

  public static PuResponse notFound() {
    return new PuResponse(null, Status.NOT_FOUND);
  }

  public static PuResponse notFound(String patientId) {
    return new PuResponse(
        Person.builder().personnummer(PersonId.of(patientId)).build(), Status.NOT_FOUND);
  }

  public static PuResponse error(String patientId) {
    return new PuResponse(
        Person.builder().personnummer(PersonId.of(patientId)).build(), Status.ERROR);
  }

  public static PuResponse error() {
    return new PuResponse(null, Status.ERROR);
  }
}
