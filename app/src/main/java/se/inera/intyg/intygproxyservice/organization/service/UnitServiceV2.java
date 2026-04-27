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
package se.inera.intyg.intygproxyservice.organization.service;

import static se.inera.intyg.intygproxyservice.common.ValidationUtility.isStringInvalid;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import se.inera.intyg.intygproxyservice.integration.api.organization.GetUnitIntegrationRequest;
import se.inera.intyg.intygproxyservice.integration.api.organization.GetUnitIntegrationService;
import se.inera.intyg.intygproxyservice.integration.api.organization.AddressConverter;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.Unit;
import se.inera.intyg.intygproxyservice.organization.dto.UnitRequest;
import se.inera.intyg.intygproxyservice.organization.dto.UnitResponseV2;

@Service
@AllArgsConstructor
@Slf4j
@Profile("v2")
public class UnitServiceV2 {

  private final GetUnitIntegrationService getUnitIntegrationService;

  public UnitResponseV2 get(UnitRequest request) {
    validateRequest(request);

    log.info(String.format("Getting unit with hsaId: '%s'", request.getHsaId()));

    final var response =
        getUnitIntegrationService.get(
            GetUnitIntegrationRequest.builder().hsaId(request.getHsaId()).build());

    if (response.getUnit() != null) {
      log.info(String.format("Unit with hsaId: '%s' was retrieved", request.getHsaId()));
    } else {
      log.warn("No unit was found with hsaId '{}', returning empty unit", request.getHsaId());
    }

    se.inera.intyg.intygproxyservice.organization.dto.Unit dtoUnit = null;
    if (response.getUnit() != null) {
      Unit convertedUnit = AddressConverter.convert(response.getUnit());
      dtoUnit =
          se.inera.intyg.intygproxyservice.organization.dto.Unit.builder()
              .unitStartDate(convertedUnit.getUnitStartDate())
              .unitEndDate(convertedUnit.getUnitEndDate())
              .feignedUnit(convertedUnit.getFeignedUnit())
              .unitHsaId(convertedUnit.getUnitHsaId())
              .unitName(convertedUnit.getUnitName())
              .telephoneNumber(convertedUnit.getTelephoneNumber())
              .address(convertedUnit.getAddress())
              .mail(convertedUnit.getMail())
              .build();
    }

    return UnitResponseV2.builder().unit(dtoUnit).build();
  }

  private static void validateRequest(UnitRequest request) {
    if (request == null) {
      throw new IllegalArgumentException("Request to get unit is null");
    }

    if (isStringInvalid(request.getHsaId())) {
      throw new IllegalArgumentException(
          String.format("HsaId is not valid: '%s'", request.getHsaId()));
    }
  }
}
