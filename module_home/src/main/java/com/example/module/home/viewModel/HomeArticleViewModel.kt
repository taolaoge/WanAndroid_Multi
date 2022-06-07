package com.example.module.home.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.module.home.bean.BannerResponse
import com.example.module.home.network.HomeArticleService
import com.ndhzs.lib.common.extensions.mapOrCatchApiException
import com.ndhzs.lib.common.ui.mvvm.BaseViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.schedulers.Schedulers.io

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

    fun getBanner()= HomeArticleService.INSTANCE.getBanner()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .mapOrCatchApiException {

        }
        .unSafeSubscribeBy {
            dealBannerData(it)
        }

    private fun dealBannerData(response:BannerResponse) {
        Log.d("bbp", "dealBannerData: $response")
    }

}