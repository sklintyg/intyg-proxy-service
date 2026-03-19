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
package se.inera.intyg.intygproxyservice.integration.elva77;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.intygproxyservice.integration.api.elva77.Elva77Response;
import se.inera.intyg.intygproxyservice.integration.api.elva77.Result;
import se.inera.intyg.intygproxyservice.integration.elva77.converter.UserProfileResponseTypeConverter;
import se.mkv.itintegration.getuserprofileresponder.v2.GetUserProfileResponseType;
import se.mkv.itintegration.userprofile.v2.UserProfileType;

@ExtendWith(MockitoExtension.class)
class UserProfileResponseTypeHandlerTest {

  @Mock UserProfileResponseTypeConverter userProfileResponseTypeConverter;
  @InjectMocks UserProfileResponseTypeHandler userProfileResponseTypeHandler;

  @Test
  void shallReturnErrorResponseIfIsUserProfileIsNull() {
    final var responseType = new GetUserProfileResponseType();
    responseType.setIsActive(true);
    responseType.setUserProfile(null);

    final var response = userProfileResponseTypeHandler.handle(responseType);
    assertEquals(Result.ERROR, response.getResult());
  }

  @Test
  void shouldReturnConvertedElva77Response() {
    final var expectedResponse = Elva77Response.builder().build();

    final var responseType = new GetUserProfileResponseType();
    responseType.setIsActive(true);
    responseType.setUserProfile(new UserProfileType());

    when(userProfileResponseTypeConverter.convert(responseType)).thenReturn(expectedResponse);

    final var response = userProfileResponseTypeHandler.handle(responseType);
    assertEquals(expectedResponse, response);
  }
}
