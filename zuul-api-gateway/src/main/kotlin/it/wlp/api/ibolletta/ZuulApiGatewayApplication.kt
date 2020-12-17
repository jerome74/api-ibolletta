package it.wlp.api.ibolletta

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.netflix.zuul.EnableZuulProxy

@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
class ZuulApiGatewayApplication

fun main(args: Array<String>) {
	runApplication<ZuulApiGatewayApplication>(*args)
}
