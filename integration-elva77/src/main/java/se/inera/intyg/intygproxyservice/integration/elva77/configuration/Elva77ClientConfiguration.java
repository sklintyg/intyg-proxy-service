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
package se.inera.intyg.intygproxyservice.integration.elva77.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import se.inera.intyg.intygproxyservice.integration.common.WebServiceClientFactory;
import se.mkv.itintegration.getuserprofile.v2.GetUserProfileResponderInterface;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class Elva77ClientConfiguration {

  private final WebServiceClientFactory webServiceClientFactory;

  @Value("${integration.elva77.getuserprofile.endpoint}")
  private String getUserProfileEndpoint;

  @Bean
  public GetUserProfileResponderInterface getUserProfileResponderInterface() {
    return webServiceClientFactory.create(
        GetUserProfileResponderInterface.class, getUserProfileEndpoint);
  }
}
