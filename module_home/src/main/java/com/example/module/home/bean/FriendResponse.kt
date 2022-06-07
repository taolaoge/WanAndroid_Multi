package com.example.module.home.bean

data class FriendResponse(
    val `data`: List<Data>,
    val errorCode: Int,
    val errorMsg: String
)