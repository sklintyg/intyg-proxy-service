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
package se.inera.intyg.intygproxyservice.integration.fakehsa.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.intygproxyservice.integration.fakehsa.converters.CareProviderConverter;
import se.inera.intyg.intygproxyservice.integration.fakehsa.converters.CredentialInformationConverter;
import se.inera.intyg.intygproxyservice.integration.fakehsa.converters.CredentialsForPersonConverter;
import se.inera.intyg.intygproxyservice.integration.fakehsa.converters.EmployeeConverter;
import se.inera.intyg.intygproxyservice.integration.fakehsa.converters.HealthCareUnitConverter;
import se.inera.intyg.intygproxyservice.integration.fakehsa.converters.HealthCareUnitMembersConverter;
import se.inera.intyg.intygproxyservice.integration.fakehsa.converters.UnitConverter;

class BootstrapHsaServiceTest {

  private FakeHsaRepository fakeHsaRepository;
  private EmployeeConverter employeeConverter;
  private HealthCareUnitMembersConverter healthCareUnitMembersConverter;
  private HealthCareUnitConverter healthCareUnitConverter;
  private BootstrapHsaService bootstrapHsaService;
  private UnitConverter unitConverter;
  private CredentialInformationConverter credentialInformationConverter;
  private CredentialsForPersonConverter credentialsForPersonConverter;
  private CareProviderConverter careProviderConverter;

  @BeforeEach
  void setUp() {
    employeeConverter = new EmployeeConverter();
    healthCareUnitConverter = new HealthCareUnitConverter();
    healthCareUnitMembersConverter = new HealthCareUnitMembersConverter();
    careProviderConverter = new CareProviderConverter();
    fakeHsaRepository =
        new FakeHsaRepository(
            employeeConverter,
            healthCareUnitMembersConverter,
            healthCareUnitConverter,
            unitConverter,
            credentialInformationConverter,
            careProviderConverter,
            credentialsForPersonConverter);
    bootstrapHsaService = new BootstrapHsaService(fakeHsaRepository, new ObjectMapper());
  }

  @Test
  void shallLoadEmployeeToRepository() throws IOException {
    bootstrapHsaService.bootstrapVardgivare();
    assertNotNull(fakeHsaRepository.getEmployee("TSTNMT2321000156-1079"));
  }

  @Test
  void shallLoadCareUnitToRepository() throws IOException {
    bootstrapHsaService.bootstrapVardgivare();
    assertNotNull(fakeHsaRepository.getHealthCareUnit("TSTNMT2321000156-ALVC"));
  }

  @Test
  void shallLoadSubCareUnitToRepository() throws IOException {
    bootstrapHsaService.bootstrapVardgivare();
    assertNotNull(fakeHsaRepository.getHealthCareUnit("TSTNMT2321000156-ALLM"));
  }
}
