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

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

class BatchUtilTest {

  @Test
  void shouldSplitUpIntoBatchWithOneItemEachIfSizeIs1() {
    final var response = BatchUtil.split(List.of("s1", "s2", "s3"), 1);
    assertAll(
        () -> assertEquals(3, response.size()),
        () -> assertEquals("s1", response.getFirst().getFirst()),
        () -> assertEquals("s2", response.get(1).getFirst()),
        () -> assertEquals("s3", response.get(2).getFirst()));
  }

  @Test
  void shouldSplitUpIntoBatchWithAllItemsIfBatchSizeIsBiggerThanListSize() {
    final var response = BatchUtil.split(List.of("s1", "s2", "s3"), 5);
    assertAll(
        () -> assertEquals(1, response.size()),
        () -> assertEquals("s1", response.getFirst().getFirst()),
        () -> assertEquals("s2", response.getFirst().get(1)),
        () -> assertEquals("s3", response.getFirst().get(2)));
  }
}
