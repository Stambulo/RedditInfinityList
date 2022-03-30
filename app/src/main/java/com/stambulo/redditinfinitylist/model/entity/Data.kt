package com.stambulo.redditinfinitylist.model.entity

import com.google.gson.annotations.Expose

data class Data(
    @Expose var after: String?,
//    val before: Any,
    @Expose var children: List<Children>,
//    val dist: Int,
//    val geo_filter: Any,
//    val modhash: String
)