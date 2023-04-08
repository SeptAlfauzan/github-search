package com.example.githubprofile.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubprofile.R
import com.example.githubprofile.adapter.UserRecyclerViewAdapter
import com.example.githubprofile.config.helper.ViewModelHelper
import com.example.githubprofile.databinding.ActivityFavoriteUsersBinding

class FavoriteUsersActivity : AppCompatActivity() {
    private var _binding: ActivityFavoriteUsersBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFavoriteUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        val rvAdapter = UserRecyclerViewAdapter(listOf(), onClick = { navigateToDetail(it) })
        val recyclerview = binding.recyclerviewContainer

        recyclerview.apply {
            this.layoutManager = layoutManager
            this.adapter = rvAdapter
        }

        val viewModel = ViewModelHelper.obtainViewModel(this as AppCompatActivity)

        viewModel.getFavoriteUsers().observe(this){
            binding.textError.visibility = if(it.isEmpty()) View.VISIBLE else View.GONE
            rvAdapter.updateData(it)
        }
        setTitle(getString(R.string.favorite_title))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.setting_button, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            R.id.open_setting -> {
                val intent = Intent(this@FavoriteUsersActivity, SettingActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> return false
        }
    }

    private fun navigateToDetail(userId: String){
        val bundle = Bundle().apply {
            putString(DetailUserActivity.USERNAME, userId)
        }
        val intent = Intent(this, DetailUserActivity::class.java).apply{
            putExtras(bundle)
        }
        startActivity(intent)
    }
}