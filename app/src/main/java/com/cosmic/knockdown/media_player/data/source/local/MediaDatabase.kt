package com.cosmic.knockdown.media_player.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cosmic.knockdown.media_player.data.Audio

@Database(entities = [Audio::class], version = 1, exportSchema = false)
abstract class MediaDatabase : RoomDatabase() {
    abstract fun audioDao(): AudioDao
}