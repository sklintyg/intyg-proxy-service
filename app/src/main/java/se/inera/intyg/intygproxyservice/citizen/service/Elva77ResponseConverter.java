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

import org.springframework.stereotype.Component;
import se.inera.intyg.intygproxyservice.citizen.dto.CitizenDTO;
import se.inera.intyg.intygproxyservice.citizen.dto.CitizenResponse;
import se.inera.intyg.intygproxyservice.integration.api.elva77.Citizen;
import se.inera.intyg.intygproxyservice.integration.api.elva77.Elva77Response;
import se.inera.intyg.intygproxyservice.integration.api.pu.Status;

@Component
public class Elva77ResponseConverter {

  public CitizenResponse convert(Elva77Response elva77Response) {
    return switch (elva77Response.getResult()) {
      case ERROR -> getCitizenResponseNotFound();
      case INFO -> getCitizenResponseInactive(elva77Response.getCitizen());
      case OK -> {
        final var citizen = elva77Response.getCitizen();
        yield CitizenResponse.builder()
            .citizen(
                CitizenDTO.builder()
                    .personnummer(citizen.getSubjectOfCareId())
                    .fornamn(citizen.getFirstname())
                    .efternamn(citizen.getLastname())
                    .postort(citizen.getCity())
                    .postadress(citizen.getStreetAddress())
                    .postnummer(citizen.getZip())
                    .isActive(citizen.getIsActive())
                    .build())
            .status(Status.FOUND)
            .build();
      }
    };
  }

  private CitizenResponse getCitizenResponseInactive(Citizen citizen) {
    return CitizenResponse.builder()
        .citizen(
            CitizenDTO.builder()
                .personnummer(citizen.getSubjectOfCareId())
                .isActive(citizen.getIsActive())
                .build())
        .status(Status.FOUND)
        .build();
  }

  private static CitizenResponse getCitizenResponseNotFound() {
    return CitizenResponse.builder().status(Status.NOT_FOUND).build();
  }
}
