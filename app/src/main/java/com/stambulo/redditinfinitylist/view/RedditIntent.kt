package com.stambulo.redditinfinitylist.view

sealed class RedditIntent {
    object FetchNews: RedditIntent()
}
