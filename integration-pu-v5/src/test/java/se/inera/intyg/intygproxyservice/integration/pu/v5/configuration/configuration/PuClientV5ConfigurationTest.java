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
package se.inera.intyg.intygproxyservice.integration.pu.v5.configuration.configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import se.inera.intyg.intygproxyservice.integration.common.WebServiceClientFactory;
import se.riv.strategicresourcemanagement.persons.person.getpersonsforprofile.v5.rivtabp21.GetPersonsForProfileResponderInterface;

@ExtendWith(MockitoExtension.class)
class PuClientV5ConfigurationTest {

  public static final String GET_PERSONS_FOR_PROFILE_ENDPOINT = "endpoint";
  @Mock private WebServiceClientFactory webServiceClientFactory;

  @InjectMocks private PuClientConfigurationV5 puClientConfigurationV5;

  @BeforeEach
  void setUp() {
    ReflectionTestUtils.setField(
        puClientConfigurationV5, "getPersonsForProfileEndpoint", GET_PERSONS_FOR_PROFILE_ENDPOINT);
  }

  @Test
  void shallReturnGetPersonsForProfileResponder() {
    final var expected = mock(GetPersonsForProfileResponderInterface.class);

    doReturn(expected)
        .when(webServiceClientFactory)
        .create(GetPersonsForProfileResponderInterface.class, GET_PERSONS_FOR_PROFILE_ENDPOINT);

    final var actual = puClientConfigurationV5.getPersonsForProfileResponder();
    assertEquals(expected, actual);
  }
}
