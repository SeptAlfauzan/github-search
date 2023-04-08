package com.example.githubprofile.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubprofile.adapter.UserRecyclerViewAdapter
import com.example.githubprofile.config.helper.ViewModelHelper
import com.example.githubprofile.databinding.FragmentFollowingBinding

class FollowingFragment : Fragment() {

    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = arguments?.getString(DetailUserActivity.USERNAME)

        val layoutManager = LinearLayoutManager(requireContext())
        val adapter = UserRecyclerViewAdapter(
            listOf(),
            onClick = { navigateToDetail(it) }
        )

        val recyclerView = binding.recyclerviewFollowingContainer
        recyclerView.apply {
            this.adapter = adapter
            this.layoutManager = layoutManager
        }

        val viewModel = ViewModelHelper.obtainViewModel(requireActivity() as AppCompatActivity)

        viewModel.isFollowingLoading.observe(viewLifecycleOwner){ isLoading(it) }
        viewModel.following.observe(viewLifecycleOwner){ adapter.updateData(it) }

        if(viewModel.following.value == null){
            viewModel.getFollowing(username ?: "")
        }

        binding.followingError.textError.visibility = (if(viewModel.following.value == null) View.VISIBLE else View.GONE)
    }
    private fun navigateToDetail(userId: String){
        val bundle = Bundle().apply {
            putString(DetailUserActivity.USERNAME, userId)
        }
        val intent = Intent(requireActivity(), DetailUserActivity::class.java).apply{
            putExtras(bundle)
        }
        startActivity(intent)
    }
    private fun isLoading(status: Boolean){
        if(status){
            binding.followingLoading.visibility = View.VISIBLE
            binding.recyclerviewFollowingContainer.visibility = View.GONE
            binding.followingError.textError.visibility = View.GONE
        }else{
            binding.followingLoading.visibility = View.INVISIBLE
            binding.recyclerviewFollowingContainer.visibility = View.VISIBLE
        }
    }
}