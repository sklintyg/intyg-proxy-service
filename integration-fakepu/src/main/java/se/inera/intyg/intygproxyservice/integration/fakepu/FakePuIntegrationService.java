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
package se.inera.intyg.intygproxyservice.integration.fakepu;

import static se.inera.intyg.intygproxyservice.integration.api.constants.PuConstants.FAKE_PU_PROFILE;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import se.inera.intyg.intygproxyservice.integration.api.elva77.Citizen;
import se.inera.intyg.intygproxyservice.integration.api.elva77.Elva77Request;
import se.inera.intyg.intygproxyservice.integration.api.elva77.Elva77Response;
import se.inera.intyg.intygproxyservice.integration.api.elva77.Elva77Service;
import se.inera.intyg.intygproxyservice.integration.api.elva77.Result;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuPersonsRequest;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuPersonsResponse;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuRequest;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuResponse;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuService;
import se.inera.intyg.intygproxyservice.integration.api.pu.Status;
import se.inera.intyg.intygproxyservice.integration.fakepu.repository.FakePuRepository;

@Service
@AllArgsConstructor
@Primary
@Profile(FAKE_PU_PROFILE)
public class FakePuIntegrationService implements PuService, Elva77Service {

  private final FakePuRepository fakePuRepository;

  @Override
  public PuResponse findPerson(PuRequest puRequest) {
    if (puRequest == null) {
      throw new IllegalArgumentException("Cannot be null!");
    }

    return getPersonFromFakeRepository(puRequest.getPersonId());
  }

  @Override
  public PuPersonsResponse findPersons(PuPersonsRequest puRequest) {
    if (puRequest == null) {
      throw new IllegalArgumentException("Cannot be null!");
    }

    final var persons =
        puRequest.getPersonIds().stream().map(this::getPersonFromFakeRepository).toList();

    return PuPersonsResponse.builder().persons(persons).build();
  }

  @Override
  public Elva77Response findCitizen(Elva77Request request) {
    if (request == null) {
      throw new IllegalArgumentException("Cannot be null!");
    }

    final var puResponse = getPersonFromFakeRepository(request.getPersonId());
    if (!puResponse.status().equals(Status.FOUND)) {
      return Elva77Response.error();
    }

    final var person = puResponse.person();
    return Elva77Response.builder()
        .citizen(
            Citizen.builder()
                .subjectOfCareId(person.getPersonnummer().id())
                .firstname(person.getFornamn())
                .lastname(person.getEfternamn())
                .city(person.getPostort())
                .streetAddress(person.getPostadress())
                .zip(person.getPostnummer())
                .isActive(person.isActive())
                .build())
        .result(Result.OK)
        .build();
  }

  private PuResponse getPersonFromFakeRepository(String personId) {
    final var person = fakePuRepository.getPerson(personId);
    if (person == null) {
      return PuResponse.notFound();
    }

    return PuResponse.found(person);
  }
}
