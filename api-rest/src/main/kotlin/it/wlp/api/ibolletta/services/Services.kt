package it.wlp.api.ibolletta.services

import it.wlp.api.ibolletta.configs.ConfigMail
import it.wlp.api.ibolletta.configs.ConfigPermissions
import it.wlp.api.ibolletta.configs.ConfigProject
import it.wlp.api.ibolletta.entities.Users
import it.wlp.api.ibolletta.entities.Profiles
import it.wlp.api.ibolletta.models.UserModel
import it.wlp.api.ibolletta.repositories.UserRepository
import it.wlp.api.ibolletta.repositories.UserprofileRepository
import it.wlp.api.ibolletta.utils.UtilCrypt
import org.apache.commons.lang3.ObjectUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.sql.Timestamp
import java.time.Instant

@Service
class UserDetailsServiceProvider : UserDetailsService {

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var configPermissions: ConfigPermissions


    override fun loadUserByUsername(username: String?): UserDetails
    {
        var builder : User.UserBuilder? = null
        userRepository.findByUsername(username!!).ifPresent({

            if (it.active == 0 && !ObjectUtils.notEqual(it.enddate,null))
                return@ifPresent

            builder = User.withUsername(it.username);
            builder!!.password(UtilCrypt.crypto.encode(it.password));
            builder!!.authorities(configPermissions.getGrantedAuthority(username!!))

        })

        return builder!!.build()
    }

}

@Service
class UserServiceProvider{

    @Autowired
    lateinit var configMail: ConfigMail

    @Autowired
    lateinit var configProject: ConfigProject

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var userProfileRepository: UserprofileRepository

    @Autowired
    lateinit var emailSender: JavaMailSender

    @Autowired
    lateinit var userprofileRepository: UserprofileRepository

    fun registerUser(userModel: UserModel) : Boolean{

        var doInsert = true
        userRepository.findByUsername(userModel.email).ifPresent{doInsert = false}

        if(doInsert) {

            var newId = 1
            userRepository.findTopByOrderByIdDesc().ifPresent { newId = it.id.plus(1) }

            if (userModel.email.isNullOrEmpty() || userModel.password.isNullOrEmpty() || userModel.username.isNullOrEmpty()) return false

            if (!ObjectUtils.notEqual(userRepository.save(Users(newId, userModel.email, userModel.email, userModel.password, 0, Timestamp(Instant.now().toEpochMilli()), null)), null)) return false


            var newIdProfile = 1
            userprofileRepository.findTopByOrderByIdDesc().ifPresent { newIdProfile = it.id.plus(1) }



            if (!ObjectUtils.notEqual(userprofileRepository.save(Profiles(newIdProfile, userModel.username, userModel.email, configProject.avatar, configProject.color, 0, Timestamp(Instant.now().toEpochMilli()), null)), null)) return false
        }

        val message = SimpleMailMessage();

        message.setFrom("utubed.service@gmail.com");
        message.setTo(userModel.email);
        message.setSubject( configMail.subject +
                        "${userModel.username}");

        message.setText(configMail.text1 +
                        userModel.username +
                        configMail.text2 +
                        configMail.text3 +
                        configMail.text4 +
                        userModel.email)

        try {emailSender.send(message)}catch (e: Exception){println(e.message)
            return false}

        return true

    }


    fun confirUser(email: String) : Boolean {

        try {
            var userEntity = userRepository.findByUsername(email).orElseThrow { RuntimeException() }
            userEntity.active = 1
            if (!ObjectUtils.notEqual(userRepository.save(userEntity),null)) RuntimeException()

            var userProfileEntity = userProfileRepository.findByEmail(email).orElseThrow { RuntimeException() }
            userProfileEntity.active = 1
            if (!ObjectUtils.notEqual(userProfileRepository.save(userProfileEntity),null)) RuntimeException()

            return true;

        } catch (e: Exception) {return false;}
    }
}