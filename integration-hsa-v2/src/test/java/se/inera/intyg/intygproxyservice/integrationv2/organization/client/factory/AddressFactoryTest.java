/*
 * Copyright (C) 2026 Inera AB (http://www.inera.se)
 *
 * This file is part of sklintyg (https://github.com/sklintyg).
 *
 * sklintyg is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * sklintyg is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package se.inera.intyg.intygproxyservice.integrationv2.organization.client.factory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import riv.infrastructure.directory.organization._5.StructuredPostalAddressType;

@ExtendWith(MockitoExtension.class)
class AddressFactoryTest {

  @InjectMocks private AddressFactory addressFactory;

  @Test
  void shouldMapStreet() {
    final var type = mock(StructuredPostalAddressType.class);
    when(type.getStreet()).thenReturn("Storgatan");

    final var result = addressFactory.create(type);

    assertEquals("Storgatan", result.street());
  }

  @Test
  void shouldMapStreetNumber() {
    final var type = mock(StructuredPostalAddressType.class);
    when(type.getPremisesNumber()).thenReturn("12");

    final var result = addressFactory.create(type);

    assertEquals("12", result.streetNumber());
  }

  @Test
  void shouldMapStreetLetter() {
    final var type = mock(StructuredPostalAddressType.class);
    when(type.getPremisesLetter()).thenReturn("B");

    final var result = addressFactory.create(type);

    assertEquals("B", result.streetLetter());
  }

  @Test
  void shouldMapZipCode() {
    final var type = mock(StructuredPostalAddressType.class);
    when(type.getPostCode()).thenReturn("12345");

    final var result = addressFactory.create(type);

    assertEquals("12345", result.zipCode());
  }

  @Test
  void shouldMapCity() {
    final var type = mock(StructuredPostalAddressType.class);
    when(type.getTown()).thenReturn("Stockholm");

    final var result = addressFactory.create(type);

    assertEquals("Stockholm", result.city());
  }

  @Test
  void shouldMapAllFields() {
    final var type = mock(StructuredPostalAddressType.class);
    when(type.getStreet()).thenReturn("Storgatan");
    when(type.getPremisesNumber()).thenReturn("12");
    when(type.getPremisesLetter()).thenReturn("B");
    when(type.getPostCode()).thenReturn("12345");
    when(type.getTown()).thenReturn("Stockholm");

    final var result = addressFactory.create(type);

    assertEquals("Storgatan", result.street());
    assertEquals("12", result.streetNumber());
    assertEquals("B", result.streetLetter());
    assertEquals("12345", result.zipCode());
    assertEquals("Stockholm", result.city());
  }
}
