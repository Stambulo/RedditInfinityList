package com.stambulo.redditinfinitylist.model.entity

import com.google.gson.annotations.Expose

data class RedditJSON(
    @Expose var data: Data,
    @Expose var kind: String
)