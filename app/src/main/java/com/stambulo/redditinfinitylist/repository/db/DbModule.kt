package com.stambulo.redditinfinitylist.repository.db

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DbModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): CacheDatabase =
        Room.databaseBuilder(context, CacheDatabase::class.java, "db.db")
            .fallbackToDestructiveMigration()
            .build()
}
