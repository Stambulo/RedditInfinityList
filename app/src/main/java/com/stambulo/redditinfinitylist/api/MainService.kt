package com.stambulo.redditinfinitylist.api

import com.stambulo.redditinfinitylist.model.entity.RedditJSON
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MainService {
    @GET("hot.json") suspend fun getPosts(
        @Query("limit") limit: Int = 20,
        @Query("after") after: String? = null
    ): Response<RedditJSON>
}
