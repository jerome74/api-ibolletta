package it.wlp.api.ibolletta.utils

import it.wlp.api.ibolletta.configs.ConfigSecret
import org.springframework.beans.factory.annotation.Autowired

object AuthObj {
    var sha256 : ByteArray? = null
    var configSecret: ConfigSecret? = null
}