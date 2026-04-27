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
package se.inera.intyg.intygproxyservice.integrationv2.authorization;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.intygproxyservice.integration.api.authorization.HandleCertificationPersonIntegrationRequest;
import se.inera.intyg.intygproxyservice.integration.api.authorization.model.Result;
import se.inera.intyg.intygproxyservice.integrationv2.authorization.client.HsaAuthorizationClient;

@ExtendWith(MockitoExtension.class)
class HsaHandleCertificationPersonIntegrationServiceTest {

  @Mock HsaAuthorizationClient hsaAuthorizationClient;

  @InjectMocks
  HsaHandleCertificationPersonIntegrationService hsaHandleCertificationPersonIntegrationService;

  @Test
  void shouldReturnResponseFromClient() {
    final var expected = Result.builder().build();
    when(hsaAuthorizationClient.handleCertificationPerson(
            any(HandleCertificationPersonIntegrationRequest.class)))
        .thenReturn(expected);

    final var response =
        hsaHandleCertificationPersonIntegrationService.get(
            HandleCertificationPersonIntegrationRequest.builder().build());

    assertEquals(expected, response.getResult());
  }
}
