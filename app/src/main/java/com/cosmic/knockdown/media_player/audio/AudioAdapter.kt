package com.cosmic.knockdown.media_player.audio

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.TwoLineListItem
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cosmic.knockdown.media_player.data.Audio
import com.cosmic.knockdown.media_player.util.ClickListener

class AudioAdapter(
    private val audioViewModel: AudioViewModel,
    private val onClickListener: ClickListener<Audio>
) : ListAdapter<Audio, AudioAdapter.AudioViewHolder>(AudioCallback()) {

    class AudioViewHolder(private val v: View, private val onClickListener: ClickListener<Audio>) :
        RecyclerView.ViewHolder(v) {
        fun bind(audio: Audio) {
            (v as TwoLineListItem).findViewById<TextView>(android.R.id.text1).text = audio.title
            v.findViewById<TextView>(android.R.id.text2).text = audio.author
            v.setOnClickListener { onClickListener.onClick(audio) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AudioViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_2, parent, false)
        return AudioViewHolder(view, onClickListener)
    }

    override fun onBindViewHolder(holder: AudioViewHolder, position: Int) {
        if (audioViewModel.audio.value != null) {
            holder.bind(audioViewModel.audio.value!![position])

        }
    }

    override fun getItemId(position: Int): Long {
        return if (audioViewModel.audio.value != null) {
            audioViewModel.audio.value!![position].id.toLong()
        } else {
            0
        }
    }

    override fun getItemCount(): Int {
        return if (audioViewModel.audio.value != null) {
            audioViewModel.audio.value!!.size
        } else {
            0
        }
    }
}

class AudioCallback : DiffUtil.ItemCallback<Audio>() {
    override fun areItemsTheSame(oldItem: Audio, newItem: Audio): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Audio, newItem: Audio): Boolean {
        return oldItem == newItem
    }

}