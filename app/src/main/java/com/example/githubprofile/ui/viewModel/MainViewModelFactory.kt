package com.example.githubprofile.ui.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubprofile.datastore.SettingPreferences

class MainViewModelFactory private constructor(private val application: Application, private val pref: SettingPreferences) : ViewModelProvider.NewInstanceFactory() {

    companion object {

        @Volatile
        private var INSTANCES: MainViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application, pref: SettingPreferences): MainViewModelFactory {
            if(INSTANCES == null){
                synchronized(MainViewModelFactory::class.java){
                    INSTANCES = MainViewModelFactory(application, pref)
                }
            }
            return INSTANCES as MainViewModelFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T: ViewModel> create(viewModel: Class<T>): T{
        if(viewModel.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(application, pref) as T
        }
        throw java.lang.IllegalArgumentException("Unkown viewmodel class")
    }
}