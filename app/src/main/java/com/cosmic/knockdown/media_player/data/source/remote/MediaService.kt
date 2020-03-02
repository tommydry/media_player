package com.cosmic.knockdown.media_player.data.source.remote

import retrofit2.http.GET

interface MediaService {

    @GET("this_is_static/content.json")
    suspend fun getAllAudio(): Media
}