package com.example.module.home.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
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
            freshRecycleView()
        }

    private val mSwipeLayout: SwipeRefreshLayout by R.id.swipe_layout_rv.view<SwipeRefreshLayout>()

    private fun initSwipeLayout(){
        mSwipeLayout.setOnRefreshListener {
            //先清除先前List的数据，然后将page的值变为0，此时就会回调监听page的代码
            viewModel.clearList()
            viewModel.clearPage()
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
            /**
             * 传入一个高阶函数，当请求成功后回调，取消swipeLayout的刷新动画
             * 可以加一个判空吗，isRefreshing
             */
            viewModel.getNormalArticleData(){
                mSwipeLayout.isRefreshing = false
            }
            //如果page==0的话，此时的banner以及topArticle的数据还没有请求，需要请求
            if (it == 0){
                loadBannerData()
                loadTopData()
                //增加监听，下拉刷新的监听
                initSwipeLayout()
            }
        }

        viewModel.isLoading.observe(this){
            //数据加载完成后，刷新rv的adapter
            if (!it) freshRecycleViewData()
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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