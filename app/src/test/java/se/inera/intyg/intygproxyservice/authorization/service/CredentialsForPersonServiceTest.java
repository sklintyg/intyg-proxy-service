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
package se.inera.intyg.intygproxyservice.authorization.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.intygproxyservice.authorization.dto.CredentialsForPersonRequest;
import se.inera.intyg.intygproxyservice.integration.api.authorization.GetCredentialsForPersonIntegrationRequest;
import se.inera.intyg.intygproxyservice.integration.api.authorization.GetCredentialsForPersonIntegrationResponse;
import se.inera.intyg.intygproxyservice.integration.api.authorization.GetCredentialsForPersonIntegrationService;
import se.inera.intyg.intygproxyservice.integration.api.authorization.model.CredentialsForPerson;
import se.inera.intyg.intygproxyservice.logging.LogHashUtility;

@ExtendWith(MockitoExtension.class)
class CredentialsForPersonServiceTest {

  private static final String PERSON_ID = "PERSON_ID";

  private static final CredentialsForPersonRequest REQUEST =
      CredentialsForPersonRequest.builder().personId(PERSON_ID).build();

  private static final GetCredentialsForPersonIntegrationResponse RESPONSE =
      GetCredentialsForPersonIntegrationResponse.builder()
          .credentials(CredentialsForPerson.builder().build())
          .build();

  @Mock private GetCredentialsForPersonIntegrationService getCredentialsForPersonIntegrationService;
  @Mock private LogHashUtility logHashUtility;

  @InjectMocks private CredentialsForPersonService credentialsForPersonService;

  @Test
  void shouldThrowIllegalArgumentExceptionIfRequestIsNull() {
    assertThrows(IllegalArgumentException.class, () -> credentialsForPersonService.get(null));
  }

  @Test
  void shouldThrowIllegalArgumentExceptionIfPersonIdIsNull() {
    final var request = CredentialsForPersonRequest.builder().build();
    assertThrows(IllegalArgumentException.class, () -> credentialsForPersonService.get(request));
  }

  @Test
  void shouldThrowIllegalArgumentExceptionIfpersonIdIsEmpty() {
    final var request = CredentialsForPersonRequest.builder().personId("").build();
    assertThrows(IllegalArgumentException.class, () -> credentialsForPersonService.get(request));
  }

  @Test
  void shouldThrowIllegalArgumentExceptionIfpersonIdIsBlank() {
    final var request = CredentialsForPersonRequest.builder().personId("   ").build();
    assertThrows(IllegalArgumentException.class, () -> credentialsForPersonService.get(request));
  }

  @Nested
  class ValidRequest {

    @BeforeEach
    void setUp() {
      when(getCredentialsForPersonIntegrationService.get(
              any(GetCredentialsForPersonIntegrationRequest.class)))
          .thenReturn(RESPONSE);
    }

    @Test
    void shallReturnCredentialInformation() {
      final var response = credentialsForPersonService.get(REQUEST);

      assertEquals(RESPONSE.getCredentials(), response.getCredentials());
    }

    @Test
    void shallSetPersonIdInRequest() {
      credentialsForPersonService.get(REQUEST);

      final var captor = ArgumentCaptor.forClass(GetCredentialsForPersonIntegrationRequest.class);
      verify(getCredentialsForPersonIntegrationService).get(captor.capture());

      assertEquals(REQUEST.getPersonId(), captor.getValue().getPersonId());
    }
  }
}
