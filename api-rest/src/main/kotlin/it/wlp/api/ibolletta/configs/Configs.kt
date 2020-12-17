package it.wlp.api.ibolletta.configs

import com.netflix.client.config.IClientConfig
import com.netflix.loadbalancer.IPing
import com.netflix.loadbalancer.IRule
import com.netflix.loadbalancer.PingUrl
import com.netflix.loadbalancer.WeightedResponseTimeRule
import it.wlp.api.ibolletta.auth.JwtTokenVerifier
import it.wlp.api.ibolletta.auth.JwtUserAndPasswordAuthenticationFilter
import it.wlp.api.ibolletta.services.UserDetailsServiceProvider
import it.wlp.api.ibolletta.utils.AuthObj
import it.wlp.api.ibolletta.utils.UtilCrypt
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.xmlpull.v1.XmlPullParserException
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2
import java.io.IOException
import kotlin.jvm.Throws

@Configuration
@EnableWebSecurity
@EnableSwagger2
class WebConfig : WebSecurityConfigurerAdapter() {
    @Autowired
    lateinit var service: UserDetailsServiceProvider

    @Autowired
    lateinit var jwtTokenVerifier: JwtTokenVerifier

    @Autowired
    lateinit var configSecret: ConfigSecret

    @Autowired
    lateinit var configPermissions: ConfigPermissions

    override fun configure(http: HttpSecurity) {

        http
                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(JwtUserAndPasswordAuthenticationFilter(authenticationManager()))
                .addFilterAfter(jwtTokenVerifier, JwtUserAndPasswordAuthenticationFilter::class.java)
                .authorizeRequests()
                .antMatchers("/api-rest/**").permitAll()
                .antMatchers("/api-rest/**").hasRole(configPermissions.access)
                .anyRequest()
                .authenticated()

        AuthObj.configSecret = configSecret
    }

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth!!.userDetailsService(service).passwordEncoder(UtilCrypt.crypto)
    }

    @Bean
    public fun api(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("it.wlp.api.ibolletta.proxies"))
                .paths(PathSelectors.regex("/api-rest.*"))
                .build();
    }


}