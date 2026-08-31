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
package se.inera.intyg.intygproxyservice.integration.fakehsa.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.intygproxyservice.integration.api.employee.HCPSpecialityCode;
import se.inera.intyg.intygproxyservice.integration.api.employee.PersonInformation.PaTitle;
import se.inera.intyg.intygproxyservice.integration.fakehsa.converters.EmployeeConverter;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedHsaPerson;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedHsaPerson.ParsedPaTitle;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedHsaPerson.Speciality;

class EmployeeConverterTest {

  private EmployeeConverter employeeConverter;

  private static final String VALUE = "value";

  @BeforeEach
  void setUp() {
    employeeConverter = new EmployeeConverter();
  }

  @Nested
  class ConvertHsaId {

    @Test
    void shouldConvertHsaId() {
      final var hsaPerson = ParsedHsaPerson.builder().hsaId(VALUE).build();
      final var result = employeeConverter.convert(hsaPerson);
      assertEquals(hsaPerson.getHsaId(), result.getPersonInformation().getFirst().getPersonHsaId());
    }

    @Test
    void shouldNotConvertHsaId() {
      final var hsaPerson = ParsedHsaPerson.builder().build();
      final var result = employeeConverter.convert(hsaPerson);
      assertNull(result.getPersonInformation().getFirst().getPersonHsaId());
    }
  }

  @Nested
  class ConvertAge {

    @Test
    void shouldConvertAge() {
      final var hsaPerson = ParsedHsaPerson.builder().age(VALUE).build();
      final var result = employeeConverter.convert(hsaPerson);
      assertEquals(hsaPerson.getAge(), result.getPersonInformation().getFirst().getAge());
    }

    @Test
    void shouldNotConvertAge() {
      final var hsaPerson = ParsedHsaPerson.builder().build();
      final var result = employeeConverter.convert(hsaPerson);
      assertNull(result.getPersonInformation().getFirst().getAge());
    }
  }

  @Nested
  class ConvertGender {

    @Test
    void shouldConvertGender() {
      final var hsaPerson = ParsedHsaPerson.builder().gender(VALUE).build();
      final var result = employeeConverter.convert(hsaPerson);
      assertEquals(hsaPerson.getGender(), result.getPersonInformation().getFirst().getGender());
    }

    @Test
    void shouldNotConvertGender() {
      final var hsaPerson = ParsedHsaPerson.builder().build();
      final var result = employeeConverter.convert(hsaPerson);
      assertNull(result.getPersonInformation().getFirst().getGender());
    }
  }

  @Nested
  class ConvertGivenName {

    @Test
    void shouldConvertGivenName() {
      final var hsaPerson = ParsedHsaPerson.builder().givenName(VALUE).build();
      final var result = employeeConverter.convert(hsaPerson);
      assertEquals(
          hsaPerson.getGivenName(), result.getPersonInformation().getFirst().getGivenName());
    }

    @Test
    void shouldNotConvertGivenName() {
      final var hsaPerson = ParsedHsaPerson.builder().build();
      final var result = employeeConverter.convert(hsaPerson);
      assertNull(result.getPersonInformation().getFirst().getGivenName());
    }
  }

  @Nested
  class ConvertFeignedPerson {

    @Test
    void shouldSetFeignedPersonToFalse() {
      final var hsaPerson = ParsedHsaPerson.builder().build();
      final var result = employeeConverter.convert(hsaPerson);
      assertFalse(result.getPersonInformation().getFirst().getFeignedPerson());
    }
  }

  @Nested
  class ConvertTitle {

    @Test
    void shouldConvertTitle() {
      final var hsaPerson = ParsedHsaPerson.builder().title(VALUE).build();
      final var result = employeeConverter.convert(hsaPerson);
      assertEquals(hsaPerson.getTitle(), result.getPersonInformation().getFirst().getTitle());
    }

    @Test
    void shouldNotConvertTitle() {
      final var hsaPerson = ParsedHsaPerson.builder().build();
      final var result = employeeConverter.convert(hsaPerson);
      assertEquals(hsaPerson.getTitle(), result.getPersonInformation().getFirst().getTitle());
    }
  }

  @Nested
  class ConvertHealthCareProfessionalLicence {

    @Test
    void shouldConvertHealthCareProfessionalLicense() {
      final var hsaPerson =
          ParsedHsaPerson.builder().healthCareProfessionalLicence(List.of(VALUE)).build();
      final var result = employeeConverter.convert(hsaPerson);
      assertEquals(
          hsaPerson.getHealthCareProfessionalLicence(),
          result.getPersonInformation().getFirst().getHealthCareProfessionalLicence());
    }

    @Test
    void shouldNotConvertHealthCareProfessionalLicense() {
      final var hsaPerson = ParsedHsaPerson.builder().build();
      final var result = employeeConverter.convert(hsaPerson);
      assertTrue(
          result.getPersonInformation().getFirst().getHealthCareProfessionalLicence().isEmpty());
    }
  }

  @Nested
  class ConvertMiddleAndSurName {

    @Test
    void shouldConvertMiddleAndSurName() {
      final var hsaPerson = ParsedHsaPerson.builder().middleAndSurname(VALUE).build();
      final var result = employeeConverter.convert(hsaPerson);
      assertEquals(
          hsaPerson.getMiddleAndSurname(),
          result.getPersonInformation().getFirst().getMiddleAndSurName());
    }

    @Test
    void shouldNotConvertHealthCareProfessionalLicense() {
      final var hsaPerson = ParsedHsaPerson.builder().build();
      final var result = employeeConverter.convert(hsaPerson);
      assertNull(result.getPersonInformation().getFirst().getMiddleAndSurName());
    }
  }

  @Nested
  class ConvertProtectedPerson {

    @Test
    void shouldConvertProtectedPerson() {
      final var hsaPerson = ParsedHsaPerson.builder().protectedPerson(false).build();
      final var result = employeeConverter.convert(hsaPerson);
      assertEquals(
          hsaPerson.isProtectedPerson(),
          result.getPersonInformation().getFirst().getProtectedPerson());
    }

    @Test
    void shouldSetProtectedPersonToFalseByDefault() {
      final var hsaPerson = ParsedHsaPerson.builder().build();
      final var result = employeeConverter.convert(hsaPerson);
      assertFalse(result.getPersonInformation().getFirst().getProtectedPerson());
    }
  }

  @Nested
  class ConvertSpecialityCode {

    @Test
    void shouldConvertSpecialityCode() {
      final var hsaPerson =
          ParsedHsaPerson.builder()
              .specialities(List.of(Speciality.builder().specialityCode(VALUE).build()))
              .build();
      final var result = employeeConverter.convert(hsaPerson);
      assertEquals(List.of(VALUE), result.getPersonInformation().getFirst().getSpecialityCode());
    }

    @Test
    void shouldFilterNullSpecialityCode() {
      final var hsaPerson =
          ParsedHsaPerson.builder().specialities(List.of(Speciality.builder().build())).build();
      final var result = employeeConverter.convert(hsaPerson);
      assertTrue(result.getPersonInformation().getFirst().getSpecialityCode().isEmpty());
    }

    @Test
    void shouldNotConvertSpecialityCode() {
      final var hsaPerson = ParsedHsaPerson.builder().build();
      final var result = employeeConverter.convert(hsaPerson);
      assertTrue(result.getPersonInformation().getFirst().getSpecialityCode().isEmpty());
    }

    @Test
    void shouldReturnEmptyListWhenSpecialitiesIsNull() {
      final var hsaPerson = ParsedHsaPerson.builder().specialities(null).build();
      final var result = employeeConverter.convert(hsaPerson);
      assertTrue(result.getPersonInformation().getFirst().getSpecialityCode().isEmpty());
    }
  }

  @Nested
  class ConvertSpecialityName {

    @Test
    void shouldConvertSpecialityName() {
      final var hsaPerson =
          ParsedHsaPerson.builder()
              .specialities(List.of(Speciality.builder().specialityName(VALUE).build()))
              .build();
      final var result = employeeConverter.convert(hsaPerson);
      assertEquals(List.of(VALUE), result.getPersonInformation().getFirst().getSpecialityName());
    }

    @Test
    void shouldFilterNullSpecialityName() {
      final var hsaPerson =
          ParsedHsaPerson.builder().specialities(List.of(Speciality.builder().build())).build();
      final var result = employeeConverter.convert(hsaPerson);
      assertTrue(result.getPersonInformation().getFirst().getSpecialityName().isEmpty());
    }

    @Test
    void shouldNotConvertSpecialityName() {
      final var hsaPerson = ParsedHsaPerson.builder().build();
      final var result = employeeConverter.convert(hsaPerson);
      assertTrue(result.getPersonInformation().getFirst().getSpecialityName().isEmpty());
    }

    @Test
    void shouldReturnEmptyListWhenSpecialitiesIsNull() {
      final var hsaPerson = ParsedHsaPerson.builder().specialities(null).build();
      final var result = employeeConverter.convert(hsaPerson);
      assertTrue(result.getPersonInformation().getFirst().getSpecialityName().isEmpty());
    }
  }

  @Nested
  class ConvertHealthCareProfessionalLicenceSpeciality {

    @Test
    void shouldConvertHealthCareProfessionalLicenceSpeciality() {
      final var hsaPerson =
          ParsedHsaPerson.builder()
              .specialities(
                  List.of(Speciality.builder().specialityCode(VALUE).specialityName(VALUE).build()))
              .build();
      final var expectedResult =
          List.of(HCPSpecialityCode.builder().specialityCode(VALUE).specialityName(VALUE).build());
      final var result = employeeConverter.convert(hsaPerson);
      assertEquals(
          expectedResult,
          result.getPersonInformation().getFirst().getHealthCareProfessionalLicenceSpeciality());
    }

    @Test
    void shouldNotConvertHealthCareProfessionalLicenceSpeciality() {
      final var hsaPerson = ParsedHsaPerson.builder().build();
      final var result = employeeConverter.convert(hsaPerson);
      assertTrue(
          result
              .getPersonInformation()
              .getFirst()
              .getHealthCareProfessionalLicenceSpeciality()
              .isEmpty());
    }
  }

  @Nested
  class ConvertParsedPaTitle {

    @Test
    void shouldConvertPaTitle() {
      final var hsaPerson =
          ParsedHsaPerson.builder()
              .paTitle(List.of(ParsedPaTitle.builder().titleName(VALUE).titleCode(VALUE).build()))
              .build();
      final var expectedResponse =
          List.of(PaTitle.builder().paTitleCode(VALUE).paTitleName(VALUE).build());
      final var result = employeeConverter.convert(hsaPerson);
      assertEquals(expectedResponse, result.getPersonInformation().getFirst().getPaTitle());
    }

    @Test
    void shouldNotConvertPaTitle() {
      final var hsaPerson = ParsedHsaPerson.builder().build();
      final var result = employeeConverter.convert(hsaPerson);
      assertTrue(result.getPersonInformation().getFirst().getPaTitle().isEmpty());
    }
  }
}
