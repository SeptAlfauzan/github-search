package com.example.githubprofile.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class User(

	@PrimaryKey(autoGenerate = true)
	@ColumnInfo(name="id")
	@field:SerializedName("id")
	val id: Int,

	@ColumnInfo(name="gists_url")
	@field:SerializedName("gists_url")
	val gistsUrl: String? = null,

	@field:SerializedName("repos_url")
	@ColumnInfo(name="repos_url")
	val reposUrl: String? = null,

	@field:SerializedName("following_url")
	@ColumnInfo(name="following_url")
	val followingUrl: String? = null,

	@field:SerializedName("twitter_username")
	@ColumnInfo(name="twitter_username")
	val twitterUsername: String? = null,

	@field:SerializedName("bio")
	@ColumnInfo(name="bio")
	val bio: String? = null,

	@field:SerializedName("created_at")
	@ColumnInfo(name="created_at")
	val createdAt: String? = null,

	@field:SerializedName("login")
	@ColumnInfo(name="login")
	val login: String? = null,

	@field:SerializedName("type")
	@ColumnInfo(name="type")
	val type: String? = null,

	@field:SerializedName("blog")
	@ColumnInfo(name="blog")
	val blog: String? = null,

	@field:SerializedName("subscriptions_url")
	@ColumnInfo(name="subscriptions_url")
	val subscriptionsUrl: String? = null,

	@field:SerializedName("updated_at")
	@ColumnInfo(name="updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("site_admin")
	@ColumnInfo(name="site_admin")
	val siteAdmin: Boolean? = null,

	@field:SerializedName("company")
	@ColumnInfo(name="company")
	val company: String? = null,

	@field:SerializedName("public_repos")
	@ColumnInfo(name="public_repos")
	val publicRepos: Int? = null,

	@field:SerializedName("gravatar_id")
	@ColumnInfo(name="gravatar_id")
	val gravatarId: String? = null,

	@field:SerializedName("email")
	@ColumnInfo(name="email")
	val email: String? = null,

	@field:SerializedName("organizations_url")
	@ColumnInfo(name="organizations_url")
	val organizationsUrl: String? = null,

	@field:SerializedName("hireable")
	@ColumnInfo(name="hireable")
	val hireable: Boolean? = null,

	@field:SerializedName("starred_url")
	@ColumnInfo(name="starred_url")
	val starredUrl: String? = null,

	@field:SerializedName("followers_url")
	@ColumnInfo(name="followers_url")
	val followersUrl: String? = null,

	@field:SerializedName("public_gists")
	@ColumnInfo(name="public_gists")
	val publicGists: Int? = null,

	@field:SerializedName("url")
	@ColumnInfo(name="url")
	val url: String? = null,

	@field:SerializedName("received_events_url")
	@ColumnInfo(name="received_events_url")
	val receivedEventsUrl: String? = null,

	@field:SerializedName("followers")
	@ColumnInfo(name="followers")
	val followers: Int? = null,

	@field:SerializedName("avatar_url")
	@ColumnInfo(name="avatar_url")
	val avatarUrl: String? = null,

	@field:SerializedName("events_url")
	@ColumnInfo(name="events_url")
	val eventsUrl: String? = null,

	@field:SerializedName("html_url")
	@ColumnInfo(name="html_url")
	val htmlUrl: String? = null,

	@field:SerializedName("following")
	@ColumnInfo(name="following")
	val following: Int? = null,

	@field:SerializedName("name")
	@ColumnInfo(name="name")
	val name: String? = null,

	@field:SerializedName("location")
	@ColumnInfo(name="location")
	val location: String? = null,

	@field:SerializedName("node_id")
	@ColumnInfo(name="node_id")
	val nodeId: String? = null
) : Parcelable