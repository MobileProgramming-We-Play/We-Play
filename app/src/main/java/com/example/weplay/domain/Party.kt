package com.example.weplay.domain

import java.io.Serializable

data class Party(
    var pid: Int,
    var ptitle: String,
    var pdate: Int,
    var pparticipantsNum: Int,
    var pparticipants: List<User>,
    var pcontent: String,
    var platitude: Double,
    var plongitude: Double
) : Serializable {
    constructor(): this(0, "notitle", 0, 0,
        ArrayList(), "nocontent", 0.0, 0.0)
}