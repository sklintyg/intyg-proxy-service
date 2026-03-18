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

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.intygproxyservice.integration.api.authorization.GetCredentialInformationIntegrationRequest;
import se.inera.intyg.intygproxyservice.integration.api.authorization.model.CredentialInformation;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.FakeHsaRepository;

@ExtendWith(MockitoExtension.class)
class FakeHsaGetCredentialInformationIntegrationServiceTest {

  private static final String HSA_ID = "hsaId";
  @Mock private FakeHsaRepository fakeHsaRepository;

  @InjectMocks
  private FakeHsaGetCredentialInformationIntegrationService fakeHsaGetUnitIntegrationService;

  @Test
  void shouldThrowIfHsaIdIsNull() {
    final var request = GetCredentialInformationIntegrationRequest.builder().build();
    assertThrows(
        IllegalArgumentException.class, () -> fakeHsaGetUnitIntegrationService.get(request));
  }

  @Test
  void shouldThrowIfHsaIdIsEmpty() {
    final var request =
        GetCredentialInformationIntegrationRequest.builder().personHsaId("").build();
    assertThrows(
        IllegalArgumentException.class, () -> fakeHsaGetUnitIntegrationService.get(request));
  }

  @Test
  void shouldReturnCredentialInformationFromFakeHsaRepository() {
    final var request =
        GetCredentialInformationIntegrationRequest.builder().personHsaId(HSA_ID).build();

    final var expectedResult = List.of(CredentialInformation.builder().build());

    when(fakeHsaRepository.getCredentialInformation(request.getPersonHsaId()))
        .thenReturn(expectedResult);

    final var result = fakeHsaGetUnitIntegrationService.get(request);

    assertEquals(expectedResult, result.getCredentialInformation());
  }
}
