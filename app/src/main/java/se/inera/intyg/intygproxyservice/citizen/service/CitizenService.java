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
package se.inera.intyg.intygproxyservice.citizen.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.intygproxyservice.citizen.dto.CitizenRequest;
import se.inera.intyg.intygproxyservice.citizen.dto.CitizenResponse;
import se.inera.intyg.intygproxyservice.integration.api.elva77.Elva77Request;
import se.inera.intyg.intygproxyservice.integration.api.elva77.Elva77Service;

@Service
@RequiredArgsConstructor
public class CitizenService {

  private final Elva77Service elva77Service;
  private final Elva77ResponseConverter elva77ResponseConverter;

  public CitizenResponse findCitizen(CitizenRequest citizenRequest) {
    validateRequest(citizenRequest);

    final var elva77Response =
        elva77Service.findCitizen(
            Elva77Request.builder().personId(citizenRequest.getPersonId()).build());

    return elva77ResponseConverter.convert(elva77Response);
  }

  private void validateRequest(CitizenRequest citizenRequest) {
    if (citizenRequest == null) {
      throw new IllegalArgumentException("Invalid request, CitizenRequest is null");
    }

    if (citizenRequest.getPersonId() == null || citizenRequest.getPersonId().isBlank()) {
      throw new IllegalArgumentException("Invalid request, PersonId is missing");
    }
  }
}
