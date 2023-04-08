package com.example.githubprofile.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.githubprofile.ui.FollowerFragment
import com.example.githubprofile.ui.FollowingFragment

class DetailUserSectionPagerAdapter(activity: AppCompatActivity, val arg: Bundle? = null): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return ( when(position){
            0 -> FollowerFragment().apply { if(arg != null) arguments = arg }
            1 -> FollowingFragment().apply { if(arg != null) arguments = arg }
            else -> null
        }) as Fragment
    }
}