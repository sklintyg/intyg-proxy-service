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
package se.inera.intyg.intygproxyservice.integrationv2.organization.client.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.HealthCareProvider;
import se.riv.infrastructure.directory.organization.gethealthcareproviderresponder.v1.GetHealthCareProviderResponseType;
import se.riv.infrastructure.directory.organization.gethealthcareproviderresponder.v1.HealthCareProviderType;

@ExtendWith(MockitoExtension.class)
class GetHealthCareProviderResponseTypeConverterTest {

  @InjectMocks
  private GetHealthCareProviderResponseTypeConverter getHealthCareProviderResponseTypeConverter;

  @Mock private HealthCareProviderTypeConverter healthCareProviderTypeConverter;

  @Test
  void shouldReturnEmptyListIfResponseIsNull() {
    final var response = getHealthCareProviderResponseTypeConverter.convert(null);

    assertEquals(Collections.emptyList(), response);
  }

  @Test
  void shouldReturnEmptyListIfCareProviderIsNull() {
    final var type = new GetHealthCareProviderResponseType();

    final var response = getHealthCareProviderResponseTypeConverter.convert(type);

    assertEquals(Collections.emptyList(), response);
  }

  @Test
  void shouldReturnConvertedCareProviders() {
    final var expected = HealthCareProvider.builder().build();
    final var type = new GetHealthCareProviderResponseType();
    type.getHealthCareProvider().add(new HealthCareProviderType());
    type.getHealthCareProvider().add(new HealthCareProviderType());
    when(healthCareProviderTypeConverter.convertV1(any(HealthCareProviderType.class)))
        .thenReturn(expected);

    final var response = getHealthCareProviderResponseTypeConverter.convert(type);

    assertEquals(2, response.size());
    assertEquals(expected, response.get(0));
  }
}
