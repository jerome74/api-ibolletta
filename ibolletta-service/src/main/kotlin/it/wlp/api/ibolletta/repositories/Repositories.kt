package it.wlp.api.ibolletta.repositories

import it.wlp.api.ibolletta.entities.Bolletta
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface BollettaRepository : JpaRepository<Bolletta, Int>