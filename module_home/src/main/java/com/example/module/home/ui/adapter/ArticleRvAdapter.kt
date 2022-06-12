package com.example.module.home.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.module.home.R
import com.example.module.home.bean.ArticleData
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
    private val TYPE_TOP_NEW = 1
    private val TYPE_TOP_TAG = 2
    private val TYPE_TOP = 3
    private val TYPE_TOP_new_TAG = 4
    private val TYPE_NEW = 5
    private val TYPE_NEW_TAG = 6
    private val TYPE_NEW_PROJECT = 7
    private val TYPE_NORMAL_TAG = 8
    private val TYPE_NORMAL_PROJECT = 9
    private val TYPE_NORMAL = 10


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

    inner class TopNewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mTvAuthor: TextView = view.findViewById(R.id.tv_top_new_author)
        val mTvTime: TextView = view.findViewById(R.id.tv_top_new_time)
        val mTvTitle: TextView = view.findViewById(R.id.tv_top_new_title)
        val mTvChapterName: TextView = view.findViewById(R.id.tv_top_new_chapterName)
        val mTvSuperChapterName: TextView = view.findViewById(R.id.tv_top_new_superChapterName)
    }

    inner class TopTagHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mTvAuthor: TextView = view.findViewById(R.id.tv_top_tag_author)
        val mTvTime: TextView = view.findViewById(R.id.tv_top_tag_time)
        val mTvTitle: TextView = view.findViewById(R.id.tv_top_tag_title)
        val mTvChapterName: TextView = view.findViewById(R.id.tv_top_tag_chapterName)
        val mTvSuperChapterName: TextView = view.findViewById(R.id.tv_top_tag_superChapterName)
    }

    inner class NewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mTvAuthor: TextView = view.findViewById(R.id.tv_new_author)
        val mTvTime: TextView = view.findViewById(R.id.tv_new_time)
        val mTvTitle: TextView = view.findViewById(R.id.tv_new_title)
        val mTvChapterName: TextView = view.findViewById(R.id.tv_new_chapterName)
        val mTvSuperChapterName: TextView = view.findViewById(R.id.tv_new_superChapterName)
    }

    inner class NewTagHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mTvTag:TextView = view.findViewById(R.id.tv_new_tag_tag)
        val mTvAuthor: TextView = view.findViewById(R.id.tv_new_tag_author)
        val mTvTime: TextView = view.findViewById(R.id.tv_new_tag_time)
        val mTvTitle: TextView = view.findViewById(R.id.tv_new_tag_title)
        val mTvChapterName: TextView = view.findViewById(R.id.tv_new_tag_chapterName)
        val mTvSuperChapterName: TextView = view.findViewById(R.id.tv_new_tag_superChapterName)
    }

    inner class NormalHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mTvAuthor: TextView = view.findViewById(R.id.tv_normal_author)
        val mTvTime: TextView = view.findViewById(R.id.tv_normal_time)
        val mTvTitle: TextView = view.findViewById(R.id.tv_normal_title)
        val mTvChapterName: TextView = view.findViewById(R.id.tv_normal_chapterName)
        val mTvSuperChapterName: TextView = view.findViewById(R.id.tv_normal_superChapterName)
    }

    inner class NormalTagHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mTvAuthor: TextView = view.findViewById(R.id.tv_normal_tag_author)
        val mTvTime: TextView = view.findViewById(R.id.tv_normal_tag_time)
        val mTvTitle: TextView = view.findViewById(R.id.tv_normal_tag_title)
        val mTvChapterName: TextView = view.findViewById(R.id.tv_normal_tag_chapterName)
        val mTvSuperChapterName: TextView = view.findViewById(R.id.tv_normal_tag_superChapterName)
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
        } else {
            //此时为置顶的item，用的是TopArticleData的数据
            if (position < topSize + 1) {
                //判断是否为新更新的文章
                return if (topArticleData[position - 1].fresh) {
                    TYPE_TOP_NEW
                } else {
                    TYPE_TOP_TAG
                }
            } else {
                //这里为非置顶的item，用的是normalArticleData的数据
                //首先判断是否为新
                val article = normalArticleData[position - 1 - topSize]
                return if (article.fresh) {
                    if (article.tags.isEmpty()) TYPE_NEW
                    else if (article.tags[0].name == "项目") TYPE_NEW_PROJECT
                    else TYPE_NEW_TAG
                } else {
                    if (article.tags.isEmpty()) TYPE_NORMAL
                    else if (article.tags[0].name == "项目") TYPE_NORMAL_PROJECT
                    else TYPE_NORMAL_TAG
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> BannerHolder(createView(R.layout.home_recycle_item_banner, parent))
            1 -> TopNewHolder(createView(R.layout.home_recycle_item_top_new, parent))
            2 -> TopTagHolder(createView(R.layout.home_recycle_item_top_tag, parent))
            5 -> NewHolder(createView(R.layout.home_recycle_item_new, parent))
            6 -> NewTagHolder(createView(R.layout.home_recycle_item_new_tag, parent))
            8 -> NormalTagHolder(createView(R.layout.home_recycle_item_normal_tag, parent))
            10 -> NormalHolder(createView(R.layout.home_recycle_item_normal, parent))
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
         * 判断是否为置顶的文章
         */
        if (position < topSize + 1 && position >= 0) {
            if (position != 0) {
                top = topArticleData[position - 1]
                if (holder is TopNewHolder){
                    holder.run {
                        if (top.author == "") mTvAuthor.text = top.shareUser else mTvAuthor.text = top.author
                        mTvChapterName.text = top.chapterName
                        mTvTime.text = top.niceDate
                        mTvTitle.text = top.title
                        mTvSuperChapterName.text = top.superChapterName
                    }
                }
                if (holder is TopTagHolder) {
                    holder.run {
                        if (top.author == "") mTvAuthor.text = top.shareUser else mTvAuthor.text = top.author
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
            article = normalArticleData[position - 1 - topSize]
            if (holder is NewHolder) {
                holder.run {
                    if (article.author == "") mTvAuthor.text = article.shareUser else mTvAuthor.text = article.author
                    mTvChapterName.text = article.chapterName
                    mTvTime.text = article.niceDate
                    mTvTitle.text = article.title
                    mTvSuperChapterName.text = article.superChapterName
                }
            }
            if (holder is NewTagHolder){
                holder.run {
                    mTvTag.text = article.tags[0].name
                    if (article.author == "") mTvAuthor.text = article.shareUser else mTvAuthor.text = article.author
                    mTvChapterName.text = article.chapterName
                    mTvTime.text = article.niceDate
                    mTvTitle.text = article.title
                    mTvSuperChapterName.text = article.superChapterName
                }
            }
            if (holder is NormalTagHolder){
                holder.run {
                    if (article.author == "") mTvAuthor.text = article.shareUser else mTvAuthor.text = article.author
                    mTvChapterName.text = article.chapterName
                    mTvTime.text = article.niceDate
                    mTvTitle.text = article.title
                    mTvSuperChapterName.text = article.superChapterName
                }
            }
            if (holder is NormalHolder){
                holder.run {
                    if (article.author == "") mTvAuthor.text = article.shareUser else mTvAuthor.text = article.author
                    mTvChapterName.text = article.chapterName
                    mTvTime.text = article.niceDate
                    mTvTitle.text = article.title
                    mTvSuperChapterName.text = article.superChapterName
                }
            }
        }
    }

    override fun getItemCount(): Int = topArticleData.size + normalArticleData.size +1
}