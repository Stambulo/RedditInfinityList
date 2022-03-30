package com.stambulo.redditinfinitylist.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PageKey(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var after: String?
)
