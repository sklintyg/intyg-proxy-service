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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static se.inera.intyg.intygproxyservice.integration.common.TypeConverterHelper.toXMLGregorianCalendar;
import static se.inera.intyg.intygproxyservice.integration.common.TypeConverterHelper.truncateToSeconds;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import riv.infrastructure.directory.organization._5.AddressType;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.Address;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.BusinessClassification;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.GeoCoordRt90;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.GeoCoordSweref99;
import se.riv.infrastructure.directory.organization.getunitresponder.v5.BusinessClassificationType;
import se.riv.infrastructure.directory.organization.getunitresponder.v5.GeoCoordRt90Type;
import se.riv.infrastructure.directory.organization.getunitresponder.v5.GeoCoordSWEREF99Type;
import se.riv.infrastructure.directory.organization.getunitresponder.v5.UnitType;

@ExtendWith(MockitoExtension.class)
class UnitTypeConverterTest {

  @Mock
  private AddressTypeConverter addressTypeConverter;

  @Mock
  private GeoCoordRt90TypeConverter geoCoordRt90TypeConverter;

  @Mock
  private GeoCoordSweref99TypeConverter geoCoordSweref99TypeConverter;

  @Mock
  private BusinessClassificationTypeConverter businessClassificationTypeConverter;
  @Mock
  private StructuredAddressConverter structuredAddressConverter;
  @InjectMocks
  private UnitTypeConverter unitTypeConverter;

  public static final LocalDateTime UNIT_END_DATE = LocalDateTime.now().plusDays(10);
  public static final LocalDateTime UNIT_START_DATE = LocalDateTime.now().plusDays(9);

  @Test
  void shouldConvertName() {
    final var type = getType();

    final var response = unitTypeConverter.convert(type);

    assertEquals(type.getUnitName(), response.getUnitName());
  }

  @Test
  void shouldConvertId() {
    final var type = getType();

    final var response = unitTypeConverter.convert(type);

    assertEquals(type.getUnitHsaId(), response.getUnitHsaId());
  }

  @Test
  void shouldConvertCountyName() {
    final var type = getType();

    final var response = unitTypeConverter.convert(type);

    assertEquals(type.getCountyName(), response.getCountyName());
  }

  @Test
  void shouldConvertCountyCode() {
    final var type = getType();

    final var response = unitTypeConverter.convert(type);

    assertEquals(type.getCountyCode(), response.getCountyCode());
  }

  @Test
  void shouldConvertMunicipalityName() {
    final var type = getType();

    final var response = unitTypeConverter.convert(type);

    assertEquals(type.getMunicipalityName(), response.getMunicipalityName());
  }

  @Test
  void shouldConvertMunicipalityCode() {
    final var type = getType();

    final var response = unitTypeConverter.convert(type);

    assertEquals(type.getMunicipalityCode(), response.getMunicipalityCode());
  }

  @Test
  void shouldConvertFeignedUnit() {
    final var type = getType();

    final var response = unitTypeConverter.convert(type);

    assertEquals(type.isFeignedUnit(), response.getFeignedUnit());
  }

  @Test
  void shouldConvertLocation() {
    final var type = getType();

    final var response = unitTypeConverter.convert(type);

    assertEquals(type.getLocation(), response.getLocation());
  }

  @Test
  void shouldConvertEndDate() {
    final var type = getType();
    final var response = unitTypeConverter.convert(type);

    assertEquals(truncateToSeconds(UNIT_END_DATE), truncateToSeconds(response.getUnitEndDate()));
  }

  @Test
  void shouldConvertStartDate() {
    final var type = getType();
    final var response = unitTypeConverter.convert(type);

    assertEquals(
        truncateToSeconds(UNIT_START_DATE), truncateToSeconds(response.getUnitStartDate()));
  }

  @Test
  void shouldConvertPostalCode() {
    final var type = getType();
    final var response = unitTypeConverter.convert(type);

    assertEquals(type.getPostalCode(), response.getPostalCode());
  }

  @Test
  void shouldConvertMail() {
    final var type = getType();
    final var response = unitTypeConverter.convert(type);

    assertEquals(type.getMail(), response.getMail());
  }

  @Test
  void shouldConvertAddress() {
    final var type = mock(UnitType.class);
    final var address = List.of("ADDRESS", "ADDRESS_2");
    final var addressType = mock(AddressType.class);
    when(addressTypeConverter.convertV5(any(AddressType.class))).thenReturn(address);
    when(type.getPostalAddress()).thenReturn(addressType);

    final var response = unitTypeConverter.convert(type);

    assertEquals(address, response.getPostalAddress());
  }

  @Test
  void shouldConvertCareType() {
    final var type = mock(UnitType.class);
    final var data = List.of("DATA1", "DATA2");
    when(type.getCareType()).thenReturn(data);

    final var response = unitTypeConverter.convert(type);

    assertEquals(data, response.getCareType());
  }

  @Test
  void shouldConvertBusinessType() {
    final var type = mock(UnitType.class);
    final var data = List.of("DATA1", "DATA2");
    when(type.getBusinessType()).thenReturn(data);

    final var response = unitTypeConverter.convert(type);

    assertEquals(data, response.getBusinessType());
  }

  @Test
  void shouldConvertManagement() {
    final var type = mock(UnitType.class);
    final var data = List.of("DATA1", "DATA2");
    when(type.getManagement()).thenReturn(data);

    final var response = unitTypeConverter.convert(type);

    assertEquals(data, response.getManagement());
  }

  @Test
  void shouldConvertGeoCord() {
    final var type = new UnitType();
    final var geoType = new GeoCoordRt90Type();
    final var geo = GeoCoordRt90.builder().build();
    type.setGeographicalCoordinatesRt90(geoType);
    when(geoCoordRt90TypeConverter.convert(any(GeoCoordRt90Type.class))).thenReturn(geo);

    final var response = unitTypeConverter.convert(type);

    assertEquals(geo, response.getGeographicalCoordinatesRt90());
  }

  @Test
  void shouldConvertGeoCordSwe() {
    final var type = new UnitType();
    final var geoType = new GeoCoordSWEREF99Type();
    final var geo = GeoCoordSweref99.builder().build();
    type.setGeographicalCoordinatesSWEREF99(geoType);
    when(geoCoordSweref99TypeConverter.convert(any(GeoCoordSWEREF99Type.class))).thenReturn(geo);

    final var response = unitTypeConverter.convert(type);

    assertEquals(geo, response.getGeographicalCoordinatesSweref99());
  }

  @Test
  void shouldConvertBusinessClassification() {
    final var type = mock(UnitType.class);
    final var businessType = new BusinessClassificationType();
    final var business = BusinessClassification.builder().build();
    when(type.getBusinessClassification()).thenReturn(List.of(businessType));
    when(businessClassificationTypeConverter.convert(any(BusinessClassificationType.class)))
        .thenReturn(business);

    final var response = unitTypeConverter.convert(type);

    assertEquals(business, response.getBusinessClassification().getFirst());
  }

  @Test
  void shouldConvertStructuredAddress() {
    final var type = mock(UnitType.class);
    final var address =
        Address.builder().address("Test Street 1").zipCode("12345").city("Test City").build();
    when(structuredAddressConverter.convertV5(any(UnitType.class))).thenReturn(address);

    final var response = unitTypeConverter.convert(type);

    assertEquals(address, response.getAddress());
  }

  @Test
  void shouldConvertTelephoneNumber() {
    final var type = getType();
    final var response = unitTypeConverter.convert(type);

    assertEquals(type.getTelephoneNumber(), response.getTelephoneNumber());
  }

  private UnitType getType() {
    final var type = new UnitType();
    type.setUnitName("NAME");
    type.setUnitHsaId("HSAID");
    type.setCountyCode("CODE");
    type.setCountyName("COUNTY_NAME");
    type.setFeignedUnit(true);
    type.setLocation("LOCATION");
    type.setMail("MAIL");
    type.getTelephoneNumber().add("TELEPHONE_NUMBER");
    type.setUnitEndDate(toXMLGregorianCalendar(UNIT_END_DATE));
    type.setUnitStartDate(toXMLGregorianCalendar(UNIT_START_DATE));
    type.setPostalCode("POSTAL_CODE");

    return type;
  }
}
