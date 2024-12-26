package com.practicum.playlistmaker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.practicum.playlistmaker.databinding.TrackItemBinding

class TrackAdapter(
    private val trackList: List<Track>
) : RecyclerView.Adapter<TrackAdapter.TrackViewHolder>() {

    inner class TrackViewHolder(
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        // Вместо inflater.inflate(...) используем автоматически сгенерированный класс binding
        val binding = TrackItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TrackViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(trackList[position])
    }

    override fun getItemCount(): Int = trackList.size
}