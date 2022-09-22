package com.suavo.fx.pricing.grpc.config;

import javax.net.ssl.X509TrustManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.suavo.fx.client.api.RatesApi;
import com.suavo.fx.client.invoker.ApiClient;
import com.suavo.fx.pricing.grpc.util.SecurityUtils;

import lombok.extern.slf4j.Slf4j;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;

@Configuration
@Slf4j
public class ExchangeRateConfig {

  @Bean
  public ApiClient ratesApiClient() {
    var apiClient = new ApiClient();

    var loggingInterceptor = new HttpLoggingInterceptor();
    if (log.isTraceEnabled()) {
      loggingInterceptor.setLevel(Level.BODY);
    }

    apiClient.getOkBuilder().addInterceptor(loggingInterceptor);
    apiClient.getOkBuilder().sslSocketFactory(
        SecurityUtils.getTrustySslContext().getSocketFactory(),
        (X509TrustManager) SecurityUtils.trustAllCerts[0]);
    apiClient.getOkBuilder().hostnameVerifier((hostname, session) -> hostname.equalsIgnoreCase(session.getPeerHost()));

    return apiClient;
  }

  @Bean
  @Autowired
  public RatesApi ratesApi(ApiClient ratesApiClient) {
    return ratesApiClient.createService(RatesApi.class);
  }
}
