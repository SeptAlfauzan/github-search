package com.example.githubprofile.ui

import android.os.Bundle
import android.view.MenuItem
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.githubprofile.config.helper.ViewModelHelper
import com.example.githubprofile.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {

    private var _binding: ActivitySettingBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val viewModel = ViewModelHelper.obtainViewModel(this@SettingActivity as AppCompatActivity)

        binding.themeSwitch.setOnCheckedChangeListener { _: CompoundButton, isChecked: Boolean ->
            viewModel.setTheme(isChecked)
        }

        viewModel.getTheme().observe(this
        ) { isDark: Boolean ->
            binding.themeSwitch.isChecked = isDark
            if (isDark) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES) else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        setTitle("Setting")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            else -> return false
        }
    }
}