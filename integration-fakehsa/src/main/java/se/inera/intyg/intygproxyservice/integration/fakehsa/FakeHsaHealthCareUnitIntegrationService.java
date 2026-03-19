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

import static se.inera.intyg.intygproxyservice.integration.api.constants.HsaConstants.FAKE_HSA_PROFILE;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import se.inera.intyg.intygproxyservice.integration.api.organization.GetHealthCareUnitIntegrationRequest;
import se.inera.intyg.intygproxyservice.integration.api.organization.GetHealthCareUnitIntegrationResponse;
import se.inera.intyg.intygproxyservice.integration.api.organization.GetHealthCareUnitIntegrationService;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.FakeHsaRepository;

@Service
@AllArgsConstructor
@Profile(FAKE_HSA_PROFILE)
public class FakeHsaHealthCareUnitIntegrationService
    implements GetHealthCareUnitIntegrationService {

  private final FakeHsaRepository fakeHsaRepository;

  @Override
  public GetHealthCareUnitIntegrationResponse get(GetHealthCareUnitIntegrationRequest request) {
    validateRequest(request);
    return GetHealthCareUnitIntegrationResponse.builder()
        .healthCareUnit(fakeHsaRepository.getHealthCareUnit(request.getHsaId()))
        .build();
  }

  private void validateRequest(GetHealthCareUnitIntegrationRequest request) {
    if (request.getHsaId() == null || request.getHsaId().isEmpty()) {
      throw new IllegalArgumentException(
          String.format("Request did not contain a valid hsaId: '%s'", request.getHsaId()));
    }
  }
}
