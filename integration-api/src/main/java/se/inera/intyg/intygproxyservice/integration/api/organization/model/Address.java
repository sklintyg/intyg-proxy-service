package se.inera.intyg.intygproxyservice.integration.api.organization.model;

import lombok.Builder;

@Builder
public record Address(String street, String streetNumber,
                      String streetLetter, String zipCode, String city) {

}
