package com.stambulo.redditinfinitylist.repository

import com.stambulo.redditinfinitylist.api.MainRemoteData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository @Inject constructor(private val remoteData: MainRemoteData){
    suspend fun getPosts() = remoteData.getPosts()
}
