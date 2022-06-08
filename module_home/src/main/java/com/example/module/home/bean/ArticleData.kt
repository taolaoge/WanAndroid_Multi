package com.example.module.home.bean

data class ArticleData(
    val curPage: Int,
    val datas: List<DataXX>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
)