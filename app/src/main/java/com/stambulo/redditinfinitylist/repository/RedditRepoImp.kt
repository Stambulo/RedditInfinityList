package com.stambulo.redditinfinitylist.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.stambulo.redditinfinitylist.api.MainService
import com.stambulo.redditinfinitylist.model.entity.DataX
import com.stambulo.redditinfinitylist.repository.db.CacheDatabase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RedditRepoImp @Inject constructor(private val api: MainService, private val db: CacheDatabase) : RedditRepo {

    @OptIn(ExperimentalPagingApi::class)
    override fun getPosts(): Flow<PagingData<DataX>> {
        return Pager(
            PagingConfig(pageSize = 20, enablePlaceholders = false, prefetchDistance = 3),
            remoteMediator = PostRemoteMediator(api, db),
            pagingSourceFactory = {db.dao.getPosts()}
        ).flow
    }
}
