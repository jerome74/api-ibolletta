package it.wlp.api.ibolletta.repositories

import it.wlp.api.ibolletta.entities.Users
import it.wlp.api.ibolletta.entities.Profiles
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserprofileRepository : JpaRepository<Profiles, Int> {
    fun findByEmail(email: String): Optional<Profiles>
    fun findTopByOrderByIdDesc() : Optional<Profiles>
}

@Repository
interface UserRepository : JpaRepository<Users, Int> {

    fun findByUsername(username: String): Optional<Users>
    fun findTopByOrderByIdDesc() : Optional<Users>
}