package com.febwjy.cinepilapp.ui.genre

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.febwjy.cinepilapp.data.model.dto.GenreResponse
import com.febwjy.cinepilapp.databinding.ItemGenreBinding

/**
 * Created by Febby Wijaya on 16/09/23.
 */
class GenreMovieAdapter(private val genres: MutableList<GenreResponse.Genres>, private val onClickListener: OnClickListener)
    : RecyclerView.Adapter<GenreMovieAdapter.ViewHolder>() {

    class OnClickListener(val clickListener: (id: String) -> Unit) {
        fun onClick(id: String) = clickListener(id)
    }

    fun updateList(mProducts: List<GenreResponse.Genres>) {
        genres.clear()
        genres.addAll(mProducts)
        notifyDataSetChanged()
    }

    inner class ViewHolder(
        private val itemBinding: ItemGenreBinding,
        val context: Context
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(genre: GenreResponse.Genres) {
            if (genre.name != null){
                itemBinding.genreTextView.text = genre.name
            } else itemBinding.genreTextView.text = "Genre Undentified"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemGenreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val context = parent.context;
        return ViewHolder(view, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(genre = genres[position])
        holder.itemView.setOnClickListener {
            genres[position].id?.let { id -> onClickListener.onClick(id.toString()) }
        }
    }

    override fun getItemCount() = genres.size
}