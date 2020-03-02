package com.cosmic.knockdown.media_player.data.source.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.cosmic.knockdown.media_player.data.Audio
import com.cosmic.knockdown.media_player.data.Result
import com.cosmic.knockdown.media_player.data.source.AudioDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AudioLocalDataSource(
    private val audioDao: AudioDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : AudioDataSource {

    override fun observeAllAudio(): LiveData<Result<List<Audio>>> {
        return audioDao.observeAllData().map {
            Result.Success(it)
        }
    }

    override suspend fun getAllAudio(): Result<List<Audio>> = withContext(ioDispatcher) {
        return@withContext try {
            Result.Success(audioDao.getAllData())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun refreshAllAudio() {
        //do nothing
    }

    override fun observeAudio(audioId: Int): LiveData<Result<Audio>> {
        return audioDao.observeAudioById(audioId).map {
            Result.Success(it)
        }
    }

    override suspend fun getAudio(audioId: Int): Result<Audio> = withContext(ioDispatcher) {
        return@withContext try {
            Result.Success(audioDao.getAudioById(audioId))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun refreshAudio(audioId: Int) {
        //do nothing
    }

    override suspend fun deleteAllAudio() = withContext(ioDispatcher) {
        audioDao.deleteAllAudio()
    }

    override suspend fun deleteAudio(audioId: Int) = withContext(ioDispatcher) {
        audioDao.deleteAudioById(audioId)
    }

    override suspend fun saveAudio(audio: Audio) = withContext(ioDispatcher) {
        audioDao.saveAudio(audio)
    }

}