package com.stambulo.redditinfinitylist.repository.db

import androidx.paging.PagingSource
import androidx.room.*
import com.stambulo.redditinfinitylist.model.entity.DataX
import com.stambulo.redditinfinitylist.model.entity.PageKey

@Dao
abstract class CacheDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertKey(pageKey: PageKey)

    @Query("SELECT * FROM PageKey")
    abstract suspend fun getPageKeys(): List<PageKey>

    @Query("DELETE  FROM PageKey")
    abstract suspend fun deleteAllKey()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertPosts(posts: List<DataX>)

    @Query("SELECT * FROM DataX")
    abstract  fun getPosts(): PagingSource<Int, DataX>

    @Query("DELETE  FROM DataX")
    abstract suspend fun deleteAllPosts()

    @Transaction
    open suspend fun insertPostAndKey(pageKey: PageKey, posts: List<DataX>) {
        insertKey(pageKey)
        insertPosts(posts)
    }

    @Transaction
    open suspend  fun deleteAll() {
        deleteAllPosts()
        deleteAllKey()
    }
}
