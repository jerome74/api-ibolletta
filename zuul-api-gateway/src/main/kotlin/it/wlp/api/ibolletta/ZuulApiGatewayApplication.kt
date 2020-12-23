package it.wlp.api.ibolletta

import org.slf4j.bridge.SLF4JBridgeHandler
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.netflix.zuul.EnableZuulProxy

@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
class ZuulApiGatewayApplication

fun main(args: Array<String>) {

	SLF4JBridgeHandler.removeHandlersForRootLogger()
	SLF4JBridgeHandler.install()

	runApplication<ZuulApiGatewayApplication>(*args)
}
