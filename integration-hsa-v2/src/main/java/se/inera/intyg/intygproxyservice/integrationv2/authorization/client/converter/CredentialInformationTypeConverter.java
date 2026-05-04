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
package se.inera.intyg.intygproxyservice.integrationv2.authorization.client.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import se.inera.intyg.intygproxyservice.integration.api.authorization.model.CredentialInformation;
import se.inera.intyg.intygproxyservice.se.riv.infrastructure.directory.authorizationmanagement.v2.CredentialInformationType;
import se.inera.intyg.intygproxyservice.se.riv.infrastructure.directory.authorizationmanagement.v2.IIType;

@Component
@RequiredArgsConstructor
public class CredentialInformationTypeConverter {

  private final CommissionTypeConverter commissionTypeConverter;
  private final NursePrescriptionRightTypeConverter nursePrescriptionRightTypeConverter;
  private final HCPSpecialityCodeTypeConverter hcpSpecialityCodeTypeConverter;
  private final HsaSystemRoleTypeConverter hsaSystemRoleTypeConverter;

  public CredentialInformation convert(CredentialInformationType type) {
    if (type == null) {
      return CredentialInformation.builder().build();
    }

    return CredentialInformation.builder()
        .personHsaId(type.getPersonHsaId())
        .personalIdentity(convertPersonalIdentity(type.getPersonalIdentity()))
        .givenName(type.getGivenName())
        .middleAndSurName(type.getMiddleAndSurName())
        .feignedPerson(type.isFeignedPerson())
        .groupPrescriptionCode(type.getGroupPrescriptionCode())
        .occupationalCode(type.getOccupationalCode())
        .healthCareProfessionalLicence(type.getHealthCareProfessionalLicence())
        .healthCareProfessionalLicenceCode(type.getHealthCareProfessionalLicenceCode())
        .healthcareProfessionalLicenseIdentityNumber(
            type.getHealthcareProfessionalLicenseIdentityNumber())
        .paTitleCode(type.getPaTitleCode())
        .protectedPerson(type.isProtectedPerson())
        .occupationalCode(type.getOccupationalCode())
        .personalPrescriptionCode(type.getPersonalPrescriptionCode())
        .commission(type.getCommission().stream().map(commissionTypeConverter::convert).toList())
        .nursePrescriptionRight(
            type.getNursePrescriptionRight().stream()
                .map(nursePrescriptionRightTypeConverter::convert)
                .toList())
        .healthCareProfessionalLicenceSpeciality(
            type.getHealthCareProfessionalLicenceSpeciality().stream()
                .map(hcpSpecialityCodeTypeConverter::convert)
                .toList())
        .hsaSystemRole(
            type.getHsaSystemRole().stream().map(hsaSystemRoleTypeConverter::convert).toList())
        .build();
  }

  private String convertPersonalIdentity(IIType type) {
    return type == null ? null : type.getExtension();
  }
}
