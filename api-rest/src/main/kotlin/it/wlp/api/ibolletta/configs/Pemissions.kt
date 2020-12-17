package it.wlp.api.ibolletta.configs

import it.wlp.api.ibolletta.utils.AuthObj
import org.json.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.PropertySource
import org.springframework.core.env.Environment
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import java.security.MessageDigest


@Component
@PropertySource("classpath:application.yml")
class ConfigProperties
{
    @Autowired
    lateinit var env : Environment

    fun getPropertes(prop : String) : String? = env.getProperty(prop)
}

@Component
@ConfigurationProperties(prefix = "authentication")
class ConfigSecret{

    var authsecretid = ""
    var expiretime = ""

    fun getAuthSecret() : ByteArray {
        if(AuthObj.sha256 == null)
            return MessageDigest.getInstance("SHA-256").digest(authsecretid.toByteArray(Charsets.UTF_8))

        return AuthObj.sha256!!
    }


}

@Component
@ConfigurationProperties(prefix = "project")
class ConfigProject{
    var signin = ""
    var confirm = ""
    var avatar = ""
    var h2 = ""
    var color = ""
    var from = ""
    var apidoc = ""
    var swaggerui = ""
}

@Component
@ConfigurationProperties(prefix = "mail")
class ConfigMail{
    var subject = ""
    var text1 = ""
    var text2 = ""
    var text3 = ""
    var text4 = ""
    var ok = ""
}

@ConfigurationProperties(prefix = "permission.authorities")
@Component
class ConfigPermissions {

    var useradmin = ""
    var admin = ""
    var user = ""
    var access = ""

    fun getGrantedAuthority(permission: String): Set<SimpleGrantedAuthority> {
        val permissions = mutableListOf<SimpleGrantedAuthority>()

        var role: String

        when (permission) {
            useradmin -> role = admin.substring(1, admin.length - 1).replace("\\", "")
            else -> role = user.substring(1, user.length - 1).replace("\\", "")
        }

        val responseJson = JSONObject("$role!!")

        val name = responseJson.getString("name")
        val actions = responseJson.getJSONObject("actions")

        permissions.add(SimpleGrantedAuthority("ROLE_$name"))


        val read = actions.getString("read")
        if (!read.isNullOrBlank()) permissions.add(SimpleGrantedAuthority(read))

        val write = actions.getString("write")
        if (!write.isNullOrBlank()) permissions.add(SimpleGrantedAuthority(write))

        val delete = actions.getString("delete")
        if (!delete.isNullOrBlank()) permissions.add(SimpleGrantedAuthority(delete))

        return permissions.toSet()
    }
}