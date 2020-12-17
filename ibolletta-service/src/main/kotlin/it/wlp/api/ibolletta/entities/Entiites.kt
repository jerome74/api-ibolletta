package it.wlp.api.ibolletta.entities

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Bolletta (@Id
                     @GeneratedValue(strategy = GenerationType.AUTO)
                     var id : Int = 0
                                   , var email : String = ""
                                   , var cc : String = ""
                                   , var importo : String = ""
                                   , var scadenza : String = ""
                                   , var numero : String = ""
                                   , var owner : String = ""
                                   , var td : String = "")