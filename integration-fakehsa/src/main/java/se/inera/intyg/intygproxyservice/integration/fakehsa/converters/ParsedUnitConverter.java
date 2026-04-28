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

import java.util.List;
import org.springframework.stereotype.Component;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.Unit;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedCareUnit;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedSubUnit;

@Component
public class ParsedUnitConverter {

  public Unit convert(ParsedSubUnit parsedSubUnit) {
    return Unit.builder()
        .unitName(parsedSubUnit.getName())
        .unitHsaId(parsedSubUnit.getId())
        .mail(parsedSubUnit.getMail())
        .postalCode(parsedSubUnit.getPostalCode())
        .postalAddress(List.of(parsedSubUnit.getPostalAddress(), parsedSubUnit.getPostalTown()))
        .address(parsedSubUnit.getAddress())
        .countyCode(parsedSubUnit.getCountyCode())
        .municipalityCode(parsedSubUnit.getMunicipalityCode())
        .telephoneNumber(List.of(parsedSubUnit.getTelephoneNumber()))
        .build();
  }

  public Unit convert(ParsedCareUnit parsedCareUnit) {
    return Unit.builder()
        .unitName(parsedCareUnit.getName())
        .unitHsaId(parsedCareUnit.getId())
        .mail(parsedCareUnit.getMail())
        .postalCode(parsedCareUnit.getPostalCode())
        .postalAddress(List.of(parsedCareUnit.getPostalAddress(), parsedCareUnit.getPostalTown()))
        .address(parsedCareUnit.getAddress())
        .countyCode(parsedCareUnit.getCountyCode())
        .municipalityCode(parsedCareUnit.getMunicipalityCode())
        .telephoneNumber(List.of(parsedCareUnit.getTelephoneNumber()))
        .build();
  }
}
