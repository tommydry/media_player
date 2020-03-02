package com.cosmic.knockdown.media_player.audio

import android.util.Log
import androidx.lifecycle.*
import com.cosmic.knockdown.media_player.MainApplication
import com.cosmic.knockdown.media_player.R
import com.cosmic.knockdown.media_player.data.Audio
import com.cosmic.knockdown.media_player.data.source.AudioRepository
import kotlinx.coroutines.launch

class AudioViewModel(
    private val audioRepository: AudioRepository
) : ViewModel() {
    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _forceUpdate = MutableLiveData<Boolean>(false)

    private val _snackbarText = MutableLiveData<String>("")
    val snackbarText: LiveData<String> = _snackbarText

    private val _audio: LiveData<List<Audio>> = _forceUpdate.switchMap { forceUpdate ->
        if (forceUpdate) {
            _dataLoading.value = true
            viewModelScope.launch {
                audioRepository.refreshAllAudio()
                _dataLoading.value = false
            }
        }
        _snackbarText.value = "Data was loaded. Force update: $forceUpdate"
        audioRepository.observeAllAudio().distinctUntilChanged().switchMap { filterAudio(it) }

    }

    val audio: LiveData<List<Audio>> = _audio

    private val isDataLoadingError = MutableLiveData<Boolean>()

    private fun filterAudio(audioResult: com.cosmic.knockdown.media_player.data.Result<List<Audio>>): LiveData<List<Audio>> {
        // TODO: liveData builder???
        Log.d(MainApplication.TAG, "filter audio")
        val result = MutableLiveData<List<Audio>>()

        if (audioResult is com.cosmic.knockdown.media_player.data.Result.Success) {
            isDataLoadingError.value = false
            viewModelScope.launch {
                result.value = audioResult.data
            }
        } else {
            result.value = emptyList()
            showSnackbarMessage(R.string.cant_load_audio)
            isDataLoadingError.value = true
        }

        return result
    }

    private fun showSnackbarMessage(message: Int) {
//        _snackbarText.value = Event(message)
    }

    fun loadAudio(forceUpdate: Boolean) {
        _forceUpdate.value = forceUpdate
    }

    fun refresh() {
        _forceUpdate.value = true
    }
}
