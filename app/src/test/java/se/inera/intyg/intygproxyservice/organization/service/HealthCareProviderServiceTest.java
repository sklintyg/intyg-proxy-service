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
package se.inera.intyg.intygproxyservice.organization.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.intygproxyservice.integration.api.organization.GetHealthCareProviderIntegrationRequest;
import se.inera.intyg.intygproxyservice.integration.api.organization.GetHealthCareProviderIntegrationResponse;
import se.inera.intyg.intygproxyservice.integration.api.organization.GetHealthCareProviderIntegrationService;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.HealthCareProvider;
import se.inera.intyg.intygproxyservice.organization.dto.HealthCareProviderRequest;

@ExtendWith(MockitoExtension.class)
class HealthCareProviderServiceTest {

  private static final String HSA_ID = "HSA_ID";
  private static final String ORG_NO = "ORG_NO";

  private static final HealthCareProviderRequest REQUEST =
      HealthCareProviderRequest.builder().hsaId(HSA_ID).build();

  private static final GetHealthCareProviderIntegrationResponse RESPONSE =
      GetHealthCareProviderIntegrationResponse.builder()
          .healthCareProviders(List.of(HealthCareProvider.builder().build()))
          .build();

  @Mock private GetHealthCareProviderIntegrationService getHealthCareProviderIntegrationService;

  @InjectMocks private HealthCareProviderService healthCareProviderService;

  @Test
  void shouldThrowIllegalArgumentExceptionIfRequestIsNull() {
    assertThrows(IllegalArgumentException.class, () -> healthCareProviderService.get(null));
  }

  @Test
  void shouldThrowIllegalArgumentExceptionIfBothHsaIdAndOrgNoAreNull() {
    final var request = HealthCareProviderRequest.builder().build();

    assertThrows(IllegalArgumentException.class, () -> healthCareProviderService.get(request));
  }

  @Test
  void shouldThrowIllegalArgumentExceptionIfBothHsaIdAndOrgNoAreEmpty() {
    final var request =
        HealthCareProviderRequest.builder().hsaId("").organizationNumber("").build();

    assertThrows(IllegalArgumentException.class, () -> healthCareProviderService.get(request));
  }

  @Test
  void shouldThrowIllegalArgumentExceptionIfBothHsaIdAndOrgNoAreBlank() {
    final var request =
        HealthCareProviderRequest.builder().hsaId(" ").organizationNumber(" ").build();

    assertThrows(IllegalArgumentException.class, () -> healthCareProviderService.get(request));
  }

  @Test
  void shouldThrowIllegalArgumentExceptionIfBothHsaIdAndOrgNoAreDefined() {
    final var request =
        HealthCareProviderRequest.builder().hsaId(HSA_ID).organizationNumber(ORG_NO).build();

    assertThrows(IllegalArgumentException.class, () -> healthCareProviderService.get(request));
  }

  @Nested
  class ValidRequest {

    @BeforeEach
    void setUp() {
      when(getHealthCareProviderIntegrationService.get(
              any(GetHealthCareProviderIntegrationRequest.class)))
          .thenReturn(RESPONSE);
    }

    @Test
    void shouldNotThrowIllegalArgumentExceptionIfOnlyHsaIdIsNull() {
      final var request = HealthCareProviderRequest.builder().organizationNumber(ORG_NO).build();

      assertDoesNotThrow(() -> healthCareProviderService.get(request));
    }

    @Test
    void shouldNotThrowIllegalArgumentExceptionIfOnlyHsaIdIsEmpty() {
      final var request =
          HealthCareProviderRequest.builder().hsaId("").organizationNumber(ORG_NO).build();

      assertDoesNotThrow(() -> healthCareProviderService.get(request));
    }

    @Test
    void shouldNotThrowIllegalArgumentExceptionIfOnlyOrgNoIdIsBlank() {
      final var request =
          HealthCareProviderRequest.builder().hsaId(" ").organizationNumber(ORG_NO).build();

      assertDoesNotThrow(() -> healthCareProviderService.get(request));
    }

    @Test
    void shouldNotThrowIllegalArgumentExceptionIfOnlyOrgNoIsNull() {
      final var request = HealthCareProviderRequest.builder().hsaId(HSA_ID).build();

      assertDoesNotThrow(() -> healthCareProviderService.get(request));
    }

    @Test
    void shouldNotThrowIllegalArgumentExceptionIfOnlyOrgNoIsEmpty() {
      final var request =
          HealthCareProviderRequest.builder().organizationNumber("").hsaId(HSA_ID).build();

      assertDoesNotThrow(() -> healthCareProviderService.get(request));
    }

    @Test
    void shouldNotThrowIllegalArgumentExceptionIfOnlyOrgNoIsBlank() {
      final var request =
          HealthCareProviderRequest.builder().hsaId(HSA_ID).organizationNumber("  ").build();
      assertDoesNotThrow(() -> healthCareProviderService.get(request));
    }

    @Test
    void shallReturnHealthCareProviders() {
      final var response = healthCareProviderService.get(REQUEST);

      assertEquals(RESPONSE.getHealthCareProviders(), response.getHealthCareProviders());
    }

    @Test
    void shallSetHsaIdInRequest() {
      healthCareProviderService.get(REQUEST);

      final var captor = ArgumentCaptor.forClass(GetHealthCareProviderIntegrationRequest.class);
      verify(getHealthCareProviderIntegrationService).get(captor.capture());

      assertEquals(REQUEST.getHsaId(), captor.getValue().getHsaId());
    }

    @Test
    void shallSetOrgNoInRequest() {
      healthCareProviderService.get(
          HealthCareProviderRequest.builder().organizationNumber(ORG_NO).build());

      final var captor = ArgumentCaptor.forClass(GetHealthCareProviderIntegrationRequest.class);
      verify(getHealthCareProviderIntegrationService).get(captor.capture());

      assertEquals(ORG_NO, captor.getValue().getOrganizationNumber());
    }
  }
}
