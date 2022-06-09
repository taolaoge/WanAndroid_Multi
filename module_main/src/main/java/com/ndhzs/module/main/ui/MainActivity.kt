package com.ndhzs.module.main.ui

import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.launcher.ARouter
import com.google.android.material.navigation.NavigationBarView
import com.ndhzs.lib.common.config.TEST_SHOW
import com.ndhzs.lib.common.ui.mvvm.BaseVmBindActivity
import com.ndhzs.module.main.IMainService
import com.ndhzs.module.main.R
import com.ndhzs.module.main.adapter.MainVpAdapter
import com.ndhzs.module.main.databinding.MainActivityMainBinding
import com.ndhzs.module.main.util.getFragmentList
import com.ndhzs.module.main.ui.viewmodel.MainActivityViewModel
import kotlinx.coroutines.launch

class MainActivity : BaseVmBindActivity<MainActivityViewModel,MainActivityMainBinding>(), NavigationBarView.OnItemSelectedListener{

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

    binding.vpMainActivityPages.adapter=MainVpAdapter(this, getFragmentList(TEST_SHOW))

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

    binding.mainActivityBottomNavigationView.setOnItemSelectedListener(this)

  }

  private fun liveDataObserve(){

  }

  override fun onNavigationItemSelected(item: MenuItem): Boolean {
    when(item.itemId){
      R.id.main_bnv_home_page->Toast.makeText(this,"首页",Toast.LENGTH_SHORT).show()
      R.id.main_bnv_square_page->Toast.makeText(this,"广场",Toast.LENGTH_SHORT).show()
      R.id.main_bnv_wechat_page->Toast.makeText(this,"公众号",Toast.LENGTH_SHORT).show()
      R.id.main_bnv_system_page->Toast.makeText(this,"体系",Toast.LENGTH_SHORT).show()
      R.id.main_bnv_project_page->Toast.makeText(this,"项目",Toast.LENGTH_SHORT).show()
    }
    return  true
  }


}