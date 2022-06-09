package com.ndhzs.module.test.page

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ndhzs.module.test.R

class TestAdapter(private val list: List<String>) : RecyclerView.Adapter<TestAdapter.TestViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestViewHolder {
        return TestViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.rv_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TestViewHolder, position: Int) {
        holder.textView1.text = list[position]
        holder.textView2.text = list[position]
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class TestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView1: TextView
        var textView2: TextView

        init {
            textView1 = itemView.findViewById(R.id.test_textView1)
            textView2 = itemView.findViewById(R.id.test_textView2)
        }
    }
}