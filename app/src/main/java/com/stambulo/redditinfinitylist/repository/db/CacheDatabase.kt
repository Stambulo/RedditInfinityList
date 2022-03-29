package com.stambulo.redditinfinitylist.repository.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.stambulo.redditinfinitylist.model.entity.DataX
import com.stambulo.redditinfinitylist.model.entity.PageKey

@Database(entities = [PageKey::class, DataX::class], version = 1, exportSchema = false)
abstract class CacheDatabase : RoomDatabase() {

    abstract val dao: CacheDao
}
