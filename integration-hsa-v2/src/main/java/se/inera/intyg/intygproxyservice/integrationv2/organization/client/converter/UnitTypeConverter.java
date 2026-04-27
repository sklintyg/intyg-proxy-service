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
package se.inera.intyg.intygproxyservice.integrationv2.organization.client.converter;

import static se.inera.intyg.intygproxyservice.integration.common.TypeConverterHelper.toLocalDate;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.Address;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.Unit;
import se.riv.infrastructure.directory.organization.getunitresponder.v5.UnitType;

@Service
@RequiredArgsConstructor
public class UnitTypeConverter {

  private final AddressTypeConverter addressTypeConverter;
  private final StructuredAddressConverter structuredAddressConverter;
  private final GeoCoordRt90TypeConverter geoCoordRt90TypeConverter;
  private final GeoCoordSweref99TypeConverter geoCoordSweref99TypeConverter;
  private final BusinessClassificationTypeConverter businessClassificationTypeConverter;

  public Unit convert(UnitType type) {
    Unit unit = Unit.builder()
        .unitName(type.getUnitName())
        .unitHsaId(type.getUnitHsaId())
        .countyName(type.getCountyName())
        .countyCode(type.getCountyCode())
        .municipalityCode(type.getMunicipalityCode())
        .municipalityName(type.getMunicipalityName())
        .feignedUnit(type.isFeignedUnit())
        .location(type.getLocation())
        .mail(type.getMail())
        .unitStartDate(toLocalDate(type.getUnitStartDate()))
        .unitEndDate(toLocalDate(type.getUnitEndDate()))
        .postalCode(type.getPostalCode())
        .postalAddress(addressTypeConverter.convertV5(type.getPostalAddress()))
        .address(structuredAddressConverter.convert(type))
        .careType(type.getCareType())
        .businessType(type.getBusinessType())
        .management(type.getManagement())
        .telephoneNumber(type.getTelephoneNumber())
        .geographicalCoordinatesRt90(
            geoCoordRt90TypeConverter.convert(type.getGeographicalCoordinatesRt90()))
        .geographicalCoordinatesSweref99(
            geoCoordSweref99TypeConverter.convert(type.getGeographicalCoordinatesSWEREF99()))
        .businessClassification(
            type.getBusinessClassification().stream()
                .map(businessClassificationTypeConverter::convert)
                .toList())
        .build();

    return convert(unit);
  }

  public static Unit convert(final Unit unit) {
    if (unit == null) {
      throw new IllegalStateException("unit is null");
    }

    String street = null;
    String streetNumber = null;
    String streetLetter = null;
    String zipCode = null;
    String city = null;

    if (unit.getAddress() != null) {
      street = unit.getAddress().street();
      streetNumber = unit.getAddress().streetNumber();
      streetLetter = unit.getAddress().streetLetter();
      zipCode = unit.getAddress().zipCode();
      city = unit.getAddress().city();
    } else {
      int length = unit.getPostalAddress().size();
      if (length > 0) {
        street = unit.getPostalAddress().getFirst();
      }
      if (length > 1) {
        streetNumber = unit.getPostalAddress().get(1);
      }
      if (length > 2) {
        streetLetter = unit.getPostalAddress().get(2);
      }
      if (length > 3) {
        zipCode = unit.getPostalAddress().get(3);
      }
      if (length > 4) {
        city = unit.getPostalAddress().get(4);
      }
    }
    List<String> postalAddress = new ArrayList<>();
    postalAddress.add(street);
    postalAddress.add(streetNumber);
    postalAddress.add(streetLetter);
    postalAddress.add(zipCode);
    postalAddress.add(city);
    return Unit.builder()
        .businessType(unit.getBusinessType())
        .businessClassification(unit.getBusinessClassification())
        .careType(unit.getCareType())
        .countyName(unit.getCountyName())
        .countyCode(unit.getCountyCode())
        .geographicalCoordinatesRt90(unit.getGeographicalCoordinatesRt90())
        .geographicalCoordinatesSweref99(unit.getGeographicalCoordinatesSweref99())
        .municipalityName(unit.getMunicipalityName())
        .municipalityCode(unit.getMunicipalityCode())
        .location(unit.getLocation())
        .unitStartDate(unit.getUnitStartDate())
        .unitEndDate(unit.getUnitEndDate())
        .feignedUnit(unit.getFeignedUnit())
        .unitHsaId(unit.getUnitHsaId())
        .unitName(unit.getUnitName())
        .postalAddress(postalAddress)
        .telephoneNumber(unit.getTelephoneNumber())
        .postalCode(unit.getPostalCode())
        .address(new Address(street, streetNumber, streetLetter, zipCode, city))
        .mail(unit.getMail())
        .management(unit.getManagement())
        .build();
  }
}
