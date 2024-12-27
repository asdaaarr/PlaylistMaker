package com.practicum.playlistmaker

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.practicum.playlistmaker.databinding.TrackItemBinding

class TrackViewHolder(
    private val binding: TrackItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(track: Track) {
        binding.tvTrackName.text = track.trackName
        binding.tvArtistName.text = track.artistName
        binding.tvTrackTime.text = track.trackTime

        // Загрузка обложки через Glide
        Glide.with(binding.root)
            .load(track.artworkUrl100)
            .placeholder(R.drawable.ic_placeholder)
            .fitCenter()
            .transform(RoundedCorners(2))
            .into(binding.ivArtwork)
    }
}