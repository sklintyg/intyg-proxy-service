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
package se.inera.intyg.intygproxyservice.integration.elva77.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.intygproxyservice.integration.api.elva77.Elva77Request;
import se.inera.intyg.intygproxyservice.integration.api.elva77.Elva77Response;
import se.inera.intyg.intygproxyservice.integration.api.elva77.Result;
import se.inera.intyg.intygproxyservice.integration.elva77.UserProfileResponseTypeHandler;
import se.mkv.itintegration.getuserprofile.v2.GetUserProfileResponderInterface;
import se.mkv.itintegration.getuserprofileresponder.v2.GetUserProfileResponseType;
import se.mkv.itintegration.getuserprofileresponder.v2.GetUserProfileType;

@ExtendWith(MockitoExtension.class)
class Elva77ClientTest {

  private static final String PERSON_ID = "personId";
  private static final Elva77Request REQUEST = Elva77Request.builder().personId(PERSON_ID).build();
  @Mock GetUserProfileResponderInterface getUserProfileResponderInterface;
  @Mock UserProfileResponseTypeHandler userProfileResponseTypeHandler;
  @InjectMocks Elva77Client elva77Client;

  @Test
  void shouldSetPersonIdToSubjectOfCare() {
    final var captor = ArgumentCaptor.forClass(GetUserProfileType.class);

    elva77Client.getUserProfile(REQUEST);

    verify(getUserProfileResponderInterface).getUserProfile(captor.capture());
    assertEquals(PERSON_ID, captor.getValue().getSubjectOfCare());
  }

  @Test
  void shouldReturnElva77Response() {
    final var expectedResponse = Elva77Response.builder().build();
    final var profileResponseType = new GetUserProfileResponseType();

    when(getUserProfileResponderInterface.getUserProfile(any(GetUserProfileType.class)))
        .thenReturn(profileResponseType);
    when(userProfileResponseTypeHandler.handle(profileResponseType)).thenReturn(expectedResponse);

    final var response = elva77Client.getUserProfile(REQUEST);
    assertEquals(expectedResponse, response);
  }

  @Test
  void shouldReturnElva77ResponseWithErrorResultIfExceptionOccurred() {
    final var expectedResponse = Elva77Response.builder().result(Result.ERROR).build();

    when(getUserProfileResponderInterface.getUserProfile(any(GetUserProfileType.class)))
        .thenThrow(IllegalStateException.class);

    final var response = elva77Client.getUserProfile(REQUEST);
    assertEquals(expectedResponse, response);
  }
}
