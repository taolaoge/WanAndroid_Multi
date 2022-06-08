package com.example.module.home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.module.home.R
import com.example.module.home.ui.adapter.ArticleRvAdapter
import com.example.module.home.viewModel.HomeArticleViewModel
import com.ndhzs.lib.common.ui.BaseFragment

/**
 * description ： TODO:类的作用
 * author :Li Jian
 * email : 1678921845@qq.com
 * date : 2022/6/6
 */
class HomeArticleFragment : BaseFragment() {
    private val viewModel by lazy { ViewModelProvider(this)[HomeArticleViewModel::class.java] }

    private val mRvArticle: RecyclerView by R.id.rv_article.view<RecyclerView>()
        .addInitialize {
            //可能需要添加线性布局或者是adapter中的list
            val layoutManager = LinearLayoutManager(context)
            val adapter =
                ArticleRvAdapter(viewModel.bannerData, viewModel.normalArticleData, viewModel.topData)
            mRvArticle.run {
                this.layoutManager = layoutManager
                this.adapter = adapter
                addItemDecoration(
                    DividerItemDecoration(
                        context, DividerItemDecoration.VERTICAL
                    )
                )
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment_home_article, container, false)
    }

    //在这里观察LiveData
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //观察page,初始化page，page = 0时，就会调用一次
        viewModel.page.observe(this) {
            viewModel.getNormalArticleData()
        }

        viewModel.isLoading.observe(this){
            if (!it) freshRecycleViewData()
        }

        viewModel.isSlide.observe(this){
            if (it) freshRecycleView()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadBannerData()
        loadTopData()
    }

    private fun loadTopData() {
        viewModel.getTop()
    }

    private fun loadBannerData() {
        viewModel.getBanner()
    }

    private fun freshRecycleView() {
        mRvArticle.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                //dx为横向滚动 dy为竖向滚动
                //如果为竖向滚动,则isSliding属性为true，横向滚动则为false
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                //manager必须为LinearLayoutManager
                val manager: LinearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                //newState是RecycleView的状态 如果它的状态为没有滚动时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //获取最后一个完全显示的ItemPosition
                    val lastVisibleItem = manager.findLastCompletelyVisibleItemPosition()
                    val totalItem = manager.itemCount
                    if (lastVisibleItem == (totalItem - 1) && viewModel.isLoading.value == false) {
                        viewModel.addPage()
                    }
                }
            }
        })
    }

    private fun freshRecycleViewData() {
        //在原来的adapter里刷新数据即可
        mRvArticle.adapter?.notifyDataSetChanged()
    }
}