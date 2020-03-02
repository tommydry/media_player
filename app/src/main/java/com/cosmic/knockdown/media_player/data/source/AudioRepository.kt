package com.cosmic.knockdown.media_player.data.source

import androidx.lifecycle.LiveData
import com.cosmic.knockdown.media_player.data.Audio
import com.cosmic.knockdown.media_player.data.Result

interface AudioRepository {
    fun observeAllAudio(): LiveData<Result<List<Audio>>>

    suspend fun getAllAudio(forceUpdate: Boolean): com.cosmic.knockdown.media_player.data.Result<List<Audio>>

    suspend fun refreshAllAudio()

    fun observeAudio(audioId: Int): LiveData<Result<Audio>>

    suspend fun getAudio(audioId: Int, forceUpdate: Boolean): com.cosmic.knockdown.media_player.data.Result<Audio>

    suspend fun refreshAudio(audioId: Int)

    suspend fun deleteAllAudio()

    suspend fun deleteAudio(audioId: Int)
}