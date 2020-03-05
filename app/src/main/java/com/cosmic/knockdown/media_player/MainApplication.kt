package com.cosmic.knockdown.media_player

import android.app.Application
import androidx.room.Room
import com.cosmic.knockdown.media_player.data.source.AudioRepository
import com.cosmic.knockdown.media_player.data.source.DefaultAudioRepository
import com.cosmic.knockdown.media_player.data.source.local.AudioLocalDataSource
import com.cosmic.knockdown.media_player.data.source.local.MediaDatabase
import com.cosmic.knockdown.media_player.data.source.remote.AudioRemoteDataSource
import com.cosmic.knockdown.media_player.data.source.remote.MediaService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainApplication : Application() {
    lateinit var audioRepository: AudioRepository

    override fun onCreate() {
        super.onCreate()
        val database =
            Room.databaseBuilder(applicationContext, MediaDatabase::class.java, "media-database")
                .build()


        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(loggingInterceptor)
        val retrofit = Retrofit.Builder().baseUrl("https://tommydry.github.io/")
            .addConverterFactory(GsonConverterFactory.create()).client(builder.build())
            .build()

        val mediaService = retrofit.create(MediaService::class.java)

        val audioRemoteDataSource = AudioRemoteDataSource(mediaService)
        val audioLocalDataSource = AudioLocalDataSource(database.audioDao())

        audioRepository = DefaultAudioRepository(
            audioLocalDataSource,
            audioRemoteDataSource
        )
    }

    companion object {
        const val TAG = "MediaApp"
    }
}