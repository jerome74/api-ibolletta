package it.wlp.api.ibolletta.utils

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

object UtilCrypt
{
    val crypto = BCryptPasswordEncoder(12)
}