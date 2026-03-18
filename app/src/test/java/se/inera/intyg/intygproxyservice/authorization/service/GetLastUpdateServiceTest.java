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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.intygproxyservice.integration.api.authorization.GetLastUpdateIntegrationResponse;
import se.inera.intyg.intygproxyservice.integration.api.authorization.GetLastUpdateIntegrationService;

@ExtendWith(MockitoExtension.class)
class GetLastUpdateServiceTest {

  private static final LocalDateTime EXPECTED = LocalDateTime.now();

  @Mock GetLastUpdateIntegrationService getLastUpdateIntegrationService;

  @InjectMocks GetLastUpdateService getLastUpdateService;

  @Test
  void shouldReturnValueFromIntegrationService() {
    when(getLastUpdateIntegrationService.get())
        .thenReturn(GetLastUpdateIntegrationResponse.builder().lastUpdate(EXPECTED).build());

    final var response = getLastUpdateService.get();

    assertEquals(EXPECTED, response.getLastUpdate());
  }

  @Test
  void shouldReturnNullIfIntegrationServiceReturnsNull() {
    final var response = getLastUpdateService.get();

    assertNull(response.getLastUpdate());
  }
}
