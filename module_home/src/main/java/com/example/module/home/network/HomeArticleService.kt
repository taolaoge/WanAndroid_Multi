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

    /**
     * 获取首页文章，参数为page
     */
    @GET("article/list/{page}/json")
    fun getHomeArticle(@Path("page") page: Int): Single<ApiWrapper<ArticleData>>

    /**
     * 获取首页的banner图
     */
    @GET("banner/json")
    fun getBanner(): Single<ApiWrapper<List<BannerData>>>

    /**
     * 获取常用网站
     */
    @GET("/friend/json")
    fun getFriend(): Single<ApiWrapper<List<FriendData>>>

    /**
     * 获取热词
     */
    @GET("/hotkey/json")
    fun getHotkey(): Single<ApiWrapper<List<HotkeyData>>>

    /**
     * 获取置顶文章
     */
    @GET("/article/top/json")
    fun getTop(): Single<ApiWrapper<List<TopData>>>
}