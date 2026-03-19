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
import se.inera.intyg.intygproxyservice.integration.api.organization.GetHealthCareUnitIntegrationRequest;
import se.inera.intyg.intygproxyservice.integration.api.organization.GetHealthCareUnitIntegrationResponse;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.HealthCareUnit;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.FakeHsaRepository;

@ExtendWith(MockitoExtension.class)
class FakeHsaHealthCareUnitIntegrationServiceTest {

  @Mock private FakeHsaRepository fakeHsaRepository;
  @InjectMocks private FakeHsaHealthCareUnitIntegrationService healthCareUnitIntegrationService;

  private static final String HSA_ID = "hsaId";
  private static final String EMPTY = "";

  @Test
  void shouldReturnHealthCareUnitMembersResponse() {
    final var request = GetHealthCareUnitIntegrationRequest.builder().hsaId(HSA_ID).build();
    final var healthCareUnitMembers = HealthCareUnit.builder().build();
    final var expectedResponse =
        GetHealthCareUnitIntegrationResponse.builder()
            .healthCareUnit(healthCareUnitMembers)
            .build();

    when(fakeHsaRepository.getHealthCareUnit(request.getHsaId())).thenReturn(healthCareUnitMembers);

    final var result = healthCareUnitIntegrationService.get(request);
    assertEquals(expectedResponse, result);
  }

  @Test
  void shouldThrowIlligalArgumentExceptionIfHsaIdIsNotProvided() {
    final var request = GetHealthCareUnitIntegrationRequest.builder().build();
    assertThrows(
        IllegalArgumentException.class, () -> healthCareUnitIntegrationService.get(request));
  }

  @Test
  void shouldThrowIlligalArgumentExceptionIfHsaIdIsEmpty() {
    final var request = GetHealthCareUnitIntegrationRequest.builder().hsaId(EMPTY).build();
    assertThrows(
        IllegalArgumentException.class, () -> healthCareUnitIntegrationService.get(request));
  }
}
