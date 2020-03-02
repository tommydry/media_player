package com.cosmic.knockdown.media_player.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cosmic.knockdown.media_player.data.Audio
import com.cosmic.knockdown.media_player.data.Result
import com.cosmic.knockdown.media_player.data.source.AudioDataSource
import com.cosmic.knockdown.media_player.MainApplication
import java.lang.Exception

class AudioRemoteDataSource(private val mediaApi: MediaService) : AudioDataSource {

    override fun observeAllAudio(): LiveData<Result<List<Audio>>> {
        return MutableLiveData<Result<List<Audio>>>()
    }

    override suspend fun getAllAudio(): Result<List<Audio>> {
        return try {
            val result = Result.Success(mediaApi.getAllAudio().media)
            result
        } catch (ex: Exception) {
            Log.d(MainApplication.TAG, "Can't grab remote audio ${ex.message}")
            ex.printStackTrace()
            Result.Error(ex)
        }
    }

    override suspend fun refreshAllAudio() {
        //do nothing pal ¯\_(ツ)_/¯¯
    }

    override fun observeAudio(audioId: Int): LiveData<Result<Audio>> {
        return MutableLiveData<Result<Audio>>()
    }

    override suspend fun getAudio(audioId: Int): Result<Audio> {
        return Result.Error(Exception("It shouldn't be called"))
    }

    override suspend fun refreshAudio(audioId: Int) {
        //do nothing pal ¯\_(ツ)_/¯¯
    }

    override suspend fun deleteAllAudio() {
        //do nothing pal ¯\_(ツ)_/¯¯
    }

    override suspend fun deleteAudio(audioId: Int) {
        //do nothing pal ¯\_(ツ)_/¯¯
    }

    override suspend fun saveAudio(audio: Audio) {
        //do nothing pal ¯\_(ツ)_/¯¯
    }

}