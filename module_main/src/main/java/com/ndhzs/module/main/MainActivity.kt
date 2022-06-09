package com.ndhzs.module.main

import android.os.Bundle
import android.widget.Button
import com.ndhzs.api.test.ITestService
import com.ndhzs.lib.common.config.TEST_SHOW
import com.ndhzs.lib.common.extensions.toast
import com.ndhzs.lib.common.service.ServiceManager
import com.ndhzs.lib.common.ui.BaseActivity

class MainActivity : BaseActivity() {
  

  
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.main_activity_main)
    


    // 观察 liveData
    ServiceManager(ITestService::class).liveData.observe {
      // ......
    }


  }
}