package se.inera.intyg.intygproxyservice.organization.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.Address;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.Unit;
import se.inera.intyg.intygproxyservice.organization.dto.UnitDTO;

@ExtendWith(MockitoExtension.class)
class UnitConverterTest {

  @InjectMocks private UnitConverter unitConverter;

  @Test
  void shouldConvertUnit() {
    final var expectedUnit = getUnitDTO();

    final var unit = getUnit();

    final var actual = unitConverter.convert(unit);

    assertEquals(expectedUnit, actual);
  }

  @Test
  void shouldReturnNullIfUnitIsNull() {
    assertNull(unitConverter.convert(null));
  }

  private static Unit getUnit() {
    return Unit.builder()
        .unitHsaId("test")
        .unitStartDate(LocalDateTime.MIN)
        .unitEndDate(LocalDateTime.MAX)
        .feignedUnit(false)
        .unitName("name")
        .telephoneNumber(List.of("12345"))
        .address(new Address("street", "123", "A", "123456", "city"))
        .mail("mail")
        .build();
  }

  private static UnitDTO getUnitDTO() {
    return UnitDTO.builder()
        .unitHsaId("test")
        .unitStartDate(LocalDateTime.MIN)
        .unitEndDate(LocalDateTime.MAX)
        .feignedUnit(false)
        .unitName("name")
        .telephoneNumber(List.of("12345"))
        .address(new Address("street", "123", "A", "123456", "city"))
        .mail("mail")
        .build();
  }
}
