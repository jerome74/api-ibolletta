package it.wlp.api.ibolletta.controllers

import it.wlp.api.ibolletta.dtos.BollettaDTO
import it.wlp.api.ibolletta.models.CredentialModel
import it.wlp.api.ibolletta.models.UserModel
import it.wlp.api.ibolletta.proxies.IBollettaProxy
import it.wlp.api.ibolletta.services.UserServiceProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import springfox.documentation.swagger2.annotations.EnableSwagger2

@RestController
@RequestMapping("/api/api-rest")
class ApiController {

    @Autowired
    lateinit var iBollettaProxy : IBollettaProxy

    @Autowired
    lateinit var userServiceProvider: UserServiceProvider

    @PostMapping(path = ["/login"] , produces= [MediaType.APPLICATION_JSON_VALUE] )
    fun login(@RequestBody credential : CredentialModel) {}

    @PostMapping(path = ["/signin"] , produces= [MediaType.APPLICATION_JSON_VALUE] )
    fun signin(@RequestBody credential : UserModel) : ResponseEntity<String> {

        if(!userServiceProvider.registerUser(credential))
            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        else
            return ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(path = ["/confirm/{email}"]  )
    fun confirm(@PathVariable email : String) : ResponseEntity<String> {

        if(!userServiceProvider.confirUser(email))
            return ResponseEntity(HttpStatus.UNAUTHORIZED);
        else
            return ResponseEntity.status(HttpStatus.OK).body("mail confirmed")
    }

    @PostMapping(path = ["/save"]  )
    fun save(@RequestBody bolletta: BollettaDTO) : ResponseEntity<HttpStatus>{
        return iBollettaProxy.saveBolletta(bolletta)
    }

    @GetMapping(path = ["/findall/{email}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAllBollettas(@PathVariable email: String): List<BollettaDTO>{
        return iBollettaProxy.findAllBollettas(email)
    }

    @GetMapping(path = ["/delete/{id}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun deleteBolletta(@PathVariable id: Int): ResponseEntity<HttpStatus>{
        return iBollettaProxy.deleteBolletta(id)
    }

    @PutMapping(path = ["/update/{id}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun updateBolletta(@PathVariable id: Int, @RequestBody inbolletta: BollettaDTO): ResponseEntity<HttpStatus>{
        return iBollettaProxy.updateBolletta(id, inbolletta)
    }

}