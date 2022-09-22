package com.suavo.fx.client.invoker;

import java.time.LocalDateTime;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

@Getter
public class ApiClient {

  private static final String DEFAULT_BASE_URL = "https://api.exchangerate.host/";

  private OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();
  private Retrofit.Builder adapterBuilder;
  private String baseUrl;

  public ApiClient() {
    this(DEFAULT_BASE_URL);
  }

  public ApiClient(@NotBlank String baseUrl) {
    this.baseUrl = baseUrl;
    createDefaultAdapter();
  }

  public void createDefaultAdapter() {

    GsonBuilder gsonBuilder = new GsonBuilder();
    gsonBuilder.registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, t,
        jsonDeserializationContext) -> LocalDateTime.parse(json.getAsJsonPrimitive().getAsString()))
        .create();
    Gson gson = gsonBuilder.create();

    adapterBuilder = new Retrofit.Builder()
        .baseUrl(this.baseUrl)
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson));
  }

  public <S> S createService(Class<S> serviceClass) {
    return adapterBuilder
        .client(okBuilder.build())
        .build()
        .create(serviceClass);
  }

}
