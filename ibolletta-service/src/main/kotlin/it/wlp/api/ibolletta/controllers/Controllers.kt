package it.wlp.api.ibolletta.controllers

import it.wlp.api.ibolletta.entities.Bolletta
import it.wlp.api.ibolletta.dtos.*
import it.wlp.api.ibolletta.repositories.BollettaRepository
import org.apache.commons.lang.ObjectUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import kotlin.streams.toList

@RestController
@RequestMapping("/ibolletta")
class IBollettaController {

    @Autowired
    lateinit var bollettaRepository: BollettaRepository

    @PostMapping(path = ["/save"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun saveBolletta(@RequestBody bolletta: BollettaDTO): ResponseEntity<HttpStatus> {
        if (ObjectUtils.equals(bollettaRepository.save(Bolletta(bolletta.id,
                        bolletta.email
                        ,bolletta.cc
                        ,bolletta.importo
                        ,bolletta.scadenza
                        ,bolletta.numero
                        ,bolletta.owner
                        ,bolletta.td)), null))
            return ResponseEntity(HttpStatus.NOT_ACCEPTABLE)
        else
            return ResponseEntity(HttpStatus.OK)
    }

    @GetMapping(path = ["/findall/{email}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAllBollettas(@PathVariable email: String): List<BollettaDTO> {
        return bollettaRepository.findAll().stream().filter { it.email.equals(email) }.map { BollettaDTO(
                it.id,
                it.email
                ,it.cc
                ,it.importo
                ,it.scadenza
                ,it.numero
                ,it.owner
                ,it.td
        ) }.toList()
    }

    @GetMapping(path = ["/delete/{id}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun deleteBolletta(@PathVariable id: Int): ResponseEntity<HttpStatus> {
        try {
            bollettaRepository.deleteById(id)
            return ResponseEntity(HttpStatus.OK)
        } catch (e: Exception) {
            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @PutMapping(path = ["/update/{id}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun deleteBolletta(@PathVariable id: Int, @RequestBody inbolletta: BollettaDTO): ResponseEntity<HttpStatus> {
        try {

            val bolletta = bollettaRepository.findById(id)

            if (ObjectUtils.equals(bolletta, null))
                    return ResponseEntity(HttpStatus.NOT_ACCEPTABLE)

            if (ObjectUtils.equals(bollettaRepository.save(bolletta.map { Bolletta(it.id,
                             inbolletta.email
                            ,inbolletta.cc
                            ,inbolletta.importo
                            ,inbolletta.scadenza
                            ,inbolletta.numero
                            ,inbolletta.owner
                            ,inbolletta.td)}.get()), null))
                return ResponseEntity(HttpStatus.BAD_REQUEST)

            return ResponseEntity(HttpStatus.OK)

        } catch (e: Exception) {
            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

}
