package com.example.weplay.domain

import java.io.Serializable

data class ParticipantInfo(
    var uid: String,
    var uname: String
) : Serializable {
    constructor() : this("", "")
}
