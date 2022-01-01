package com.example.fiqri_bfaa_3.api


import com.example.fiqri_bfaa_3.data.model.DetailUserResponse
import com.example.fiqri_bfaa_3.data.model.User
import com.example.fiqri_bfaa_3.data.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET("search/users")
    @Headers("Authorization:token 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")
    fun getSearchUser(
        @Query("q") query: String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization:token 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")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization:token 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")
    fun getFollowers(
        @Path("username") username: String
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization:token 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")
    fun getFollowing(
        @Path("username") username: String
    ): Call<ArrayList<User>>


}