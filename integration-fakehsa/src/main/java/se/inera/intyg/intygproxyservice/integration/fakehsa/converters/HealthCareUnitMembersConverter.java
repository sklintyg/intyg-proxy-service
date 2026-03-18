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
import java.util.function.Predicate;
import org.springframework.stereotype.Component;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.HealthCareUnitMember;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.HealthCareUnitMembers;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedCareUnit;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedSubUnit;

@Component
public class HealthCareUnitMembersConverter {

  private static final String NOT_FOUND = "-finns-ej";

  public HealthCareUnitMembers convert(ParsedCareUnit parsedCareUnit) {
    return HealthCareUnitMembers.builder()
        .healthCareUnitPrescriptionCode(toList(parsedCareUnit.getPrescriptionCode()))
        .healthCareUnitMember(getHealthCareUnitMember(parsedCareUnit))
        .build();
  }

  private List<HealthCareUnitMember> getHealthCareUnitMember(ParsedCareUnit parsedCareUnit) {
    if (hasSubUnits(parsedCareUnit.getSubUnits())) {
      return getHealthCareUnitMembers(parsedCareUnit.getSubUnits());
    }
    return Collections.emptyList();
  }

  private static boolean hasSubUnits(List<ParsedSubUnit> parsedCareUnit) {
    return parsedCareUnit != null && !parsedCareUnit.isEmpty();
  }

  private static List<String> toList(String value) {
    if (value == null || value.isEmpty()) {
      return Collections.emptyList();
    }
    return List.of(value);
  }

  private List<HealthCareUnitMember> getHealthCareUnitMembers(List<ParsedSubUnit> subUnits) {
    return subUnits.stream()
        .filter(removeInvalidUnits())
        .map(
            subUnit ->
                HealthCareUnitMember.builder()
                    .healthCareUnitMemberHsaId(subUnit.getId())
                    .healthCareUnitMemberName(subUnit.getName())
                    .healthCareUnitMemberStartDate(subUnit.getStart())
                    .healthCareUnitMemberEndDate(subUnit.getEnd())
                    .healthCareUnitMemberpostalAddress(toList(subUnit.getPostalAddress()))
                    .healthCareUnitMemberpostalCode(subUnit.getPostalCode())
                    .healthCareUnitMemberPrescriptionCode(toList(subUnit.getPrescriptionCode()))
                    .build())
        .toList();
  }

  private static Predicate<ParsedSubUnit> removeInvalidUnits() {
    return subUnit -> !subUnit.getId().endsWith(NOT_FOUND);
  }
}
