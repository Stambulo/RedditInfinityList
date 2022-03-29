package com.stambulo.redditinfinitylist.model.entity

import com.google.gson.annotations.Expose

data class Children(
    @Expose var data: DataX,
    @Expose var kind: String
)