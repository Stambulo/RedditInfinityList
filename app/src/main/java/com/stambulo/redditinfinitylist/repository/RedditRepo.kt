package com.stambulo.redditinfinitylist.repository

import androidx.paging.PagingData
import com.stambulo.redditinfinitylist.model.entity.DataX
import kotlinx.coroutines.flow.Flow

interface RedditRepo {
    fun getPosts(): Flow<PagingData<DataX>>
}
