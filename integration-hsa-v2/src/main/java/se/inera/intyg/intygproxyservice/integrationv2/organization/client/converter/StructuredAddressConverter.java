package se.inera.intyg.intygproxyservice.integrationv2.organization.client.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import riv.infrastructure.directory.organization._5.StructuredPostalAddressType;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.Address;

@Component
@RequiredArgsConstructor
public class StructuredAddressConverter {

  public Address convert(StructuredPostalAddressType type) {
    return Address.builder()
        .street(type.getStreet())
        .streetNumber(type.getPremisesNumber())
        .streetLetter(type.getPremisesLetter())
        .zipCode(type.getPostCode())
        .city(type.getTown())
        .build();
  }
}
