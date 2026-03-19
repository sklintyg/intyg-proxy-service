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

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import se.inera.intyg.intygproxyservice.integration.api.authorization.model.CredentialsForPerson;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedHsaPerson;

@Component
@RequiredArgsConstructor
public class CredentialsForPersonConverter {

  private final RestrictionConverter restrictionConverter;
  private final SpecialitiesConverter specialitiesConverter;
  private final ProfessionalLicenceTypeConverter licenceTypeConverter;

  public CredentialsForPerson convert(ParsedHsaPerson parsedHsaPerson) {
    if (parsedHsaPerson == null) {
      return CredentialsForPerson.builder().build();
    }

    return CredentialsForPerson.builder()
        .personalIdentityNumber(parsedHsaPerson.getPersonalIdentityNumber())
        .personalPrescriptionCode(parsedHsaPerson.getPersonalPrescriptionCode())
        .educationCode(parsedHsaPerson.getEducationCodes())
        .restrictions(
            parsedHsaPerson.getRestrictions().stream().map(restrictionConverter::convert).toList())
        .healthCareProfessionalLicenceSpeciality(
            parsedHsaPerson.getSpecialities().stream().map(specialitiesConverter::convert).toList())
        .healthCareProfessionalLicence(
            parsedHsaPerson.getHealthCareProfessionalLicenceType().stream()
                .map(licenceTypeConverter::convert)
                .toList())
        .build();
  }
}
