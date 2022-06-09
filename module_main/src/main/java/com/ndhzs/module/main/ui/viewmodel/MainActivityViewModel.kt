package com.ndhzs.module.main.ui.viewmodel

import com.ndhzs.lib.common.ui.BaseViewModel

class MainActivityViewModel:BaseViewModel() {
    private lateinit var personalIntegration:String

    fun getPersonalIntegration():String{
        if(!::personalIntegration.isInitialized){
            personalIntegration="todo"
        }

        return personalIntegration
    }

}