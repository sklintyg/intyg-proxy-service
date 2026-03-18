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
package se.inera.intyg.intygproxyservice.integration.pu.v5;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuPersonsRequest;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuPersonsResponse;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuRequest;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuResponse;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuService;
import se.inera.intyg.intygproxyservice.integration.pu.v5.client.PuClientV5;
import se.inera.intyg.intygproxyservice.integration.pu.v5.common.BatchUtil;

@Service
@Slf4j
@RequiredArgsConstructor
public class PuIntegrationServiceV5 implements PuService {

  private final PuClientV5 puClientV5;

  @Value("${integration.pu.batch.size}")
  private int batchSize;

  @Override
  public PuResponse findPerson(PuRequest puRequest) {
    return puClientV5.findPerson(puRequest);
  }

  @Override
  public PuPersonsResponse findPersons(PuPersonsRequest puRequest) {
    final var batches = BatchUtil.split(puRequest.getPersonIds(), batchSize);
    return PuPersonsResponse.builder()
        .persons(
            batches.stream()
                .map(PuIntegrationServiceV5::batchRequest)
                .map(puClientV5::findPersons)
                .map(PuPersonsResponse::getPersons)
                .flatMap(List::stream)
                .toList())
        .build();
  }

  private static PuPersonsRequest batchRequest(List<String> batch) {
    return PuPersonsRequest.builder().personIds(batch).build();
  }
}
