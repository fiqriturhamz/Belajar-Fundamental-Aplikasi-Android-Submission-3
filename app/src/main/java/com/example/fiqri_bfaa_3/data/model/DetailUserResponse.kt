package com.example.fiqri_bfaa_3.data.model
//nama variable tidak bisa diubah karena berdasarkan endpoint dari Github Api,jika diubah terjadi error
data class DetailUserResponse(
    val login: String,
    val id: Int,
    val avatar_url: String,
    val followers_url: String,
    val following_url: String,
    val name: String,
    val following: Int,
    val followers: Int,
    val public_repos: Int,
    val company: String,
    val location: String
)
