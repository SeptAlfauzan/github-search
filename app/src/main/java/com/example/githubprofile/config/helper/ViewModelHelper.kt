package com.example.githubprofile.config.helper

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.githubprofile.datastore.SettingPreferences
import com.example.githubprofile.ui.viewModel.MainViewModel
import com.example.githubprofile.ui.viewModel.MainViewModelFactory

class ViewModelHelper {
    companion object {
        fun obtainViewModel(activity: AppCompatActivity): MainViewModel {
            val pref = SettingPreferences.getInstance()
            val factory = MainViewModelFactory.getInstance(activity.application, pref)
            return ViewModelProvider(activity, factory).get(MainViewModel::class.java)
        }
    }
}