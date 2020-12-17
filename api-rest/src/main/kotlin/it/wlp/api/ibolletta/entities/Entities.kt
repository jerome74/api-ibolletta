package it.wlp.api.ibolletta.entities

import java.sql.Timestamp
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Users(@Id var id: Int
                 , var username: String
                 , var email: String
                 , var password: String
                 , var active: Int
                 , var startdate: Timestamp
                 , var enddate: Timestamp?)

@Entity
data class Profiles(@Id var id: Int
                    , var nickname: String
                    , var email: String
                    , var avatarname: String
                    , var avatarcolor: String
                    , var active: Int
                    , var startdate: Timestamp
                    , var enddate: Timestamp?)


