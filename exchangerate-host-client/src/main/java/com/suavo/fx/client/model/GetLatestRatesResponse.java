package com.suavo.fx.client.model;

import java.io.Serializable;
import java.util.Map;

import lombok.Data;

@Data
public class GetLatestRatesResponse implements Serializable {

  private static final long serialVersionUID = 1L;

  private String base;
  private String date;
  private Map<String, Double> rates;
}
