package se.inera.intyg.intygproxyservice.integrationv2.organization.client.factory;

import org.springframework.stereotype.Component;
import riv.infrastructure.directory.organization._5.StructuredPostalAddressType;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.Address;

@Component
public class AddressFactory {

  public Address create(StructuredPostalAddressType type) {

    return Address.builder()
        .street(type.getStreet())
        .streetNumber(type.getPremisesNumber())
        .streetLetter(type.getPremisesLetter())
        .zipCode(type.getPostCode())
        .city(type.getTown())
        .build();
  }
}
