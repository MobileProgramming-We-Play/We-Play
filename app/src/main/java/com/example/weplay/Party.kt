package com.example.weplay

data class Party(var pTitle:String, var pSports:String, var pNowNum:Int, var pTotalNum:Int) {
    constructor():this("noinfo", "noinfo", 0, 0)
}
