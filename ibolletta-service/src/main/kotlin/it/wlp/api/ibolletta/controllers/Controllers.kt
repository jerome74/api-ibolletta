package it.wlp.api.ibolletta.controllers

import it.wlp.api.ibolletta.configs.ConfigProperties
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

    @Autowired
    lateinit var configProperties: ConfigProperties



    @PostMapping(path = ["/save"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun saveBolletta(@RequestBody bolletta: BollettaDTO): ResponseEntity<String> {
        bollettaRepository.save(Bolletta(bolletta.id,
                        bolletta.email
                        ,bolletta.cc
                        ,bolletta.importo
                        ,bolletta.scadenza
                        ,bolletta.numero
                        ,bolletta.owner
                        ,bolletta.td))



        return ResponseEntity.ok()
                .contentLength(configProperties.getPropertes("ibolletta.message.save.ok")!!.length.toLong())
                .contentType(MediaType.TEXT_PLAIN)
                .body(configProperties.getPropertes("ibolletta.message.save.ok"))


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
    fun deleteBolletta(@PathVariable id: Int): ResponseEntity<String> {
        try {
            bollettaRepository.deleteById(id)
            return ResponseEntity.ok()
                    .contentLength(configProperties.getPropertes("ibolletta.message.save.ok")!!.length.toLong())
                    .contentType(MediaType.TEXT_PLAIN)
                    .body(configProperties.getPropertes("ibolletta.message.delete.ok"))
        } catch (e: Exception) {
            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @PutMapping(path = ["/update/{id}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun deleteBolletta(@PathVariable id: Int, @RequestBody inbolletta: BollettaDTO): ResponseEntity<String> {
        try {

            val bolletta = bollettaRepository.findById(id)

            if (ObjectUtils.equals(bolletta, null))
                    return ResponseEntity(HttpStatus.NOT_ACCEPTABLE)

            bollettaRepository.save(bolletta.map { Bolletta(it.id,
                             inbolletta.email
                            ,inbolletta.cc
                            ,inbolletta.importo
                            ,inbolletta.scadenza
                            ,inbolletta.numero
                            ,inbolletta.owner
                            ,inbolletta.td)}.get())

            return ResponseEntity.ok()
                    .contentLength(configProperties.getPropertes("ibolletta.message.update.ok")!!.length.toLong())
                    .contentType(MediaType.TEXT_PLAIN)
                    .body(configProperties.getPropertes("ibolletta.message.update.ok"))

        } catch (e: Exception) {
            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

}
