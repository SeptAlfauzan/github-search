package com.example.githubprofile.interfaces

import com.example.githubprofile.response.SearchResponse
import com.example.githubprofile.response.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {

    @Headers("Authorization: token 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")
    @GET("/search/users")
    fun getListUsers(@Query("q") username : String): Call<SearchResponse>

    @Headers("Authorization: token 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")
    @GET("/users/{username}/followers")
    fun getFollower(@Path("username") username : String): Call<List<UserResponse>>

    @Headers("Authorization: token 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")
    @GET("/users/{username}/following")
    fun getFollowing(@Path("username") username : String): Call<List<UserResponse>>

    @Headers("Authorization: token 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")
    @GET("/users/{username}")
    fun getUser(@Path("username") username : String): Call<UserResponse>


}