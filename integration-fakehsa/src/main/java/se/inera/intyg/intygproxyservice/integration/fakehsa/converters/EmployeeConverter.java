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
import java.util.function.Function;
import org.springframework.stereotype.Component;
import se.inera.intyg.intygproxyservice.integration.api.employee.Employee;
import se.inera.intyg.intygproxyservice.integration.api.employee.HCPSpecialityCode;
import se.inera.intyg.intygproxyservice.integration.api.employee.PersonInformation;
import se.inera.intyg.intygproxyservice.integration.api.employee.PersonInformation.PaTitle;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedHsaPerson;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedHsaPerson.ParsedPaTitle;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedHsaPerson.Speciality;

@Component
public class EmployeeConverter {

  private static final boolean FEIGNED_PERSON = false;

  public Employee convert(ParsedHsaPerson parsedHsaPerson) {
    return Employee.builder()
        .personInformation(
            List.of(
                PersonInformation.builder()
                    .personHsaId(parsedHsaPerson.getHsaId())
                    .age(parsedHsaPerson.getAge())
                    .gender(parsedHsaPerson.getGender())
                    .givenName(parsedHsaPerson.getGivenName())
                    .feignedPerson(FEIGNED_PERSON)
                    .title(parsedHsaPerson.getTitle())
                    .healthCareProfessionalLicence(
                        parsedHsaPerson.getHealthCareProfessionalLicence())
                    .healthCareProfessionalLicenceSpeciality(getSpecialities(parsedHsaPerson))
                    .middleAndSurName(parsedHsaPerson.getMiddleAndSurname())
                    .protectedPerson(parsedHsaPerson.isProtectedPerson())
                    .paTitle(getPaTitles(parsedHsaPerson.getPaTitle()))
                    .build()))
        .build();
  }

  private List<PaTitle> getPaTitles(List<ParsedPaTitle> parsedPaTitle) {
    if (parsedPaTitle == null || parsedPaTitle.isEmpty()) {
      return Collections.emptyList();
    }
    return parsedPaTitle.stream().map(toPaTitle()).toList();
  }

  private static List<HCPSpecialityCode> getSpecialities(ParsedHsaPerson parsedHsaPerson) {
    if (parsedHsaPerson.getSpecialities() == null || parsedHsaPerson.getSpecialities().isEmpty()) {
      return Collections.emptyList();
    }
    return parsedHsaPerson.getSpecialities().stream().map(toHCPSpecialityCode()).toList();
  }

  private static Function<ParsedPaTitle, PaTitle> toPaTitle() {
    return title ->
        PaTitle.builder()
            .paTitleCode(title.getTitleCode())
            .paTitleName(title.getTitleName())
            .build();
  }

  private static Function<Speciality, HCPSpecialityCode> toHCPSpecialityCode() {
    return speciality ->
        HCPSpecialityCode.builder()
            .specialityCode(speciality.getSpecialityCode())
            .specialityName(speciality.getSpecialityName())
            .build();
  }
}
