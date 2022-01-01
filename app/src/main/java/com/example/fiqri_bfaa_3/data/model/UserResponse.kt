package com.example.fiqri_bfaa_3.data.model

import com.google.gson.annotations.SerializedName

data class UserResponse(

    @field:SerializedName("items")
    val items: List<User>
)

data class User(


    @field:SerializedName("public_repos")
    val publicRepos: Int?,

    @field:SerializedName("following_url")
    val followingUrl: String?,


    @field:SerializedName("login")
    val login: String?,

    @field:SerializedName("followers_url")
    val followersUrl: String?,


    @field:SerializedName("avatar_url")
    val avatarUrl: String?,


    @field:SerializedName("id")
    val id: Int?,

)

