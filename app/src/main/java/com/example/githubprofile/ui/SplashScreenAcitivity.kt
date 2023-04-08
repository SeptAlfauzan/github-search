package com.example.githubprofile.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.githubprofile.R
import com.example.githubprofile.config.helper.ViewModelHelper

class SplashScreenAcitivity : AppCompatActivity() {

    private val SPLASH_TIME_OUT: Long = 1000
    override fun onCreate(savedInstanceState: Bundle?) {
        val viewModel = ViewModelHelper.obtainViewModel(this@SplashScreenAcitivity as AppCompatActivity)
        viewModel.getTheme().observe(this
        ) { isDark: Boolean ->
            if (isDark) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES) else AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_NO)
            delegate.applyDayNight()
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen_acitivity)

        window.navigationBarColor = resources.getColor(R.color.dark_primary)
        Handler().postDelayed({
            startActivity(Intent(this@SplashScreenAcitivity, MainActivity::class.java))
            finish()
        }, SPLASH_TIME_OUT)
    }
}