package com.example.module.home.network

import com.example.module.home.bean.*
import com.ndhzs.lib.common.network.ApiGenerator
import com.ndhzs.lib.common.network.ApiWrapper
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * description ： TODO:类的作用
 * author :Li Jian
 * email : 1678921845@qq.com
 * date : 2022/6/6
 */
interface HomeArticleService {

    companion object {
        val INSTANCE by lazy {
            ApiGenerator.getApiService(HomeArticleService::class)
        }
    }

    @GET("/article/list/{page}/json")
    fun getHomeArticle(@Path("page") page: Int): Single<ApiWrapper<HomeArticleResponse>>

    @GET("/banner/json")
    fun getBanner():Single<ApiWrapper<BannerResponse>>

    @GET("/friend/json")
    fun getFriend():Single<ApiWrapper<FriendResponse>>

    @GET("/hotkey/json")
    fun getHotkey():Single<ApiWrapper<HotkeyResponse>>

    @GET("/article/top/json")
    fun getTop():Single<ApiWrapper<TopResponse>>
}