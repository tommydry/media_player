package com.cosmic.knockdown.media_player.data.source.remote

import retrofit2.http.GET

interface MediaService {

    @GET("media_player_content/content.json")
    suspend fun getAllAudio(): Media
}