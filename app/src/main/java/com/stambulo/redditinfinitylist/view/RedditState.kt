package com.stambulo.redditinfinitylist.view

import androidx.paging.PagingData
import com.stambulo.redditinfinitylist.model.entity.DataX
import kotlinx.coroutines.flow.Flow

sealed class RedditState {
    object Idle: RedditState()
    object Loading: RedditState()
    data class Error(val error: String?): RedditState()
    data class NewsSuccess(val success: Flow<PagingData<DataX>>): RedditState()
}
