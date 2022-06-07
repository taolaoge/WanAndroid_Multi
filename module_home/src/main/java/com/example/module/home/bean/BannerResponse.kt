package com.example.module.home.bean

data class BannerResponse(
    val `data`: List<DataX>,
    val errorCode: Int,
    val errorMsg: String
)