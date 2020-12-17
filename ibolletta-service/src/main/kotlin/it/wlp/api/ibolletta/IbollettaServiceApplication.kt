package it.wlp.api.ibolletta

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableDiscoveryClient
class IbollettaServiceApplication

fun main(args: Array<String>) {
	runApplication<IbollettaServiceApplication>(*args)
}
