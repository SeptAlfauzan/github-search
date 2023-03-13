package com.example.githubprofile

import android.content.res.Configuration
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.example.githubprofile.adapter.DetailUserSectionPagerAdapter
import com.example.githubprofile.databinding.ActivityDetailUserBinding
import com.example.githubprofile.response.UserResponse
import com.example.githubprofile.viewModel.MainViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {

    companion object{
        val TAG = "DetailUserActivity"
        val TAB_TITLES = listOf<String>(
            "Follower",
            "Following"
        )
        val USERNAME = "username"
    }
    lateinit var binding: ActivityDetailUserBinding

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailUserBinding.inflate(layoutInflater)

        setContentView(binding.root)
        val bundle = intent.extras
        val username = bundle?.getString(USERNAME)

        val argsToSectionPager = Bundle().apply {
            putString(USERNAME, username)
        }

        val sectionPagerAdapter = DetailUserSectionPagerAdapter(this, argsToSectionPager)
        val viewPager = binding.viewPager

        viewPager.adapter = sectionPagerAdapter
        val tabs: TabLayout = binding.tabs

        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = TAB_TITLES[position]
        }.attach()


        viewModel.user.observe(this) {
            setDetailData(it)
        }
        viewModel.isLoading.observe(this){ isLoading(it) }
        viewModel.notification.observe(this){
            it.getContentIfNotHandled()?.let {text ->
                Toast.makeText(this, text, Toast.LENGTH_LONG).show()
            }
        }

        if(viewModel.user.value == null){
            username?.let { user -> viewModel.getUser(user) }
        }
    }

    private fun isLoading(status: Boolean){
        if(status){
            binding.detailLoading.visibility = View.VISIBLE
            binding.detailMainContainer.visibility = View.GONE
        }else{
            binding.detailLoading.visibility = View.GONE
            binding.detailMainContainer.visibility = View.VISIBLE
        }
    }

    private fun setDetailData(data: UserResponse){
        binding.cardDetail.detailName.text = data.name
        binding.cardDetail.detailUsername.text = data.login

        Glide.with(this).load(data.avatarUrl).into(
            binding.cardDetail.detailUserAvatar
        )

        binding.followerInfo.textview.text = "${data.followers} Follower"
        binding.followingInfo.textview.text = "${data.following} Following"
        binding.detailDescription.text = data.bio ?: "-"
        binding.emailInfo.emailUser.text = data.email ?: "-"
        binding.twitterInfo.twitterUser.text = data.twitterUsername ?: "-"
    }
}