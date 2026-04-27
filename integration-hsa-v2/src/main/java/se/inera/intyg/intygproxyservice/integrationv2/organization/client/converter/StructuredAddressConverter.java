package se.inera.intyg.intygproxyservice.integrationv2.organization.client.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.Address;
import se.riv.infrastructure.directory.organization.getunitresponder.v5.UnitType;

@Component
@RequiredArgsConstructor
public class StructuredAddressConverter {

  public Address convert(UnitType type) {
    return Address.builder()
        .street(type.getStreet())
        .streetNumber(type.getStructuredPostalAddress().getPremisesNumber())
        .streetLetter(type.getStructuredPostalAddress().getPremisesLetter())
        .zipCode(type.getStructuredPostalAddress().getPostCode())
        .city(type.getStructuredPostalAddress().getTown())
        .build();
  }
}
