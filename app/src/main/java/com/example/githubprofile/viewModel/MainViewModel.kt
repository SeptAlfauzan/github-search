package com.example.githubprofile.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubprofile.Event
import com.example.githubprofile.config.ApiConfig
import com.example.githubprofile.interfaces.ApiServices
import com.example.githubprofile.response.SearchResponse
import com.example.githubprofile.response.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log

class MainViewModel: ViewModel() {

    val TAG = "MainViewModel"

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _users = MutableLiveData<SearchResponse>()
    val users: LiveData<SearchResponse> = _users


    private var _followers = MutableLiveData<List<UserResponse>>()
    val follower: LiveData<List<UserResponse>> = _followers

    private var _following = MutableLiveData<List<UserResponse>>()
    val following: LiveData<List<UserResponse>> = _following

    private var _notification = MutableLiveData<Event<String>>()
    val notification: LiveData<Event<String>> = _notification

    private var _user = MutableLiveData<UserResponse>()
    val user: LiveData<UserResponse> = _user

    fun findUsers(username: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getListUsers(username)
        client.enqueue(object: Callback<SearchResponse>{
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                _isLoading.value = false
                if(!response.isSuccessful){
                    Log.d(TAG, "onResponse: ${response.message()}")
                    _notification.value = Event("Error! ${response.message()}")
                }else{
                    response.body().let {
                        _users.value = it
                    }
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                _isLoading.value = false
                Log.d(TAG, "onFailure: ${t.message}")
                _notification.value = Event("Error! ${t.message.toString()}")
            }
        })
    }

    fun getFollower(username: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollower(username)
        client.enqueue(object: Callback<List<UserResponse>>{
            override fun onResponse(
                call: Call<List<UserResponse>>,
                response: Response<List<UserResponse>>
            ) {
                _isLoading.value = false
                
                if(!response.isSuccessful){
                    Log.d(TAG, "onResponse: ${response.message()}")
                    _notification.value = Event("Error! ${response.message()}")
                }else{
                    response.body().let { 
                        _followers.value = it
                    }
                }
            }

            override fun onFailure(call: Call<List<UserResponse>>, t: Throwable) {
                _isLoading.value = false
                Log.d(TAG, "onFailure: ${t.message}")
                _notification.value = Event("Error! ${ t.message.toString() }")
            }

        })
    }

    fun getFollowing(username: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(username)
        client.enqueue(object: Callback<List<UserResponse>>{
            override fun onResponse(
                call: Call<List<UserResponse>>,
                response: Response<List<UserResponse>>
            ) {
                _isLoading.value = false

                if(!response.isSuccessful){
                    Log.d(TAG, "onResponse: ${response.message()}")
                    _notification.value = Event("Error! ${response.message()}")
                }else{
                    response.body().let {
                        _following.value = it
                    }
                }
            }

            override fun onFailure(call: Call<List<UserResponse>>, t: Throwable) {
                _isLoading.value = false
                Log.d(TAG, "onFailure: ${t.message}")
                _notification.value = Event("Error! ${t.message.toString()}")
            }

        })
    }

    fun getUser(username: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUser(username)
        client.enqueue(object: Callback<UserResponse>{
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                _isLoading.value = false
                if (!response.isSuccessful){
                    Log.d(TAG, "onResponse: ${response.message()}")
                    _notification.value = Event("Error! ${response.message()}")
                }else{
                    response.body().let {
                        _user.value = it
                    }
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.d(TAG, "onFailure: ${t.message}")
                _notification.value = Event("Error! ${t.message.toString()}")
            }

        })
    }

}