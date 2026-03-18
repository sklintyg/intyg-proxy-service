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

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import se.inera.intyg.intygproxyservice.integration.api.elva77.Elva77Response;
import se.inera.intyg.intygproxyservice.integration.elva77.converter.UserProfileResponseTypeConverter;
import se.mkv.itintegration.getuserprofileresponder.v2.GetUserProfileResponseType;
import se.mkv.itintegration.userprofile.v2.UserProfileType;

@Component
@RequiredArgsConstructor
public class UserProfileResponseTypeHandler {

  private final UserProfileResponseTypeConverter userProfileResponseTypeConverter;

  public Elva77Response handle(GetUserProfileResponseType responseType) {
    final var userProfile = responseType.getUserProfile();

    if (userNotFound(userProfile)) {
      return Elva77Response.error();
    }

    return userProfileResponseTypeConverter.convert(responseType);
  }

  private static boolean userNotFound(UserProfileType userProfile) {
    return userProfile == null;
  }
}
