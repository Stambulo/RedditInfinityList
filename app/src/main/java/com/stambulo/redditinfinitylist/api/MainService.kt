package com.stambulo.redditinfinitylist.api

import com.stambulo.redditinfinitylist.model.entity.RedditJSON
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import dagger.hilt.android.components.ActivityComponent

interface MainService {

    @GET("hot.json") suspend fun getPosts(
        @Query("limit") limit: Int = 0,
        @Query("after") after: String? = null
    ): Response<RedditJSON>
}
