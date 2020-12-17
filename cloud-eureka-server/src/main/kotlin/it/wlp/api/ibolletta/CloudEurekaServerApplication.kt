package it.wlp.api.ibolletta

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer

@SpringBootApplication
@EnableEurekaServer
class CloudEurekaServerApplication

fun main(args: Array<String>) {
	runApplication<CloudEurekaServerApplication>(*args)
}
