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
package se.inera.intyg.intygproxyservice.integration.fakehsa.converters;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import se.inera.intyg.intygproxyservice.integration.api.authorization.model.CredentialInformation;
import se.inera.intyg.intygproxyservice.integration.api.authorization.model.HsaSystemRole;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedCareProvider;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedCareUnit;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedCredentialInformation;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedHsaPerson;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedHsaPerson.ParsedPaTitle;

@Component
@RequiredArgsConstructor
public class CredentialInformationConverter {

  private final CommissonListConverter commissonListConverter;

  public CredentialInformation convert(
      ParsedCredentialInformation credentialInformation,
      ParsedHsaPerson hsaPerson,
      Map<String, ParsedCareProvider> careProviderMap,
      Map<String, ParsedCareUnit> careUnitMap) {
    return CredentialInformation.builder()
        .personHsaId(hsaPerson.getHsaId())
        .personalPrescriptionCode(hsaPerson.getPersonalPrescriptionCode())
        .paTitleCode(convertPaTitle(hsaPerson))
        .hsaSystemRole(convertSystemRole(hsaPerson))
        .commission(
            commissonListConverter.convert(careUnitMap, careProviderMap, credentialInformation))
        .build();
  }

  private static List<HsaSystemRole> convertSystemRole(ParsedHsaPerson hsaPerson) {
    return hsaPerson.getSystemRoles() != null
        ? hsaPerson.getSystemRoles().stream()
            .map(role -> HsaSystemRole.builder().role(role).build())
            .toList()
        : Collections.emptyList();
  }

  private static List<String> convertPaTitle(ParsedHsaPerson hsaPerson) {
    return hsaPerson.getPaTitle() != null
        ? hsaPerson.getPaTitle().stream().map(ParsedPaTitle::getTitleCode).toList()
        : Collections.emptyList();
  }
}
