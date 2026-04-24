package se.inera.intyg.intygproxyservice.integration.api.organization.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class StructuredVisitingAddress {
  String street;
  String premisesNumber;
  String premisesLetter;
  String town;
}
