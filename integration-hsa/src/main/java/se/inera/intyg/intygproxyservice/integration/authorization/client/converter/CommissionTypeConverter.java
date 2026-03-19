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
package se.inera.intyg.intygproxyservice.integration.authorization.client.converter;

import static se.inera.intyg.intygproxyservice.integration.common.TypeConverterHelper.toLocalDate;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import se.inera.intyg.intygproxyservice.integration.api.authorization.model.Commission;
import se.riv.infrastructure.directory.authorizationmanagement.v2.CommissionType;

@Component
@RequiredArgsConstructor
public class CommissionTypeConverter {

  private final CommissionRightTypeConverter commissionRightTypeConverter;

  public Commission convert(CommissionType type) {
    if (type == null) {
      return Commission.builder().build();
    }

    return Commission.builder()
        .commissionHsaId(type.getCommissionHsaId())
        .commissionName(type.getCommissionName())
        .commissionPurpose(type.getCommissionPurpose())
        .healthCareUnitHsaId(type.getHealthCareUnitHsaId())
        .healthCareProviderHsaId(type.getHealthCareProviderHsaId())
        .healthCareProviderName(type.getHealthCareProviderName())
        .healthCareProviderOrgNo(type.getHealthCareProviderOrgNo())
        .healthCareProviderEndDate(toLocalDate(type.getHealthCareProviderEndDate()))
        .healthCareProviderStartDate(toLocalDate(type.getHealthCareProviderStartDate()))
        .healthCareUnitName(type.getHealthCareUnitName())
        .healthCareUnitEndDate(toLocalDate(type.getHealthCareUnitEndDate()))
        .healthCareUnitStartDate(toLocalDate(type.getHealthCareUnitStartDate()))
        .feignedCommission(type.isFeignedCommission())
        .archivedHealthCareProvider(type.isArchivedHealthCareProvider())
        .archivedHealthCareUnit(type.isArchivedHealthCareUnit())
        .feignedHealthCareUnit(type.isFeignedHealthCareUnit())
        .feignedHealthCareProvider(type.isFeignedHealthCareProvider())
        .pharmacyIdentifier(type.getPharmacyIdentifier())
        .commissionRight(
            type.getCommissionRight().stream().map(commissionRightTypeConverter::convert).toList())
        .build();
  }
}
