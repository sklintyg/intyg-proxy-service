package se.inera.intyg.intygproxyservice.integration.api.organization.model;

import java.util.Collections;
import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class StructuredPostalAddress {
  @Builder.Default List<PostalAddress> addressee = Collections.emptyList();
  String street;
  String premisesNumber;
  String premisesLetter;
  String postalCode;
  String town;
}
