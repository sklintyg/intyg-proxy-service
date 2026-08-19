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
package se.inera.intyg.intygproxyservice.integration.common;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.util.List;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.http.HTTPConduit;
import org.springframework.util.ResourceUtils;

@Slf4j
@RequiredArgsConstructor
public class WebServiceClientFactory {

  private final String keyStoreType;
  private final String keyStorePassword;
  private final String keyStorePath;
  private final String trustStorePassword;
  private final String trustStorePath;
  private final String tlsVersion;
  private final List<String> cipherSuites;

  public <T> T create(Class<T> webserviceType, String endpoint) {
    final var jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
    jaxWsProxyFactoryBean.setAddress(endpoint);

    final var webserviceInstance = jaxWsProxyFactoryBean.create(webserviceType);

    final var client = ClientProxy.getClient(webserviceInstance);

    final var conduit = (HTTPConduit) client.getConduit();

    final var params = getTlsClientParameters();
    conduit.setTlsClientParameters(params);

    return webserviceInstance;
  }

  private TLSClientParameters getTlsClientParameters() {
    try {
      final var keyManagers = getKeyManagerFactory().getKeyManagers();
      final var trustManagers = getTrustManagerFactory().getTrustManagers();

      final var sslContext = SSLContext.getInstance(tlsVersion);
      sslContext.init(keyManagers, trustManagers, null);

      final var params = new TLSClientParameters();
      params.setSSLSocketFactory(sslContext.getSocketFactory());
      params.setCipherSuites(cipherSuites);
      return params;
    } catch (Exception ex) {
      log.error("Could not initialize sslContext!", ex);
      throw new IllegalStateException("Could not initialize sslContext!", ex);
    }
  }

  private KeyManagerFactory getKeyManagerFactory() {
    try {
      final var keyManagerFactory =
          KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());

      final var keyStore = KeyStore.getInstance(keyStoreType);
      keyStore.load(
          new FileInputStream(ResourceUtils.getFile(keyStorePath)), keyStorePassword.toCharArray());

      keyManagerFactory.init(keyStore, keyStorePassword.toCharArray());

      return keyManagerFactory;
    } catch (Exception ex) {
      log.error("Could not initialize keystore!", ex);
      throw new IllegalStateException("Could not initialize keystore!", ex);
    }
  }

  private TrustManagerFactory getTrustManagerFactory() {
    try {
      final var trustManagerFactory =
          TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());

      final var trustStore = KeyStore.getInstance(keyStoreType);
      trustStore.load(
          new FileInputStream((ResourceUtils.getFile(trustStorePath))),
          trustStorePassword.toCharArray());

      trustManagerFactory.init(trustStore);

      return trustManagerFactory;
    } catch (Exception ex) {
      log.error("Could not initialize truststore!", ex);
      throw new IllegalStateException("Could not initialize truststore!", ex);
    }
  }
}
