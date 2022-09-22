package com.suavo.fx.client.model;

import java.util.Map;

import lombok.Data;

@Data
public class GetSymbolsResponse {

  private Map<String, SymbolData> symbols;
}
