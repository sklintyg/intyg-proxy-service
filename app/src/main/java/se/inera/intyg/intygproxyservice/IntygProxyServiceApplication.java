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
package se.inera.intyg.intygproxyservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigurationExcludeFilter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;

@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(
    basePackages = "se.inera.intyg.intygproxyservice",
    excludeFilters = {
        @Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
        @Filter(type = FilterType.CUSTOM, classes = AutoConfigurationExcludeFilter.class),
        @Filter(
            type = FilterType.REGEX,
            pattern = {
                "se\\.inera\\.intyg\\.intygproxyservice\\.integrationv2\\..*",
                "se\\.inera\\.intyg\\.intygproxyservice\\.integration\\.configuration\\..*",
                "se\\.inera\\.intyg\\.intygproxyservice\\.integration\\.employee\\..*",
                "se\\.inera\\.intyg\\.intygproxyservice\\.integration\\.organization\\..*",
                "se\\.inera\\.intyg\\.intygproxyservice\\.integration\\.authorization\\..*"
            }
        )
    }
)
public class IntygProxyServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(IntygProxyServiceApplication.class, args);
  }
}
