package it.wlp.api.ibolletta.configs

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
class Configs {
    @Bean
    public fun api() : Docket {
        return Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("it.wlp.api.ibolletta.controllers"))
                .paths(PathSelectors.regex("/ibolletta-service.*"))
                .build();
    }
}

@Component
@PropertySource("classpath:bootstrap.yml")
class ConfigProperties
{
    @Autowired
    lateinit var env : Environment

    fun getPropertes(prop : String) : String? = env.getProperty(prop)
}