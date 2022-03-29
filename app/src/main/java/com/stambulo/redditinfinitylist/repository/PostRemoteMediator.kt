package com.stambulo.redditinfinitylist.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.stambulo.redditinfinitylist.api.MainService
import com.stambulo.redditinfinitylist.model.entity.DataX
import com.stambulo.redditinfinitylist.model.entity.PageKey
import com.stambulo.redditinfinitylist.repository.db.CacheDatabase
import retrofit2.HttpException
import java.io.IOException

@ExperimentalPagingApi
class PostRemoteMediator(
    private val api: MainService,
    private val db: CacheDatabase

) : RemoteMediator<Int, DataX>() {
    override suspend fun load(loadType: LoadType, state: PagingState<Int, DataX>): MediatorResult =
        when (loadType) {
            LoadType.REFRESH -> refresh(state)
            LoadType.PREPEND -> prepend()
            LoadType.APPEND -> append(state)
        }

    private suspend fun append(state: PagingState<Int, DataX>): MediatorResult {
        return try {
            val postKey = db.dao.getPageKeys().lastOrNull()
            val apiResponse =
                api.getPosts(
                    limit = state.config.pageSize,
                    after = postKey?.after
                )
            if (apiResponse.isSuccessful) {
                val response = apiResponse.body()?.data
                val posts = response?.children?.map { it.data }
                posts?.let {
                    db.dao.insertPostAndKey(
                        PageKey(0, response.after),
                        posts)
                }
                MediatorResult.Success(endOfPaginationReached = posts.isNullOrEmpty())
            } else {
                MediatorResult.Error(HttpException(apiResponse))
            }
        } catch (exception: IOException) {
            MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            MediatorResult.Error(exception)
        }
    }

    private fun prepend(): MediatorResult {
        return MediatorResult.Success(endOfPaginationReached = true)
    }

    private suspend fun refresh(state: PagingState<Int, DataX>): MediatorResult {
        return try {
            val apiResponse = api.getPosts(limit = state.config.pageSize)
            if (apiResponse.isSuccessful) {
                db.dao.deleteAll()
                val response = apiResponse.body()?.data
                val posts = response?.children?.map { it.data }
                posts?.let {
                    db.dao.insertPostAndKey(
                        PageKey(0, response.after),
                        posts
                    )
                    MediatorResult.Success(endOfPaginationReached = it.isEmpty())
                }
                MediatorResult.Success(endOfPaginationReached = false)
            } else {
                MediatorResult.Error(HttpException(apiResponse))
            }
        } catch (exception: IOException) {
            MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            MediatorResult.Error(exception)
        }
    }
}
