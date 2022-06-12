package com.example.module.home.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.module.home.R
import com.example.module.home.bean.BannerData
import com.example.module.home.bean.DataXX
import com.example.module.home.bean.TopData
import java.util.*
import kotlin.concurrent.timerTask

/**
 * description ： TODO:类的作用
 * author :Li Jian
 * email : 1678921845@qq.com
 * date : 2022/6/8
 */
class ArticleRvAdapter(
    private val bannerData: List<BannerData>,
    private val normalArticleData: List<DataXX>, private val topArticleData: List<TopData>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val topSize = topArticleData.size
    private val TYPE_BANNER = 0
    private val TYPE_TOP = 1
    private val TYPE_NEW = 5
    private val TYPE_BOTTOM = 11
    var lastPosition = 498

    inner class BannerHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val vp2: ViewPager2 = view.findViewById(R.id.vp2_banner)

        init {
            vp2.adapter = BannerAdapter(bannerData)
            vp2.setCurrentItem(498, false)
            vp2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    //监听是否滑动
                    lastPosition = position
                }
            })
            Timer().schedule(timerTask {
                lastPosition++
                vp2.currentItem = lastPosition
            }, 0, 5000)
        }
    }

    inner class BottomHolder(view: View) : RecyclerView.ViewHolder(view) {}

    inner class TopHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mTvTag:TextView = view.findViewById(R.id.tv_top_tag)
        val mTvNew: TextView = view.findViewById(R.id.tv_top_top)
        val mTvAuthor: TextView = view.findViewById(R.id.tv_top_author)
        val mTvTime: TextView = view.findViewById(R.id.tv_top_time)
        val mTvTitle: TextView = view.findViewById(R.id.tv_top_title)
        val mTvChapterName: TextView = view.findViewById(R.id.tv_top_chapterName)
        val mTvSuperChapterName: TextView = view.findViewById(R.id.tv_top_superChapterName)
    }


    inner class NormalHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mTvAuthor: TextView = view.findViewById(R.id.tv_new_author)
        val mTvTime: TextView = view.findViewById(R.id.tv_new_time)
        val mTvTitle: TextView = view.findViewById(R.id.tv_new_title)
        val mTvChapterName: TextView = view.findViewById(R.id.tv_new_chapterName)
        val mTvSuperChapterName: TextView = view.findViewById(R.id.tv_new_superChapterName)
        val mTvTag: TextView = view.findViewById(R.id.tv_new_tag_tag)
        val mTvNew: TextView = view.findViewById(R.id.tv_new_tag)
    }


    /**
     *得到当前position的item的种类
     *因为需求是一个rv中含有banner和滑动，这是两个不同种类的item，所以需要重写这个方法来判断不同的位置该用不同的item
     *如果position == 0 则此时的item为banner轮播图
     *有了类型的判断就可以在onCreateViewHolder中首先判断viewType的种类，对应的item的类型填充不同的视图
     *这一步是必须要判断的，如果不判断的种类的话，在onCreateViewHolder中会造成item的类型错乱，显示错乱等等
     */
    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return TYPE_BANNER
        } else if (position == 1 + topSize + normalArticleData.size){
            return TYPE_BOTTOM
        } else {
            //此时为置顶的item，用的是TopArticleData的数据
            return if (position < topSize + 1) {
                TYPE_TOP
            } else {
                /**
                 * 这里为非置顶的item，用的是normalArticleData的数据
                 * 首先判断是否为最后一个item，最后一个item为显示刷新数据的item
                 * 然后在article的数据中判断是否为新
                 */
                TYPE_NEW
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> BannerHolder(createView(R.layout.home_recycle_item_banner, parent))
            1 -> TopHolder(createView(R.layout.home_recycle_item_top, parent))
            5 -> NormalHolder(createView(R.layout.home_recycle_item_normal, parent))
            11 -> BottomHolder(createView(R.layout.home_recycle_item_bottom, parent))
            else -> {
                NormalHolder(createView(R.layout.home_recycle_item_normal, parent))
            }
        }
    }

    private fun createView(a: Int, parent: ViewGroup): View {
        return LayoutInflater.from(parent.context)
            .inflate(a, parent, false)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var article: DataXX
        var top: TopData
        /**
         * 判断是否为置顶的文章或者是最后一个item
         */
        if (position < topSize + 1 && position >= 0) {
            //如果不是置顶的banner或者bottom_item
            if (position != 0) {
                top = topArticleData[position - 1]
                if (holder is TopHolder) {
                    holder.run {
                        if (top.author == "") mTvAuthor.text = top.shareUser
                        else mTvAuthor.text = top.author
                        if (!top.fresh) mTvNew.visibility = View.GONE
                        if (top.tags.isEmpty()) mTvTag.visibility = View.GONE
                        mTvChapterName.text = top.chapterName
                        mTvTime.text = top.niceDate
                        mTvTitle.text = top.title
                        mTvSuperChapterName.text = top.superChapterName
                    }
                }
            }
        } else {
            /**
             * 首先判断是否为新的文章
             * 新的文章按新的文章来处理
             */
            if (position != 1 + topSize + normalArticleData.size) {
                article = normalArticleData[position - 1 - topSize]
                if (holder is NormalHolder) {
                    holder.run {
                        if (article.author == "") mTvAuthor.text =
                            article.shareUser else mTvAuthor.text = article.author
                        if (!article.fresh) mTvNew.visibility = View.GONE
                        if (article.tags.isEmpty()) {
                            mTvTag.visibility = View.GONE
                        } else {
                            mTvTag.text = article.tags[0].name
                        }
                        mTvChapterName.text = article.chapterName
                        mTvTime.text = article.niceDate
                        mTvTitle.text = article.title
                        mTvSuperChapterName.text = article.superChapterName
                    }
                }
            }
        }
    }


    override fun getItemCount(): Int = topArticleData.size + normalArticleData.size + 2
}