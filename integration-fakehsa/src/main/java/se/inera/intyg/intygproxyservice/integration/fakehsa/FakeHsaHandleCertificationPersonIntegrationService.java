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

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import se.inera.intyg.intygproxyservice.integration.api.authorization.HandleCertificationPersonIntegrationRequest;
import se.inera.intyg.intygproxyservice.integration.api.authorization.HandleCertificationPersonIntegrationResponse;
import se.inera.intyg.intygproxyservice.integration.api.authorization.HandleCertificationPersonIntegrationService;
import se.inera.intyg.intygproxyservice.integration.api.authorization.model.Result;

@Service
@Profile(FAKE_HSA_PROFILE)
@RequiredArgsConstructor
public class FakeHsaHandleCertificationPersonIntegrationService
    implements HandleCertificationPersonIntegrationService {

  @Override
  public HandleCertificationPersonIntegrationResponse get(
      HandleCertificationPersonIntegrationRequest request) {
    return HandleCertificationPersonIntegrationResponse.builder()
        .result(Result.builder().resultCode("OK").resultText("OK").build())
        .build();
  }
}
