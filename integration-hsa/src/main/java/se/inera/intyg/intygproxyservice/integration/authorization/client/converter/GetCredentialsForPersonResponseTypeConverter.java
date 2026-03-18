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

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import se.inera.intyg.intygproxyservice.integration.api.authorization.model.CredentialsForPerson;
import se.riv.infrastructure.directory.authorizationmanagement.gethospcredentialsforpersonresponder.v1.GetHospCredentialsForPersonResponseType;
import se.riv.infrastructure.directory.authorizationmanagement.v2.IIType;

@Component
@RequiredArgsConstructor
public class GetCredentialsForPersonResponseTypeConverter {

  private final NursePrescriptionRightTypeConverter nursePrescriptionRightTypeConverter;
  private final HCPSpecialityCodeTypeConverter hcpSpecialityCodeTypeConverter;
  private final HealthCareProfessionalLicenceTypeConverter
      healthCareProfessionalLicenceTypeConverter;
  private final RestrictionTypeConverter restrictionTypeConverter;

  public CredentialsForPerson convert(GetHospCredentialsForPersonResponseType type) {
    if (type == null) {
      return CredentialsForPerson.builder().build();
    }

    return CredentialsForPerson.builder()
        .feignedPerson(type.isFeignedPerson())
        .educationCode(type.getEducationCode())
        .personalIdentityNumber(convertPersonalIdentity(type.getPersonalIdentityNumber()))
        .personalPrescriptionCode(type.getPersonalPrescriptionCode())
        .nursePrescriptionRight(
            type.getNursePrescriptionRight().stream()
                .map(nursePrescriptionRightTypeConverter::convert)
                .toList())
        .healthcareProfessionalLicenseIdentityNumber(
            type.getHealthcareProfessionalLicenseIdentityNumber())
        .healthCareProfessionalLicenceSpeciality(
            type.getHealthCareProfessionalLicenceSpeciality().stream()
                .map(hcpSpecialityCodeTypeConverter::convert)
                .toList())
        .healthCareProfessionalLicence(
            type.getHealthCareProfessionalLicence().stream()
                .map(healthCareProfessionalLicenceTypeConverter::convert)
                .toList())
        .restrictions(
            type.getRestrictions().stream().map(restrictionTypeConverter::convert).toList())
        .build();
  }

  private String convertPersonalIdentity(IIType type) {
    return type == null ? null : type.getExtension();
  }
}
