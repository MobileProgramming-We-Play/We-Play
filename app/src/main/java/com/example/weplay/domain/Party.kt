package com.example.weplay.domain

import java.io.Serializable

data class Party(
    var pid: Int,
    var pplace: String,
    var ptitle: String,
    var pdate: Int,
    var pparticipantsNum: Int,
    var pparticipants: List<ParticipantInfo>,
    var pcontent: String,
    var platitude: Double,
    var plongitude: Double,
    var psports: String
) : Serializable {
    constructor(): this(0,"noplace", "notitle", 0, 0,
        ArrayList(), "nocontent", 0.0, 0.0,"notitle")
}