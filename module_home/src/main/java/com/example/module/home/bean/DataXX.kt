package com.example.module.home.bean

data class DataXX(
    val curPage: Int,
    val datas: List<DataXXX>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
)