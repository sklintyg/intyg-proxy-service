package se.inera.intyg.intygproxyservice.integrationv2.organization.client.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import riv.infrastructure.directory.organization._5.StructuredPostalAddressType;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.PostalAddress;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.StructuredPostalAddress;

@Component
@RequiredArgsConstructor
public class StructuredAddressConverter {

  public StructuredPostalAddress convert(StructuredPostalAddressType type) {
    return StructuredPostalAddress.builder()
        .addressee(type.getAddressee().stream()
            .map(PostalAddress::new)
            .toList())
        .street(type.getStreet())
        .premisesNumber(type.getPremisesNumber())
        .premisesLetter(type.getPremisesLetter())
        .postalCode(type.getPostCode())
        .town(type.getTown())
        .build();
  }
}
