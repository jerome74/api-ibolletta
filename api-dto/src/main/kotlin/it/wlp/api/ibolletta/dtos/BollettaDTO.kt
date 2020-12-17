package it.wlp.api.ibolletta.dtos

data class BollettaDTO (var id : Int = 0
                     , var email : String = ""
                     , var cc : String = ""
                     , var importo : String = ""
                     , var scadenza : String = ""
                     , var numero : String = ""
                     , var owner : String = ""
                     , var td : String = "")