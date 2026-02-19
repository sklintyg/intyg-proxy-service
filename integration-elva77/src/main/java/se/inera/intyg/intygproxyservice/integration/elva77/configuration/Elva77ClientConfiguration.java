package se.inera.intyg.intygproxyservice.integration.elva77.configuration;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import se.inera.intyg.intygproxyservice.integration.common.WebServiceClientFactory;
import se.mkv.itintegration.getuserprofile.v2.GetUserProfileResponderInterface;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class Elva77ClientConfiguration {

  private final WebServiceClientFactory webServiceClientFactory;

  @Value("${integration.elva77.getuserprofile.endpoint}")
  private String getUserProfileEndpoint;

  @Bean
  public GetUserProfileResponderInterface getUserProfileResponderInterface() {
    return webServiceClientFactory.create(
        GetUserProfileResponderInterface.class,
        getUserProfileEndpoint
    );
  }
}