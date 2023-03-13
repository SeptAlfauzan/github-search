package com.example.githubprofile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubprofile.adapter.UserRecyclerViewAdapter
import com.example.githubprofile.databinding.FragmentFollowingBinding
import com.example.githubprofile.response.UserResponse
import com.example.githubprofile.viewModel.MainViewModel

class FollowingFragment : Fragment() {

    lateinit var binding: FragmentFollowingBinding
    val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowingBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
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


        viewModel.following.observe(viewLifecycleOwner){
            adapter.updateData(it)
        }

        viewModel.isLoading.observe(viewLifecycleOwner){ isLoading(it) }

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
            binding.followingLoading.visibility = View.GONE
            binding.recyclerviewFollowingContainer.visibility = View.VISIBLE
        }
    }
}