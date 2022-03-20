package com.stambulo.redditinfinitylist.model.entity

data class Image(
    val id: String,
    val resolutions: List<Resolution>,
    val source: Source,
    val variants: Variants
)