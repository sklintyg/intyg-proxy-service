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
package se.inera.intyg.intygproxyservice.integration.pu.v5.common;

import java.util.List;
import java.util.stream.Collectors;

public class BatchUtil {

  private BatchUtil() {
    throw new IllegalStateException("Utility class");
  }

  public static <T> List<List<T>> split(List<T> list, int batchSize) {
    return list.stream()
        .collect(Collectors.groupingBy(object -> (list.indexOf(object)) / batchSize))
        .values()
        .stream()
        .toList();
  }
}
