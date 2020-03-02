package com.cosmic.knockdown.media_player.util

import androidx.fragment.app.Fragment
import com.cosmic.knockdown.media_player.ViewModelFactory
import com.cosmic.knockdown.media_player.MainApplication

fun Fragment.getViewModelFactory(): ViewModelFactory {
    val audioRepository = (context!!.applicationContext as MainApplication).audioRepository
    return ViewModelFactory(audioRepository)
}