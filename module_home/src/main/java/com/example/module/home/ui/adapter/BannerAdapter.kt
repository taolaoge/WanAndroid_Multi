package com.example.module.home.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.module.home.R
import com.example.module.home.bean.Banner
import com.example.module.home.bean.BannerData

/**
 * description ： 首页banner图适配器
 * author :Li Jian
 * email : 1678921845@qq.com
 * date : 2022/6/8
 */
class BannerAdapter(private val data: List<BannerData>)
    :RecyclerView.Adapter<BannerAdapter.InnerHolder>(){

    inner class InnerHolder(view: View):RecyclerView.ViewHolder(view){
       val mImg:ImageView = view.findViewById(R.id.vp_item_img)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.home_vp_item_banner_item,parent,false)
        return InnerHolder(view)
    }

    override fun onBindViewHolder(holder: InnerHolder, position: Int) {
        val newPosition = position % 3
        Glide.with(holder.itemView.context).load(data[newPosition].imagePath).into(holder.mImg)
    }

    override fun getItemCount(): Int = 1000
}