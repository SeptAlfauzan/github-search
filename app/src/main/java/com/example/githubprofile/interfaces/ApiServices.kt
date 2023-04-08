package com.example.githubprofile.interfaces

import com.example.githubprofile.model.Search
import com.example.githubprofile.model.User
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {
    @GET("/search/users")
    suspend fun getListUsers(@Query("q") username : String): Search
    @GET("/users/{username}/followers")
    suspend fun getFollower(@Path("username") username : String): List<User>
    @GET("/users/{username}/following")
    suspend fun getFollowing(@Path("username") username : String): List<User>
    @GET("/users/{username}")
    suspend fun getUser(@Path("username") username : String): User
}