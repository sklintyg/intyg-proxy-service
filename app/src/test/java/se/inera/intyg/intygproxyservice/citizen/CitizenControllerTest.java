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
package se.inera.intyg.intygproxyservice.citizen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.intygproxyservice.citizen.dto.CitizenDTO;
import se.inera.intyg.intygproxyservice.citizen.dto.CitizenRequest;
import se.inera.intyg.intygproxyservice.citizen.dto.CitizenResponse;
import se.inera.intyg.intygproxyservice.citizen.service.CitizenService;

@ExtendWith(MockitoExtension.class)
class CitizenControllerTest {

  private static final CitizenRequest REQUEST = CitizenRequest.builder().build();

  @Mock CitizenService citizenService;
  @InjectMocks CitizenController citizenController;

  @Test
  void shouldFindCitizen() {
    final var expectedResponse =
        CitizenResponse.builder().citizen(CitizenDTO.builder().build()).build();

    when(citizenService.findCitizen(REQUEST)).thenReturn(expectedResponse);

    final var response = citizenController.findCitizen(REQUEST);
    assertEquals(expectedResponse, response);
  }
}
