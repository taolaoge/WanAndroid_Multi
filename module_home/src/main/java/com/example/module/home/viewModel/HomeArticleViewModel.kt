package com.example.module.home.viewModel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.module.home.bean.ArticleData
import com.example.module.home.bean.BannerData
import com.example.module.home.bean.DataXX
import com.example.module.home.bean.TopData
import com.example.module.home.network.HomeArticleService
import com.ndhzs.lib.common.extensions.mapOrCatchApiException
import com.ndhzs.lib.common.extensions.mapOrThrowApiException
import com.ndhzs.lib.common.extensions.toast
import com.ndhzs.lib.common.ui.mvvm.BaseViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * description ： TODO:类的作用
 * author :Li Jian
 * email : 1678921845@qq.com
 * date : 2022/6/6
 */
class HomeArticleViewModel : BaseViewModel() {
    //保持单向更新
    private val _page = MutableLiveData(0)
    val page: LiveData<Int>
        get() = _page

    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    val bannerData = ArrayList<BannerData>()
    val topData = ArrayList<TopData>()
    val normalArticleData = ArrayList<DataXX>()


    fun getBanner() {
        HomeArticleService.INSTANCE.getBanner()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {
                //处理错误
                toast(it.toString())
            }
            .mapOrThrowApiException()
            .safeSubscribeBy {
                //处理数据
                dealBannerData(it)
            }
    }

    private fun dealBannerData(data: List<BannerData>) {
        //将banner数据添加进动态数组
        for (i in data) {
            bannerData.add(i)
        }
    }

    fun getTop() {
        HomeArticleService.INSTANCE.getTop()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {
                toast(it.toString())
            }
            .mapOrThrowApiException()
            .safeSubscribeBy {
                //处理数据
                dealTopData(it)
            }
    }

    private fun dealTopData(data: List<TopData>) {
           for (i in data){
               topData.add(i)
           }
    }

    fun addPage(){
        _page.value = _page.value!!.plus(1)
    }

    fun getNormalArticleData(block:()->Unit){
        _isLoading.value = true
        HomeArticleService.INSTANCE.getHomeArticle(page.value!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {
                toast(it.toString())
            }
            .mapOrThrowApiException()
            .safeSubscribeBy {
                dealNormalArticleData(it)
                _isLoading.value = false
                //请求到了数据后，此时的转圈进度条就可以关闭，回调函数调用处的高阶函数
                block()
            }
    }

    private fun dealNormalArticleData(data: ArticleData) {
           for (i in data.datas){
               normalArticleData.add(i)
           }
    }

    fun clearPage(){
        _page.value = 0
    }

    fun clearList(){
        bannerData.clear()
        normalArticleData.clear()
        topData.clear()
    }
}