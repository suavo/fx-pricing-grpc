package com.suavo.fx.pricing.grpc.util;

import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityUtils {

  @SneakyThrows
  public static SSLContext getTrustySslContext() {
    SSLContext sslContext = SSLContext.getInstance("SSL");
    sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
    return sslContext;
  }

  public static final TrustManager[] trustAllCerts = new TrustManager[] {
      new X509TrustManager() {
        @Override
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
          return new X509Certificate[0];
        }

        @Override
        public void checkClientTrusted(
            java.security.cert.X509Certificate[] certs, String authType) {
          // empty
        }

        @Override
        public void checkServerTrusted(
            java.security.cert.X509Certificate[] certs, String authType) {
          // empty
        }
      }
  };
}
