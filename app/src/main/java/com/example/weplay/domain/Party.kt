package com.example.weplay.domain

import java.io.Serializable

data class Party(
    var pid: Int,
    var pplace: String,
    var ptitle: String,
    var pdate: Long,
    var pparticipantsNum: Int,
    var pparticipants: Map<String, ParticipantInfo>,
    var pcontent: String,
    var platitude: Double,
    var plongitude: Double,
    var psports: String,
    var pcity : String
) : Serializable {
    constructor(): this(0,"noplace", "notitle", 0, 0,
        HashMap(), "nocontent", 0.0, 0.0,"notitle", "notitle")
}