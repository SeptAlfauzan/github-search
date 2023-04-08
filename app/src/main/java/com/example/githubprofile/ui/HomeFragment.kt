package com.example.githubprofile.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubprofile.adapter.UserRecyclerViewAdapter
import com.example.githubprofile.config.helper.ViewModelHelper
import com.example.githubprofile.databinding.FragmentHomeBinding
import com.example.githubprofile.model.User
import com.example.githubprofile.ui.viewModel.MainViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = binding.recyclerviewContainer

        val rvAdapter = UserRecyclerViewAdapter(
            listOf(),
            onClick = { navigateToDetail(it) }
        )

        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView.apply {
            this.adapter = rvAdapter
            this.layoutManager = layoutManager
        }

        val viewModel: MainViewModel = ViewModelHelper.obtainViewModel(requireActivity() as AppCompatActivity)

        binding.searchUser.setIconifiedByDefault(false)
        binding.searchUser.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.findUsers(query ?: "")
                binding.searchUser.clearFocus()
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

        viewModel.isLoading.observe(viewLifecycleOwner){ isLoading(it) }
        viewModel.notification.observe(viewLifecycleOwner){
            it.getContentIfNotHandled()?.let {text ->
                Toast.makeText(requireContext(), text, Toast.LENGTH_LONG).show()
            }
        }
        viewModel.search.observe(viewLifecycleOwner){
            rvAdapter.updateData(it.items as List<User>)
            binding.textError.visibility = (if(it == null || it.items?.size == 0) View.VISIBLE else View.GONE)
        }
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
            binding.searchLoading.visibility = View.VISIBLE
            binding.recyclerviewContainer.visibility = View.GONE
            binding.textError.visibility  = View.GONE
        }else{
            binding.searchLoading.visibility = View.GONE
            binding.recyclerviewContainer.visibility = View.VISIBLE
        }
    }
}