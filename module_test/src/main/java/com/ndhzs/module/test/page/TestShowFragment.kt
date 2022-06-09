package com.ndhzs.module.test.page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.ndhzs.lib.common.config.TEST_SHOW
import com.ndhzs.lib.common.ui.BaseFragment
import com.ndhzs.module.test.R

/**
 * ...
 * @author 985892345 (Guo Xiangrui)
 * @email 2767465918@qq.com
 * @date 2022/5/27 21:40
 */
@Route(path = TEST_SHOW)
class TestShowFragment : BaseFragment() {
  private var recyclerView: RecyclerView? = null
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {

    val view: View = inflater.inflate(R.layout.test_fragment_show, container, false)

    recyclerView = view.findViewById(R.id.test_rv)

    val list: MutableList<String> = ArrayList()

    for (i in 0..49) {
      list.add(i.toString())
    }
    val manager = LinearLayoutManager(requireActivity())
    recyclerView!!.layoutManager = manager
    recyclerView!!.adapter = TestAdapter(list)


    return view
  }
}