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
package se.inera.intyg.intygproxyservice.integration.fakepu;

import se.inera.intyg.intygproxyservice.integration.fakepu.repository.model.ParsedAddress;
import se.inera.intyg.intygproxyservice.integration.fakepu.repository.model.ParsedAddressInformation;
import se.inera.intyg.intygproxyservice.integration.fakepu.repository.model.ParsedDeregistration;
import se.inera.intyg.intygproxyservice.integration.fakepu.repository.model.ParsedName;
import se.inera.intyg.intygproxyservice.integration.fakepu.repository.model.ParsedNameWrapper;
import se.inera.intyg.intygproxyservice.integration.fakepu.repository.model.ParsedPerson;
import se.inera.intyg.intygproxyservice.integration.fakepu.repository.model.ParsedPersonalIdentity;

public class TestData {

  public static final String PERSON_ID = "191212121212";
  public static final String PERSON_ID_2 = "201212121212";
  public static final String GIVEN_NAME = "Tolvan";
  public static final String MIDDLE_NAME = "TT";
  public static final String SURNAME = "Tolvansson";
  public static final String CARE_OF = "c/o Tretton Trettonson";
  public static final String POSTAL_ADDRESS1 = "Fjortonkvarteret";
  public static final String POSTAL_ADDRESS2 = "Femtonvägen 15";
  public static final String POSTAL_CODE = "121314";
  public static final String CITY = "Tolvsund";
  public static final boolean DECEASED = true;
  public static final boolean PROTECTED_PERSON = true;
  public static final boolean TEST_INDICATED = true;
  public static final String DEREGISTRATION_REASON_CODE_FOR_DECEASED = "AV";
  public static final boolean IS_ACTIVE = true;
  public static final ParsedPerson PARSED_PERSON =
      ParsedPerson.builder()
          .personalIdentity(ParsedPersonalIdentity.builder().extension(PERSON_ID).build())
          .name(
              ParsedNameWrapper.builder()
                  .givenName(ParsedName.builder().name(GIVEN_NAME).build())
                  .middleName(ParsedName.builder().name(MIDDLE_NAME).build())
                  .surname(ParsedName.builder().name(SURNAME).build())
                  .build())
          .addressInformation(
              ParsedAddressInformation.builder()
                  .residentialAddress(
                      ParsedAddress.builder()
                          .postalAddress2(POSTAL_ADDRESS2)
                          .postalCode(POSTAL_CODE)
                          .city(CITY)
                          .build())
                  .build())
          .deregistration(
              ParsedDeregistration.builder()
                  .deregistrationReasonCode(DEREGISTRATION_REASON_CODE_FOR_DECEASED)
                  .build())
          .protectedPersonIndicator(PROTECTED_PERSON)
          .testIndicator(TEST_INDICATED)
          .isActive(IS_ACTIVE)
          .build();
}
