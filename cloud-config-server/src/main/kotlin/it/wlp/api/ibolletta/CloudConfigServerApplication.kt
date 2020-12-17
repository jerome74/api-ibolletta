package it.wlp.api.ibolletta

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.config.server.EnableConfigServer

@SpringBootApplication
@EnableConfigServer
class CloudConfigServerApplication

fun main(args: Array<String>) {
	runApplication<CloudConfigServerApplication>(*args)
}
