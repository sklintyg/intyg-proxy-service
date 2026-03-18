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
package se.inera.intyg.intygproxyservice.integration.fakehsa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.intygproxyservice.integration.api.authorization.GetCredentialsForPersonIntegrationRequest;
import se.inera.intyg.intygproxyservice.integration.api.authorization.GetCredentialsForPersonIntegrationResponse;
import se.inera.intyg.intygproxyservice.integration.api.authorization.model.CredentialsForPerson;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.FakeHsaRepository;

@ExtendWith(MockitoExtension.class)
class FakeHsaGetCredentialsForPersonIntegrationServiceTest {

  private static final String PERSON_ID = "personId";
  @Mock private FakeHsaRepository fakeHsaRepository;

  @InjectMocks
  private FakeHsaGetCredentialsForPersonIntegrationService credentialsForPersonIntegrationService;

  @Test
  void shouldThrowIfParameterPersonIdIsMissing() {
    final var request = GetCredentialsForPersonIntegrationRequest.builder().build();
    assertThrows(
        IllegalArgumentException.class, () -> credentialsForPersonIntegrationService.get(request));
  }

  @Test
  void shouldThrowIfParameterPersonIdIsEmpty() {
    final var request = GetCredentialsForPersonIntegrationRequest.builder().personId("").build();
    assertThrows(
        IllegalArgumentException.class, () -> credentialsForPersonIntegrationService.get(request));
  }

  @Test
  void shouldReturnResponseWithCredentialsForPerson() {
    final var credentialsForPerson = CredentialsForPerson.builder().build();
    when(fakeHsaRepository.getCredentialsForPerson(PERSON_ID)).thenReturn(credentialsForPerson);

    final var expectedResult =
        GetCredentialsForPersonIntegrationResponse.builder()
            .credentials(credentialsForPerson)
            .build();

    final var request =
        GetCredentialsForPersonIntegrationRequest.builder().personId(PERSON_ID).build();

    final var result = credentialsForPersonIntegrationService.get(request);

    assertEquals(expectedResult, result);
  }
}
