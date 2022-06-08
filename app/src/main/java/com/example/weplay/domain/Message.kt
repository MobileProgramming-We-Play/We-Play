package com.example.weplay.domain

data class Message(var uid: String, var text: String, var time: String) {
    constructor() : this("noinfo", "noinfo", "noinfo")
}