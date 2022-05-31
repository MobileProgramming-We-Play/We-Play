package com.example.weplay.domain

import java.io.Serializable

data class User(
    var email: String,
    var nickName: String,
    var age: Int,
    var gender: Int // 0:남자 1:여자
) : Serializable {
    constructor() : this("noinfo", "noinfo", -1, -1)
}
