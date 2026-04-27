package se.inera.intyg.intygproxyservice.integration.api.organization;

import java.util.ArrayList;
import java.util.List;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.Address;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.Unit;

public class AddressConverter {

  private AddressConverter() {
  }

  /**
   * Converts the address part of a Unit by creating a new Unit. Both the structured address field
   * and the unstructured address list are always populated.
   *
   * @param unit The Unit to convert. Must not be null.
   * @return
   */
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
