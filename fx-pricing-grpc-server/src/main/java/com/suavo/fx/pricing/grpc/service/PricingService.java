package com.suavo.fx.pricing.grpc.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;

import javax.annotation.PostConstruct;

import org.springframework.scheduling.TaskScheduler;

import com.suavo.fx.client.api.RatesApi;
import com.suavo.fx.pricing.grpc.protos.GetPriceRequest;
import com.suavo.fx.pricing.grpc.protos.Price;
import com.suavo.fx.pricing.grpc.protos.PricingServiceGrpc.PricingServiceImplBase;

import io.grpc.stub.ServerCallStreamObserver;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
@Slf4j
public class PricingService extends PricingServiceImplBase {

  public static final int MAX_RATE = 100;

  private final TaskScheduler priceTaskScheduler;
  private final RatesApi ratesApi;

  @PostConstruct
  public void init() {
    var resp = ratesApi.getLatestRates(null, null, null, null);
    // TODO: load and cache rates for all symbols and stream to all subscribers
  }

  @Override
  public void getPrice(GetPriceRequest request, StreamObserver<Price> responseObserver) {
    var future = priceTaskScheduler.scheduleAtFixedRate(() -> responseObserver.onNext(Price.newBuilder()
        .setSpotRate(BigDecimal.valueOf(Math.random() * MAX_RATE).setScale(2, RoundingMode.HALF_UP).doubleValue())
        .build()), Duration.ofSeconds(1));
    var priceResponseObserver = (ServerCallStreamObserver<Price>) responseObserver;
    priceResponseObserver.setOnCancelHandler(() -> future.cancel(true));
    priceResponseObserver.setOnCloseHandler(() -> future.cancel(true));
  }
}
