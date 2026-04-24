package se.inera.intyg.intygproxyservice.integrationv2.organization.client.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import riv.infrastructure.directory.organization._5.StructuredPostalAddressType;
import riv.infrastructure.directory.organization._5.StructuredVisitingAddressType;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.PostalAddress;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.StructuredPostalAddress;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.StructuredVisitingAddress;

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

  public StructuredVisitingAddress convert(StructuredVisitingAddressType type) {
    return StructuredVisitingAddress.builder()
        .street(type.getStreet())
        .premisesNumber(type.getPremisesNumber())
        .premisesLetter(type.getPremisesLetter())
        .town(type.getTown())
        .build();
  }
}
