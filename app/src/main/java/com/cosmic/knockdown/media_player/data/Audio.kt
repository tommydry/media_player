package com.cosmic.knockdown.media_player.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Audio(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "type") val type: String?,
    @ColumnInfo(name = "author") val author: String?,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "url") val url: String?
)