package it.wlp.api.ibolletta

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.openfeign.EnableFeignClients
import springfox.documentation.swagger2.annotations.EnableSwagger2

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = ["it.wlp.api.ibolletta.proxies"])
class ApiRestApplication

fun main(args: Array<String>) {
	runApplication<ApiRestApplication>(*args)
}
