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
import com.example.githubprofile.databinding.FragmentFollowerBinding

class FollowerFragment : Fragment() {

    private var _binding: FragmentFollowerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowerBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = arguments?.getString(DetailUserActivity.USERNAME)

        val layoutManager = LinearLayoutManager(requireContext())
        val adapter = UserRecyclerViewAdapter(
            listOf(),
            onClick = { navigateToDetail(it, binding.root) }
        )

        val recyclerView = binding.recyclerviewFollowerContainer
        recyclerView.apply {
            this.adapter = adapter
            this.layoutManager = layoutManager
        }

        val viewModel = ViewModelHelper.obtainViewModel(requireActivity() as AppCompatActivity)

        viewModel.isFollowerLoading.observe(viewLifecycleOwner){ isLoading(it) }
        viewModel.follower.observe(viewLifecycleOwner){ adapter.updateData(it) }

        if(viewModel.follower.value == null){
            viewModel.getFollower(username ?: "")
        }

        binding.followerError.textError.visibility = (if(viewModel.follower.value == null) View.VISIBLE else View.GONE)
    }

    private fun navigateToDetail(userId: String, view: View){

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
            binding.followerLoading.visibility = View.VISIBLE
            binding.recyclerviewFollowerContainer.visibility = View.GONE
            binding.followerError.textError.visibility = View.GONE
        }else{
            binding.followerLoading.visibility = View.INVISIBLE
            binding.recyclerviewFollowerContainer.visibility = View.VISIBLE
        }
    }
}