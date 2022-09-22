package com.suavo.fx.client.api;

import java.util.List;

import com.suavo.fx.client.model.GetLatestRatesResponse;
import com.suavo.fx.client.model.GetSymbolsResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RatesApi {

  @GET("latest")
  Observable<GetLatestRatesResponse> getLatestRates(
      @Query("base") String base, @Query("symbols") List<String> symbols,
      @Query("amount") Integer amount, @Query("places") Integer places);

  @GET("symbols")
  Observable<GetSymbolsResponse> getSymbols();
}
