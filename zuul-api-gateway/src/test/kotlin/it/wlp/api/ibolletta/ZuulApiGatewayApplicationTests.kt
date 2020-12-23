package it.wlp.api.ibolletta

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.netflix.zuul.EnableZuulProxy

@SpringBootTest
@EnableZuulProxy
class ZuulApiGatewayApplicationTests {

	@Test
	fun contextLoads() {
	}

}
