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

import org.springframework.stereotype.Component;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.HealthCareUnit;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedCareUnit;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedSubUnit;

@Component
public class HealthCareUnitConverter {

  public HealthCareUnit convert(ParsedCareUnit parsedCareUnit) {
    return HealthCareUnit.builder()
        .healthCareUnitHsaId(parsedCareUnit.getId())
        .healthCareProviderStartDate(parsedCareUnit.getStart())
        .healthCareProviderEndDate(parsedCareUnit.getEnd())
        .healthCareUnitName(parsedCareUnit.getName())
        .unitIsHealthCareUnit(true)
        .healthCareProviderHsaId(parsedCareUnit.getCareProviderHsaId())
        .healthCareProviderOrgNo(parsedCareUnit.getHealthCareProviderOrgno())
        .build();
  }

  public HealthCareUnit convert(ParsedSubUnit parsedSubUnit) {
    return HealthCareUnit.builder()
        .healthCareUnitMemberHsaId(parsedSubUnit.getId())
        .healthCareUnitMemberName(parsedSubUnit.getName())
        .healthCareUnitMemberStartDate(parsedSubUnit.getStart())
        .healthCareUnitMemberEndDate(parsedSubUnit.getEnd())
        .healthCareUnitHsaId(parsedSubUnit.getParentHsaId())
        .unitIsHealthCareUnit(false)
        .build();
  }
}
