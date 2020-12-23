package it.wlp.api.ibolletta.proxies

import it.wlp.api.ibolletta.dtos.BollettaDTO
import org.springframework.cloud.netflix.ribbon.RibbonClient
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@FeignClient("zuul-api-gateway")
@RibbonClient("ibolletta-service")
interface IBollettaProxy {

    @PostMapping("ibolletta-service/ibolletta/save")
    fun saveBolletta(@RequestBody bolletta: BollettaDTO): ResponseEntity<String>

    @GetMapping("ibolletta-service/ibolletta/findall/{email}")
    fun findAllBollettas(@PathVariable email: String): List<BollettaDTO>

    @GetMapping("ibolletta-service/ibolletta/delete/{id}")
    fun deleteBolletta(@PathVariable id: Int): ResponseEntity<String>

    @PutMapping("ibolletta-service/ibolletta/update/{id}")
    fun updateBolletta(@PathVariable id: Int, @RequestBody inbolletta: BollettaDTO): ResponseEntity<String>
}