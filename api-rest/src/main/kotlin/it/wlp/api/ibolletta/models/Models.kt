package it.wlp.api.ibolletta.models

import javax.persistence.Id

data class UserModel(val username: String
                    , val email: String
                    , val password: String)

data class UserprofileModel(val nickname: String
                            , val email: String
                            , val avatarname: String
                            , val avatarcolor: String)

class CredentialModel() {
    lateinit var username: String
    lateinit var password: String
}
