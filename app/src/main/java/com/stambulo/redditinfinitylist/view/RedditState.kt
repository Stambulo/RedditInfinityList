package com.stambulo.redditinfinitylist.view

import com.stambulo.redditinfinitylist.model.entity.RedditJSON
import retrofit2.Response

sealed class RedditState {
    object Idle: RedditState()
    object Loading: RedditState()
    data class Error(val error: String?): RedditState()
    data class NewsSuccess(val success: Response<RedditJSON>): RedditState()
}
