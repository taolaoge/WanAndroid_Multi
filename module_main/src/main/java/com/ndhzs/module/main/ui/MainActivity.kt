package com.ndhzs.module.main.ui

import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.launcher.ARouter
import com.ndhzs.api.test.ITestService
import com.ndhzs.lib.common.config.TEST_SHOW
import com.ndhzs.lib.common.extensions.toast
import com.ndhzs.lib.common.service.ServiceManager
import com.ndhzs.lib.common.ui.BaseActivity
import com.ndhzs.lib.common.ui.mvvm.BaseVmBindActivity
import com.ndhzs.module.main.IMainService
import com.ndhzs.module.main.R
import com.ndhzs.module.main.adapter.MainVpAdapter
import com.ndhzs.module.main.databinding.MainActivityMainBinding
import com.ndhzs.module.main.viewmodel.MainActivityViewModel
import kotlinx.coroutines.launch

class MainActivity : BaseVmBindActivity<MainActivityViewModel,MainActivityMainBinding>(){


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    initContent()
    liveDataObserve()


    lifecycleScope.launch{
      ARouter.getInstance()
        .navigation(IMainService::class.java)
        .dataFlow
        .emit(IMainService.Data("",""))
    }


    binding.vpMainActivityPages.adapter=MainVpAdapter(this,listOf(ARouter.getInstance().build(TEST_SHOW).navigation() as Fragment))

  }

  private fun initContent(){
    if(supportActionBar!=null){
      setSupportActionBar(binding.mainActivityToolBar)
    }
    //将抽屉布局和toolbar关联
    val toggle = ActionBarDrawerToggle(this,binding.mainActivityDrawerLayoutMainLayout, binding.mainActivityToolBar, 0, 0)
    toggle.syncState()

    binding.navigationViewMainMore.menu.findItem(R.id.main_nv_menu_integral).run {
        (actionView as? TextView)?.text=viewModel.getPersonalIntegration()
    }



  }


  private fun liveDataObserve(){

  }



}