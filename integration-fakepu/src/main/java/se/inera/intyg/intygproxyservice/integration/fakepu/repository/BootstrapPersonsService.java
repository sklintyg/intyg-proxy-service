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
package se.inera.intyg.intygproxyservice.integration.fakepu.repository;

import static se.inera.intyg.intygproxyservice.integration.api.constants.PuConstants.FAKE_PU_PROFILE;
import static se.inera.intyg.intygproxyservice.integration.fakepu.repository.PersonConverter.convert;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;
import se.inera.intyg.intygproxyservice.integration.fakepu.repository.model.ParsedPerson;
import tools.jackson.databind.json.JsonMapper;

@Service
@Slf4j
@AllArgsConstructor
@Profile(FAKE_PU_PROFILE)
public class BootstrapPersonsService {

  public static final String LOCATION_PATTERN = "bootstrap-persons/*.json";
  private final FakePuRepository fakePuRepository;
  private final JsonMapper jsonMapper;

  @PostConstruct
  public void bootstrapPersoner() {
    final var files = getResourceListing();
    log.info("Bootstrapping {} personer for PU stub ...", files.size());
    for (Resource res : files) {
      try {
        addPerson(res);
      } catch (Exception e) {
        log.error("Could not add person!", e);
      }
    }
  }

  private List<Resource> getResourceListing() {
    try {
      final var r = new PathMatchingResourcePatternResolver();
      return Arrays.asList(r.getResources(LOCATION_PATTERN));
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }

  private void addPerson(Resource res) throws IOException {
    log.debug("Loading person from " + res.getFilename());

    final var parsedPerson = jsonMapper.readValue(res.getInputStream(), ParsedPerson.class);
    fakePuRepository.addPerson(convert(parsedPerson));

    log.debug("Loaded person " + parsedPerson.getPersonalIdentity().getExtension());
  }
}
