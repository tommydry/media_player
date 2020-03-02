package com.cosmic.knockdown.media_player.data.source

import androidx.lifecycle.LiveData
import com.cosmic.knockdown.media_player.data.Audio
import com.cosmic.knockdown.media_player.data.Result
import kotlinx.coroutines.*
import java.lang.Exception

class DefaultAudioRepository(
    private val audioLocalDataSource: AudioDataSource,
    private val audioRemoteDataSource: AudioDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : AudioRepository {

    override fun observeAllAudio(): LiveData<Result<List<Audio>>> {
        return audioLocalDataSource.observeAllAudio()
    }

    override suspend fun getAllAudio(forceUpdate: Boolean): Result<List<Audio>> {
        if (forceUpdate) {
            try {
                updateAudioFromRemoteDataSource()
            } catch (ex: Exception) {
                return Result.Error(ex)
            }
        }
        return audioLocalDataSource.getAllAudio()
    }

    override suspend fun refreshAllAudio() {
        updateAudioFromRemoteDataSource()
    }

    override fun observeAudio(audioId: Int): LiveData<Result<Audio>> {
        return audioLocalDataSource.observeAudio(audioId)
    }

    override suspend fun getAudio(audioId: Int, forceUpdate: Boolean): Result<Audio> {
        if (forceUpdate) {
            updateOneAudioItemFromRemoteDataSource(audioId)
        }

        return audioLocalDataSource.getAudio(audioId)
    }

    override suspend fun refreshAudio(audioId: Int) {
        updateOneAudioItemFromRemoteDataSource(audioId)
    }

    override suspend fun deleteAllAudio() {
        withContext(ioDispatcher) {
            coroutineScope {
                launch { audioLocalDataSource.deleteAllAudio() }
            }
        }
    }

    override suspend fun deleteAudio(audioId: Int) {
        withContext(ioDispatcher) {
            coroutineScope {
                launch { audioLocalDataSource.deleteAudio(audioId) }
            }
        }
    }

    private suspend fun updateAudioFromRemoteDataSource() {
        val remoteAudio = audioRemoteDataSource.getAllAudio()
        if (remoteAudio is Result.Success) {
            audioLocalDataSource.deleteAllAudio()
            remoteAudio.data.forEach { audio ->
                audioLocalDataSource.saveAudio(audio)
            }
        } else if (remoteAudio is Result.Error) {
            throw remoteAudio.exception
        }
    }

    private suspend fun updateOneAudioItemFromRemoteDataSource(audioId: Int) {
        val remoteAudio = audioRemoteDataSource.getAudio(audioId)
        if (remoteAudio is Result.Success) {
            audioRemoteDataSource.saveAudio(remoteAudio.data)
        }
    }

}