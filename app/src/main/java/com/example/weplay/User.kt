package com.example.weplay

data class User(
    var email: String,
    var nickName: String,
    var age: Int,
    var gender: Int // 0:남자 1:여자
) {
    constructor() : this("noinfo", "noinfo", -1, -1)
}