package se.inera.intyg.intygproxyservice.organization.service;

import org.springframework.stereotype.Component;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.Unit;
import se.inera.intyg.intygproxyservice.organization.dto.UnitDTO;

@Component
public class UnitConverter {

  public UnitDTO convert(Unit unit) {
    if (unit == null) {
      return null;
    }

    return UnitDTO.builder()
        .unitStartDate(unit.getUnitStartDate())
        .unitEndDate(unit.getUnitEndDate())
        .feignedUnit(unit.getFeignedUnit())
        .unitHsaId(unit.getUnitHsaId())
        .unitName(unit.getUnitName())
        .telephoneNumber(unit.getTelephoneNumber())
        .address(unit.getAddress())
        .mail(unit.getMail())
        .build();
  }
}
