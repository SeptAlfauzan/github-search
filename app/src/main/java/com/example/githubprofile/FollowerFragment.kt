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
import com.example.githubprofile.databinding.FragmentFollowerBinding
import com.example.githubprofile.viewModel.MainViewModel

class FollowerFragment : Fragment() {

    private var _binding: FragmentFollowerBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()

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


        viewModel.follower.observe(viewLifecycleOwner){
            adapter.updateData(it)
        }
        viewModel.isLoading.observe(viewLifecycleOwner){ isLoading(it) }

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
            binding.followerLoading.visibility = View.GONE
            binding.recyclerviewFollowerContainer.visibility = View.VISIBLE
        }
    }
}