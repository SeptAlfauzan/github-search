package com.example.githubprofile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubprofile.adapter.UserRecyclerViewAdapter
import com.example.githubprofile.databinding.FragmentHomeBinding
import com.example.githubprofile.response.UserResponse
import com.example.githubprofile.viewModel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    val TAG = "HomeFragment"

    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
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


        viewModel.users.observe(viewLifecycleOwner) {it ->
            rvAdapter.updateData(it.items as List<UserResponse>)
            binding.searchError.textError.visibility = (if(it == null || it.items?.size == 0) View.VISIBLE else View.GONE)
        }
        viewModel.isLoading.observe(viewLifecycleOwner){ isLoading(it) }
        viewModel.notification.observe(viewLifecycleOwner){
            it.getContentIfNotHandled()?.let {text ->
                Toast.makeText(requireContext(), text, Toast.LENGTH_LONG).show()
            }
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
            binding.searchError.textError.visibility = View.GONE
        }else{
            binding.searchLoading.visibility = View.GONE
            binding.recyclerviewContainer.visibility = View.VISIBLE
        }
    }
}