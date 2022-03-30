package com.stambulo.redditinfinitylist.repository

import com.stambulo.redditinfinitylist.api.MainService
import com.stambulo.redditinfinitylist.repository.db.CacheDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
class RepoModule {
    @Singleton
    @Provides
    fun provideRepo(api: MainService, db: CacheDatabase): RedditRepo = RedditRepoImp(api, db)
}
