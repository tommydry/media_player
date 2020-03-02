package com.cosmic.knockdown.media_player.audio

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar

import com.cosmic.knockdown.media_player.R
import com.cosmic.knockdown.media_player.data.Audio
import com.cosmic.knockdown.media_player.MainApplication
import com.cosmic.knockdown.media_player.util.ClickListener
import com.cosmic.knockdown.media_player.util.getViewModelFactory

class AudioFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private var audioList: RecyclerView? = null
    private var swipeRefreshLayout: SwipeRefreshLayout? = null

    companion object {
        fun newInstance() = AudioFragment()
    }

    private lateinit var viewModel: AudioViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.audio_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupSwipeRefreshLayout()
        viewModel = getViewModelFactory().create(AudioViewModel::class.java)
        val audioAdapter = setupAudioListAdapter()
        viewModel.audio.observe(
            viewLifecycleOwner, Observer<List<Audio>> {
                Log.d(MainApplication.TAG, "audio was changed")
                swipeRefreshLayout!!.isRefreshing = false
                audioAdapter.notifyDataSetChanged()
            }
        )
        setupSnackbar()
    }

    private fun setupAudioListAdapter(): AudioAdapter {
        audioList!!.layoutManager = LinearLayoutManager(context)
        val audioAdapter = AudioAdapter(viewModel, object : ClickListener<Audio> {
            override fun onClick(item: Audio) {
                findNavController().navigate(R.id.audio_player_fragment)
            }
        })
        audioAdapter.setHasStableIds(true)
        audioList!!.adapter = audioAdapter
        return audioAdapter
    }

    private fun setupSwipeRefreshLayout() {
        audioList = view!!.findViewById(R.id.audioList)
        swipeRefreshLayout = view!!.findViewById(R.id.swipeRefreshLayout)
        swipeRefreshLayout!!.setOnRefreshListener(this)
        swipeRefreshLayout!!.setColorSchemeResources(
            R.color.colorPrimary,
            android.R.color.holo_green_dark,
            android.R.color.holo_orange_dark,
            android.R.color.holo_blue_dark
        )
    }

    private fun setupSnackbar() {
        viewModel.snackbarText.observe(viewLifecycleOwner, Observer<String> {
            if (it.isNotEmpty()) Snackbar.make(view!!, it, Snackbar.LENGTH_LONG).show()
        })
    }

    override fun onRefresh() {
        Log.d(MainApplication.TAG, "onRefresh")
        viewModel.loadAudio(true)
    }

}
