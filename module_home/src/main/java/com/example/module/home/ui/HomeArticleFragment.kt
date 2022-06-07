package com.example.module.home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.module.home.R
import com.example.module.home.viewModel.HomeArticleViewModel
import com.ndhzs.lib.common.ui.BaseFragment

/**
 * description ： TODO:类的作用
 * author :Li Jian
 * email : 1678921845@qq.com
 * date : 2022/6/6
 */
class HomeArticleFragment : BaseFragment() {
    val viewModel by lazy { ViewModelProvider(this)[HomeArticleViewModel::class.java] }

    val mRvArticle: RecyclerView by R.id.rv_article.view<RecyclerView>()
        .addInitialize {
            //可能需要添加线性布局或者是adapter中的list
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment_home_article, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBanner()
    }

    private fun initBanner() {
        viewModel.getBanner()
    }
}