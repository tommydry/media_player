package com.cosmic.knockdown.media_player.data.source

import com.cosmic.knockdown.media_player.data.Audio
import com.cosmic.knockdown.media_player.data.Result

interface AudioDataSource {
    fun observeAllAudio(): androidx.lifecycle.LiveData<Result<List<Audio>>>

    suspend fun getAllAudio(): com.cosmic.knockdown.media_player.data.Result<List<Audio>>

    suspend fun refreshAllAudio()

    fun observeAudio(audioId: Int): androidx.lifecycle.LiveData<Result<Audio>>

    suspend fun getAudio(audioId: Int): com.cosmic.knockdown.media_player.data.Result<Audio>

    suspend fun refreshAudio(audioId: Int)

    suspend fun deleteAllAudio()

    suspend fun deleteAudio(audioId: Int)

    suspend fun saveAudio(audio: Audio)
}