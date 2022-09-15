package com.suavo.fxpricinggrpc

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FxPricingGrpcApplication

fun main(args: Array<String>) {
	runApplication<FxPricingGrpcApplication>(*args)
}