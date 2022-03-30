package com.stambulo.redditinfinitylist.api

import javax.inject.Inject

class MainRemoteData @Inject constructor(private val mainService: MainService) {
    suspend fun getPosts() = mainService.getPosts()
}
