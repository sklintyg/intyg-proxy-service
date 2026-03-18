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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import org.springframework.stereotype.Component;
import se.inera.intyg.intygproxyservice.integration.api.authorization.model.Commission;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedCareProvider;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedCareUnit;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedCredentialInformation;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedCredentialInformation.ParsedCommission;

@Component
public class CommissonListConverter {

  public List<Commission> convert(
      Map<String, ParsedCareUnit> careUnitMap,
      Map<String, ParsedCareProvider> careProviderMap,
      ParsedCredentialInformation credentialInformation) {
    if (credentialInformation.getCommissionList() == null) {
      return Collections.emptyList();
    }

    final var filteredCommissonPurpose =
        credentialInformation.getCommissionList().stream()
            .filter(isPresentInUnitMap(careUnitMap))
            .filter(hasCommissonPurpose())
            .toList();

    if (filteredCommissonPurpose.isEmpty()) {
      return Collections.emptyList();
    }

    final var commissionsList = new ArrayList<Commission>();

    for (ParsedCommission parsedCommission : credentialInformation.getCommissionList()) {
      final var parsedCareUnit = careUnitMap.get(parsedCommission.getHealthCareUnitHsaId());

      if (!careProviderMap.containsKey(parsedCareUnit.getCareProviderHsaId())) {
        continue;
      }

      final var parsedCareProvider = careProviderMap.get(parsedCareUnit.getCareProviderHsaId());

      parsedCommission
          .getCommissionPurpose()
          .forEach(
              purpose ->
                  commissionsList.add(
                      Commission.builder()
                          .commissionHsaId(credentialInformation.getHsaId())
                          .commissionPurpose(purpose)
                          .commissionName(credentialInformation.getGivenName())
                          .healthCareUnitHsaId(parsedCareUnit.getId())
                          .healthCareUnitName(parsedCareUnit.getName())
                          .healthCareUnitStartDate(parsedCareUnit.getStart())
                          .healthCareUnitEndDate(parsedCareUnit.getEnd())
                          .healthCareProviderOrgNo(parsedCareUnit.getHealthCareProviderOrgno())
                          .healthCareProviderHsaId(parsedCareProvider.getId())
                          .healthCareProviderName(parsedCareProvider.getName())
                          .build()));
    }

    return commissionsList;
  }

  private static Predicate<ParsedCommission> isPresentInUnitMap(
      Map<String, ParsedCareUnit> careUnitMap) {
    return parsedCommission -> careUnitMap.containsKey(parsedCommission.getHealthCareUnitHsaId());
  }

  private static Predicate<ParsedCommission> hasCommissonPurpose() {
    return parsedCommission ->
        parsedCommission.getCommissionPurpose() != null
            && !parsedCommission.getCommissionPurpose().isEmpty();
  }
}
