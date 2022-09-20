package com.suavo.fx.pricing.grpc.web;

import org.springframework.stereotype.Controller;

@Controller("/proto")
public class ProtoController {

  public String getProto() {
    return "redirect:application.proto";
  }

}
