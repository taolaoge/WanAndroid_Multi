package com.ndhzs.module.main.util

import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.launcher.ARouter
import com.ndhzs.lib.common.config.TEST_SHOW

/**
 *description:装main模块使用的工具
 * author:than
 * email:2058109198@qq.com
 * date:2022/6/9
 */

fun getFragmentList(vararg path:String):List<Fragment>{
    val fragmentList= mutableListOf<Fragment>()

    for (p in path){
        fragmentList.add(ARouter.getInstance().build(p).navigation() as Fragment)
    }

    return fragmentList
}