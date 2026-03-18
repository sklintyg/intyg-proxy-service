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
package se.inera.intyg.intygproxyservice.integration.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import se.inera.intyg.intygproxyservice.integration.common.WebServiceClientFactory;
import se.riv.infrastructure.directory.authorizationmanagement.getcredentialsforpersonincludingprotectedperson.v2.rivtabp21.GetCredentialsForPersonIncludingProtectedPersonResponderInterface;
import se.riv.infrastructure.directory.authorizationmanagement.gethospcredentialsforperson.v1.rivtabp21.GetHospCredentialsForPersonResponderInterface;
import se.riv.infrastructure.directory.authorizationmanagement.gethosplastupdate.v1.rivtabp21.GetHospLastUpdateResponderInterface;
import se.riv.infrastructure.directory.authorizationmanagement.handlehospcertificationperson.v1.rivtabp21.HandleHospCertificationPersonResponderInterface;
import se.riv.infrastructure.directory.employee.getemployeeincludingprotectedperson.v3.rivtabp21.GetEmployeeIncludingProtectedPersonResponderInterface;
import se.riv.infrastructure.directory.organization.gethealthcareprovider.v1.rivtabp21.GetHealthCareProviderResponderInterface;
import se.riv.infrastructure.directory.organization.gethealthcareunit.v2.rivtabp21.GetHealthCareUnitResponderInterface;
import se.riv.infrastructure.directory.organization.gethealthcareunitmembers.v2.rivtabp21.GetHealthCareUnitMembersResponderInterface;
import se.riv.infrastructure.directory.organization.getunit.v4.rivtabp21.GetUnitResponderInterface;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class HsaClientConfiguration {

  private final WebServiceClientFactory webServiceClientFactory;

  @Value("${integration.hsa.getemployeeincludingprotectedperson.endpoint}")
  private String getEmployeeIncludingProtectedPersonEndpoint;

  @Value("${integration.hsa.gethealthcareunit.endpoint}")
  private String getHealthCareUnitEndpoint;

  @Value("${integration.hsa.gethealthcareunitmembers.endpoint}")
  private String getHealthCareUnitMembersEndpoint;

  @Value("${integration.hsa.gethealthcareprovider.endpoint}")
  private String getHealthCareProviderEndpoint;

  @Value("${integration.hsa.getunit.endpoint}")
  private String getUnitEndpoint;

  @Value("${integration.hsa.getcredentialinformation.endpoint}")
  private String getCredentialInformationEndpoint;

  @Value("${integration.hsa.getlastupdate.endpoint}")
  private String getLastUpdateEndpoint;

  @Value("${integration.hsa.getcredentialsforperson.endpoint}")
  private String getCredentialsForPersonEndpoint;

  @Value("${integration.hsa.handlecertificationperson.endpoint}")
  private String handleCertificationPersonEndpoint;

  @Bean
  public GetCredentialsForPersonIncludingProtectedPersonResponderInterface
      getCredentialInformation() {
    return webServiceClientFactory.create(
        GetCredentialsForPersonIncludingProtectedPersonResponderInterface.class,
        getCredentialInformationEndpoint);
  }

  @Bean
  public GetHospLastUpdateResponderInterface getLastUpdate() {
    return webServiceClientFactory.create(
        GetHospLastUpdateResponderInterface.class, getLastUpdateEndpoint);
  }

  @Bean
  public GetEmployeeIncludingProtectedPersonResponderInterface
      getEmployeeIncludingProtectedPerson() {
    return webServiceClientFactory.create(
        GetEmployeeIncludingProtectedPersonResponderInterface.class,
        getEmployeeIncludingProtectedPersonEndpoint);
  }

  @Bean
  public GetHealthCareUnitResponderInterface getHealthCareUnitResponderInterface() {
    return webServiceClientFactory.create(
        GetHealthCareUnitResponderInterface.class, getHealthCareUnitEndpoint);
  }

  @Bean
  public GetHealthCareUnitMembersResponderInterface getHealthCareUnitMembersResponderInterface() {
    return webServiceClientFactory.create(
        GetHealthCareUnitMembersResponderInterface.class, getHealthCareUnitMembersEndpoint);
  }

  @Bean
  public GetHealthCareProviderResponderInterface getHealthCareProviderResponderInterface() {
    return webServiceClientFactory.create(
        GetHealthCareProviderResponderInterface.class, getHealthCareProviderEndpoint);
  }

  @Bean
  public GetUnitResponderInterface getUnitResponderInterface() {
    return webServiceClientFactory.create(GetUnitResponderInterface.class, getUnitEndpoint);
  }

  @Bean
  public GetHospCredentialsForPersonResponderInterface getCredentialsForPerson() {
    return webServiceClientFactory.create(
        GetHospCredentialsForPersonResponderInterface.class, getCredentialsForPersonEndpoint);
  }

  @Bean
  public HandleHospCertificationPersonResponderInterface getHandleCertificationPersonInterface() {
    return webServiceClientFactory.create(
        HandleHospCertificationPersonResponderInterface.class, handleCertificationPersonEndpoint);
  }
}
