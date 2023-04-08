package com.example.githubprofile.database

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.githubprofile.Event
import com.example.githubprofile.config.ApiConfig
import com.example.githubprofile.model.Search
import com.example.githubprofile.model.User
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class UserRepository(application: Application) {
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    private val userDao: UserDao
    private val TAG = "UserRepository"

    init {
        val db = UserRoomDatabase.getDatabase(application)
        userDao = db.userDao()
    }

    fun addToFavorite(user: User){
        executorService.execute{
            userDao.insert(user)
        }
    }

    fun removeFromFavorite(user: User){
        executorService.execute{
            userDao.delete(user)
        }
    }

    fun getUserFromFavoriteList(user_id: Int): LiveData<User> = userDao.getUserById(user_id)

    fun getFavoriteUsers(): LiveData<List<User>> = userDao.getAllFavoriteUser()

    suspend fun findUsers(username: String, loading: MutableLiveData<Boolean>, search: MutableLiveData<Search>, notification: MutableLiveData<Event<String>>){
        loading.value = true
        try {
            val response = ApiConfig.getApiService().getListUsers(username)
            search.value = response
        } catch(t: Throwable) {
            Log.d(TAG, "onFailure: ${t.message}")
            notification.value = Event("Error! ${t.message.toString()}")
        } finally {
            loading.value = false
        }
    }

    suspend fun getFollower(username: String, loading: MutableLiveData<Boolean>, followers: MutableLiveData<List<User>>, notification: MutableLiveData<Event<String>>){
        loading.value = true
        try {
            val response = ApiConfig.getApiService().getFollower(username)
            followers.value = response
        } catch (t: Throwable) {
            Log.d(TAG, "onFailure: ${t.message}")
            notification.value = Event("Error! ${t.message.toString()}")
        }finally {
            loading.value = false
        }
    }

    suspend fun getFollowing(username: String, loading: MutableLiveData<Boolean>, following: MutableLiveData<List<User>>, notification: MutableLiveData<Event<String>>){
        loading.value = true
        try {
            val response = ApiConfig.getApiService().getFollowing(username)
            following.value = response
        } catch (t: Throwable) {
            Log.d(TAG, "onFailure: ${t.message}")
            notification.value = Event("Error! ${t.message.toString()}")
        } finally {
            loading.value = false
        }
    }

    suspend fun getUser(username: String, loading: MutableLiveData<Boolean>, currentUser: MutableLiveData<User>, notification: MutableLiveData<Event<String>>){
        loading.value = true
        try {
            val response = ApiConfig.getApiService().getUser(username)
            currentUser.value = response
        } catch (t: Throwable) {
            Log.d(TAG, "onFailure: ${t.message}")
            notification.value = Event("Error! ${t.message.toString()}")
        } finally {
            loading.value = false
        }
    }
}