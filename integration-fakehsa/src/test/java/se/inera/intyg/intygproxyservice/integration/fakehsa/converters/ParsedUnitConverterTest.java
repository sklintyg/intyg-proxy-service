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

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedCareUnit;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedSubUnit;

class ParsedUnitConverterTest {

  private static final String HSA_ID = "hsaId";
  private static final String UNIT_NAME = "unitName";
  private static final String POSTAL_ADDRESS = "post1";
  private static final String POSTAL_TOWN = "postalTown";
  private static final String POSTAL_CODE = "postalCode";
  private static final String MUNICIPALITY_CODE = "municipalityCode";
  private static final String MAIL = "mail";
  private static final String COUNTRY_CODE = "countryCode";
  private static final String PHONE_NUMBER = "phoneNumber";
  private ParsedUnitConverter parsedUnitConverter = new ParsedUnitConverter();

  @Nested
  class ParsedUnitTest {

    @Test
    void shouldConvertId() {
      final var result = parsedUnitConverter.convert(getParsedUnit()).getUnitHsaId();
      assertEquals(HSA_ID, result);
    }

    @Test
    void shouldConvertName() {
      final var result = parsedUnitConverter.convert(getParsedUnit()).getUnitName();
      assertEquals(UNIT_NAME, result);
    }

    @Test
    void shouldConvertMail() {
      final var result = parsedUnitConverter.convert(getParsedUnit()).getMail();
      assertEquals(MAIL, result);
    }

    @Test
    void shouldConvertPostalCode() {
      final var result = parsedUnitConverter.convert(getParsedUnit()).getPostalCode();
      assertEquals(POSTAL_CODE, result);
    }

    @Test
    void shouldConvertPostalAddress() {
      final var result = parsedUnitConverter.convert(getParsedUnit()).getPostalAddress();
      assertEquals(List.of(POSTAL_ADDRESS, POSTAL_TOWN), result);
    }

    @Test
    void shouldConvertCountryCode() {
      final var result = parsedUnitConverter.convert(getParsedUnit()).getCountyCode();
      assertEquals(COUNTRY_CODE, result);
    }

    @Test
    void shouldConvertMunicipalityCode() {
      final var result = parsedUnitConverter.convert(getParsedUnit()).getMunicipalityCode();
      assertEquals(MUNICIPALITY_CODE, result);
    }

    @Test
    void shouldConvertPhoneNumber() {
      final var result = parsedUnitConverter.convert(getParsedUnit()).getTelephoneNumber();
      assertEquals(List.of(PHONE_NUMBER), result);
    }
  }

  @Nested
  class ParsedSubUnitTest {

    @Test
    void shouldConvertId() {
      final var result = parsedUnitConverter.convert(getParsedSubUnit()).getUnitHsaId();
      assertEquals(HSA_ID, result);
    }

    @Test
    void shouldConvertName() {
      final var result = parsedUnitConverter.convert(getParsedSubUnit()).getUnitName();
      assertEquals(UNIT_NAME, result);
    }

    @Test
    void shouldConvertMail() {
      final var result = parsedUnitConverter.convert(getParsedSubUnit()).getMail();
      assertEquals(MAIL, result);
    }

    @Test
    void shouldConvertPostalCode() {
      final var result = parsedUnitConverter.convert(getParsedSubUnit()).getPostalCode();
      assertEquals(POSTAL_CODE, result);
    }

    @Test
    void shouldConvertPostalAddress() {
      final var result = parsedUnitConverter.convert(getParsedSubUnit()).getPostalAddress();
      assertEquals(List.of(POSTAL_ADDRESS, POSTAL_TOWN), result);
    }

    @Test
    void shouldConvertCountryCode() {
      final var result = parsedUnitConverter.convert(getParsedSubUnit()).getCountyCode();
      assertEquals(COUNTRY_CODE, result);
    }

    @Test
    void shouldConvertMunicipalityCode() {
      final var result = parsedUnitConverter.convert(getParsedSubUnit()).getMunicipalityCode();
      assertEquals(MUNICIPALITY_CODE, result);
    }

    @Test
    void shouldConvertPhoneNumber() {
      final var result = parsedUnitConverter.convert(getParsedSubUnit()).getTelephoneNumber();
      assertEquals(List.of(PHONE_NUMBER), result);
    }
  }

  private ParsedSubUnit getParsedSubUnit() {
    return ParsedSubUnit.builder()
        .id(HSA_ID)
        .name(UNIT_NAME)
        .postalAddress(POSTAL_ADDRESS)
        .postalTown(POSTAL_TOWN)
        .postalCode(POSTAL_CODE)
        .countyCode(COUNTRY_CODE)
        .mail(MAIL)
        .municipalityCode(MUNICIPALITY_CODE)
        .telephoneNumber(PHONE_NUMBER)
        .build();
  }

  private ParsedCareUnit getParsedUnit() {
    return ParsedCareUnit.builder()
        .id(HSA_ID)
        .name(UNIT_NAME)
        .postalAddress(POSTAL_ADDRESS)
        .postalTown(POSTAL_TOWN)
        .postalCode(POSTAL_CODE)
        .telephoneNumber(PHONE_NUMBER)
        .countyCode(COUNTRY_CODE)
        .mail(MAIL)
        .municipalityCode(MUNICIPALITY_CODE)
        .build();
  }
}
