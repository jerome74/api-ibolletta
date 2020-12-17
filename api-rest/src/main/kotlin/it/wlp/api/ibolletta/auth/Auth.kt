package it.wlp.api.ibolletta.auth

import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import it.wlp.api.ibolletta.configs.ConfigPermissions
import it.wlp.api.ibolletta.configs.ConfigProject
import it.wlp.api.ibolletta.configs.ConfigSecret
import it.wlp.api.ibolletta.models.CredentialModel
import it.wlp.api.ibolletta.repositories.UserRepository
import it.wlp.api.ibolletta.utils.AuthObj
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.lang.IllegalStateException
import java.util.*
import java.util.stream.Collectors
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtUserAndPasswordAuthenticationFilter(val authent: AuthenticationManager) : UsernamePasswordAuthenticationFilter() {


    override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication {

        val authenticationRequest = ObjectMapper().readValue<CredentialModel>(request!!.inputStream, CredentialModel::class.java)
        val authentication = UsernamePasswordAuthenticationToken(authenticationRequest.username, authenticationRequest.password)

        val auth = authent.authenticate(authentication)

        return auth
    }


    override fun successfulAuthentication(request: HttpServletRequest?, response: HttpServletResponse?, chain: FilterChain?, authResult: Authentication?) {


        val cal = Calendar.getInstance()
        cal.add(Calendar.MINUTE, AuthObj.configSecret!!.expiretime.toInt())
        val to30min = cal.time


        val jwttoken = Jwts.builder()
                .setSubject(authResult!!.name)
                .claim("authorities", authResult!!.authorities)
                .setIssuedAt(Date())
                .setExpiration(to30min)
                .signWith(Keys.hmacShaKeyFor(AuthObj.configSecret!!.getAuthSecret()))
                .compact()

        response!!.addHeader("Authentication", "Bearer $jwttoken")

    }

}

@Component
class JwtTokenVerifier : OncePerRequestFilter()
{

    @Autowired
    lateinit var configSecret: ConfigSecret

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var configProject: ConfigProject

    @Autowired
    lateinit var configPermissions: ConfigPermissions

    @Throws(IllegalStateException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {


        val authoritation = request.getHeader("Authentication")

        if(request.requestURI.contains(configProject.signin)
                || request.requestURI.contains(configProject.confirm)
                || request.requestURI.contains(configProject.h2)
                || request.requestURI.contains(configProject.apidoc)
                || request.requestURI.contains(configProject.swaggerui)
        ){

            userRepository.findByUsername(configPermissions.useradmin).ifPresent{

                val authenticationAuth = UsernamePasswordAuthenticationToken(it.username,it.password,configPermissions.getGrantedAuthority(configPermissions.useradmin))
                SecurityContextHolder.getContext().authentication = authenticationAuth
                filterChain.doFilter(request,response)
            }
        }
        else if(authoritation.isNullOrBlank() || !authoritation.startsWith("Bearer")){
            filterChain.doFilter(request,response)
            return
        }
        else{
            val token = authoritation.replace("Bearer " , "")
            val claimJwts = Jwts.parser()
                    .setSigningKey(Keys.hmacShaKeyFor(configSecret.getAuthSecret()))
                    .parseClaimsJws(token)

            val username = claimJwts.body.subject
            val authorities = claimJwts.body.get("authorities") as List<Map<String, String>>

            val simpleGrantedAuthority = authorities.stream().map { SimpleGrantedAuthority(it.get("authority")) }.collect(Collectors.toSet())
            val authenticationAuth = UsernamePasswordAuthenticationToken(username,null,simpleGrantedAuthority)

            SecurityContextHolder.getContext().authentication = authenticationAuth

            filterChain.doFilter(request,response)

        }
    }
}