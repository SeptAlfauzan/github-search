package com.example.githubprofile.ui.viewModel

import android.app.Application
import androidx.lifecycle.*
import com.example.githubprofile.Event
import com.example.githubprofile.database.UserRepository
import com.example.githubprofile.datastore.SettingPreferences
import com.example.githubprofile.model.Search
import com.example.githubprofile.model.User
import kotlinx.coroutines.launch

class MainViewModel(private val application: Application, private val pref: SettingPreferences) :
    ViewModel() {
    private val repository = UserRepository(application)
    private var _isLoading = MutableLiveData<Boolean>()
    private var _isFollowerLoading = MutableLiveData<Boolean>()
    private var _isFollowingLoading = MutableLiveData<Boolean>()
    private var _notification = MutableLiveData<Event<String>>()
    private var _search = MutableLiveData<Search>()
    private var _currentUser = MutableLiveData<User>()
    private var _followers = MutableLiveData<List<User>>()
    private var _following = MutableLiveData<List<User>>()

    val isLoading: LiveData<Boolean> = _isLoading
    val isFollowerLoading: LiveData<Boolean> = _isFollowerLoading
    val isFollowingLoading: LiveData<Boolean> = _isFollowingLoading
    val notification: LiveData<Event<String>> = _notification
    val search: LiveData<Search> = _search
    val currentUser: LiveData<User> = _currentUser
    val follower: LiveData<List<User>> = _followers
    val following: LiveData<List<User>> = _following

    fun getTheme(): LiveData<Boolean> =
        pref.getThemeSetting(application.applicationContext).asLiveData()

    fun setTheme(isDark: Boolean) {
        viewModelScope.launch {
            pref.setTheme(application.applicationContext, isDark)
        }
    }

    fun findUsers(username: String) {
        viewModelScope.launch {

            repository.findUsers(username, _isLoading, _search, _notification)
        }
    }

    fun getFollower(username: String) {
        viewModelScope.launch {
            repository.getFollower(username, _isFollowerLoading, _followers, _notification)
        }
    }

    fun getFollowing(username: String) {
        viewModelScope.launch {
            repository.getFollowing(username, _isFollowingLoading, _following, _notification)
        }
    }

    fun getUser(username: String) {
        viewModelScope.launch {
            repository.getUser(username, _isLoading, _currentUser, _notification)
        }
    }

    fun getFavoriteUsers(): LiveData<List<User>> = repository.getFavoriteUsers()
    fun checkIsFavorite(id: Int): LiveData<User> = repository.getUserFromFavoriteList(id)

    fun toggleFavorite(isFavorite: Boolean, user: User) {
        if (isFavorite) {
            repository.addToFavorite(user)
            _notification.value = Event("${user.name} added to favorite 👍")
        } else {
            repository.removeFromFavorite(user)
            _notification.value = Event("${user.name} removed from favorite 💔")
        }
    }
}