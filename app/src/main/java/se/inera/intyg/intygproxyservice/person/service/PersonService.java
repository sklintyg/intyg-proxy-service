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
package se.inera.intyg.intygproxyservice.person.service;

import static se.inera.intyg.intygproxyservice.config.RedisConfig.PERSON_CACHE;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import se.inera.intyg.intygproxyservice.common.CacheUtility;
import se.inera.intyg.intygproxyservice.common.HashUtility;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuRequest;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuResponse;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuService;
import se.inera.intyg.intygproxyservice.integration.api.pu.Status;
import se.inera.intyg.intygproxyservice.person.dto.PersonRequest;
import se.inera.intyg.intygproxyservice.person.dto.PersonResponse;
import tools.jackson.databind.json.JsonMapper;

@Service
@RequiredArgsConstructor
public class PersonService {

  private final PuService puService;
  private final PersonDTOMapper personDTOMapper;
  private final CacheManager cacheManager;
  private final JsonMapper jsonMapper;

  public PersonResponse findPerson(PersonRequest personRequest) {
    validateRequest(personRequest);

    final var responseFromCache = getPersonFromCache(personRequest.getPersonId());

    if (responseFromCache.isPresent()) {
      return PuResponseConverter.convert(personDTOMapper, responseFromCache.get());
    }

    final var puResponse = findPersonInPu(personRequest);

    if (puResponse.status().equals(Status.FOUND)) {
      savePersonInCache(puResponse);
    }

    return PuResponseConverter.convert(personDTOMapper, puResponse);
  }

  private static void validateRequest(PersonRequest personRequest) {
    if (personRequest == null) {
      throw new IllegalArgumentException("PersonRequest is null");
    }

    if (personRequest.getPersonId() == null || personRequest.getPersonId().isBlank()) {
      throw new IllegalArgumentException(
          String.format("PersonId is not valid: '%s'", personRequest.getPersonId()));
    }
  }

  private PuResponse findPersonInPu(PersonRequest personRequest) {
    return puService.findPerson(PuRequest.builder().personId(personRequest.getPersonId()).build());
  }

  private void savePersonInCache(PuResponse puResponse) {
    CacheUtility.save(
        cacheManager,
        jsonMapper,
        puResponse,
        HashUtility.hash(puResponse.person().getPersonnummer().id()),
        PERSON_CACHE);
  }

  private Optional<PuResponse> getPersonFromCache(String id) {
    return CacheUtility.get(cacheManager, jsonMapper, HashUtility.hash(id), PuResponse.class);
  }
}
