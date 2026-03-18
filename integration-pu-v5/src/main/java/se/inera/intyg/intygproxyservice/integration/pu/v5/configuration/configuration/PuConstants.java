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
package se.inera.intyg.intygproxyservice.integration.pu.v5.configuration.configuration;

public abstract class PuConstants {

  private PuConstants() {
    throw new IllegalStateException("Class to keep constants");
  }

  public static final String CODE_PERSONAL_ID = "1.2.752.129.2.1.3.1";
  public static final String CODE_COORDINATION_NUMBER = "1.2.752.129.2.1.3.3";
  public static final int COORDINATION_NUMBER_MONTH_INDEX = 6;
  public static final int COORDINATION_NUMBER_MONTH_VALUE_MIN = 6;
  public static final String DEREGISTRATION_REASON_CODE_FOR_DECEASED = "AV";
}
