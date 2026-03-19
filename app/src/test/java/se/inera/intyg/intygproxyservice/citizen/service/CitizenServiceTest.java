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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.intygproxyservice.citizen.dto.CitizenDTO;
import se.inera.intyg.intygproxyservice.citizen.dto.CitizenRequest;
import se.inera.intyg.intygproxyservice.citizen.dto.CitizenResponse;
import se.inera.intyg.intygproxyservice.integration.api.elva77.Citizen;
import se.inera.intyg.intygproxyservice.integration.api.elva77.Elva77Request;
import se.inera.intyg.intygproxyservice.integration.api.elva77.Elva77Response;
import se.inera.intyg.intygproxyservice.integration.api.elva77.Elva77Service;
import se.inera.intyg.intygproxyservice.integration.api.elva77.Result;
import se.inera.intyg.intygproxyservice.integration.api.pu.Status;

@ExtendWith(MockitoExtension.class)
class CitizenServiceTest {

  private static final String PERSON_ID = "personId";

  @Mock Elva77Service elva77Service;
  @Mock Elva77ResponseConverter elva77ResponseConverter;
  @InjectMocks CitizenService citizenService;

  @Nested
  class ValidateRequest {

    @Test
    void shouldThrowIfRequestIsNull() {
      final var illegalArgumentException =
          assertThrows(IllegalArgumentException.class, () -> citizenService.findCitizen(null));
      assertEquals(
          "Invalid request, CitizenRequest is null", illegalArgumentException.getMessage());
    }

    @Test
    void shouldThrowIfPersonIdInRequestIsNull() {
      final var request = CitizenRequest.builder().personId(null).build();

      final var illegalArgumentException =
          assertThrows(IllegalArgumentException.class, () -> citizenService.findCitizen(request));
      assertEquals("Invalid request, PersonId is missing", illegalArgumentException.getMessage());
    }

    @Test
    void shouldThrowIfPersonIdInRequestIsEmpty() {
      final var request = CitizenRequest.builder().personId("").build();

      final var illegalArgumentException =
          assertThrows(IllegalArgumentException.class, () -> citizenService.findCitizen(request));
      assertEquals("Invalid request, PersonId is missing", illegalArgumentException.getMessage());
    }
  }

  @Test
  void shouldReturnCitizenResponse() {
    final var userDTO = CitizenDTO.builder().build();
    final var expectedResponse =
        CitizenResponse.builder().citizen(userDTO).status(Status.FOUND).build();

    final var request = CitizenRequest.builder().personId(PERSON_ID).build();

    final var elva77Request = Elva77Request.builder().personId(PERSON_ID).build();

    final var citizen = Citizen.builder().build();
    final var elva77Response = Elva77Response.builder().citizen(citizen).result(Result.OK).build();

    when(elva77Service.findCitizen(elva77Request)).thenReturn(elva77Response);
    when(elva77ResponseConverter.convert(elva77Response)).thenReturn(expectedResponse);

    final var response = citizenService.findCitizen(request);
    assertEquals(expectedResponse, response);
  }
}
