package com.cosmic.knockdown.media_player.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cosmic.knockdown.media_player.data.Audio

@Dao
interface AudioDao {
    @Query("Select * from Audio")
    fun observeAllData(): LiveData<List<Audio>>

    @Query("Select * from Audio")
    fun getAllData(): List<Audio>

    @Query("Select * from Audio where id = :id")
    fun observeAudioById(id: Int): LiveData<Audio>

    @Query("Select * from Audio where id = :id")
    fun getAudioById(id: Int): Audio

    @Query("Delete from Audio")
    fun deleteAllAudio()

    @Query("Delete from Audio where id = :id")
    fun deleteAudioById(id: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAudio(audio: Audio)
}