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
package se.inera.intyg.intygproxyservice.integration.common;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebServiceClientConfiguration {

  @Bean("ntjpWebServiceClientFactory")
  public WebServiceClientFactory ntjpWebServiceClientFactory(
      @Value("${integration.ntjp.client.keystore.type:PKCS12}") String keyStoreType,
      @Value("${integration.ntjp.client.keystore.password}") String keyStorePassword,
      @Value("${integration.ntjp.client.keystore.path}") String keyStorePath,
      @Value("${integration.ntjp.client.truststore.password}") String trustStorePassword,
      @Value("${integration.ntjp.client.truststore.path}") String trustStorePath,
      @Value("${integration.ntjp.client.tls.version}") String tlsVersion,
      @Value("${integration.ntjp.client.tls.cipherSuites}") List<String> tlsCipherSuites) {
    return new WebServiceClientFactory(
        keyStoreType,
        keyStorePassword,
        keyStorePath,
        trustStorePassword,
        trustStorePath,
        tlsVersion,
        tlsCipherSuites);
  }

  @Bean("elva77WebServiceClientFactory")
  public WebServiceClientFactory elva77WebServiceClientFactory(
      @Value("${integration.ntjp.client.keystore.type:PKCS12}") String keyStoreType,
      @Value("${integration.ntjp.client.keystore.password}") String keyStorePassword,
      @Value("${integration.ntjp.client.keystore.path}") String keyStorePath,
      @Value("${integration.ntjp.client.truststore.password}") String trustStorePassword,
      @Value("${integration.ntjp.client.truststore.path}") String trustStorePath,
      @Value("${integration.elva77.client.tls.version}") String tlsVersion,
      @Value("${integration.elva77.client.tls.cipherSuites}") List<String> tlsCipherSuites) {
    return new WebServiceClientFactory(
        keyStoreType,
        keyStorePassword,
        keyStorePath,
        trustStorePassword,
        trustStorePath,
        tlsVersion,
        tlsCipherSuites);
  }
}
