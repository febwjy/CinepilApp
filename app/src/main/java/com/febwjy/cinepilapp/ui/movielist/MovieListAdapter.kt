package com.febwjy.cinepilapp.ui.movielist

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.febwjy.cinepilapp.data.model.dto.MovieListResponse
import com.febwjy.cinepilapp.databinding.ItemMovieListBinding
import com.febwjy.cinepilapp.utils.Constant
import com.febwjy.cinepilapp.utils.DateUtils

/**
 * Created by Febby Wijaya on 22/01/24.
 */
class MovieListAdapter (
    private val movieList: MutableList<MovieListResponse.MovieList>,
    private val onClickListener: OnClickListener
) : RecyclerView.Adapter<MovieListAdapter.ViewHolder>(){

    class OnClickListener (val clickListener: (idMovie: String) -> Unit) {
        fun onClick(idMovie: String) = clickListener(idMovie)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(mMovieList: List<MovieListResponse.MovieList>) {
        movieList.clear()
        movieList.addAll(mMovieList)
        notifyDataSetChanged()
    }

    inner class ViewHolder (private val itemBinding: ItemMovieListBinding, private val context: Context)
        : RecyclerView.ViewHolder(itemBinding.root){

            @SuppressLint("SetTextI18n")
            fun bind(movieList: MovieListResponse.MovieList) {
                if (movieList.title != null) {
                    itemBinding.txtMovieTitle.text = movieList.title
                    itemBinding.txtMovieRating.text = movieList.voteAverage.toString() + "/10"
                    itemBinding.txtReleaseDate.text = DateUtils.getFormattedDate(movieList.releaseDate.toString())
                } else itemBinding.txtMovieTitle.text = movieList.originalTitle
                if (movieList.posterPath != null) {
                    val imgPath = Constant.IMG_URL + movieList.backdropPath
                    val uri: Uri = Uri.parse(imgPath)
                    Glide.with(context).load(uri)
                        .into(itemBinding.imgMovie)
                    itemBinding.imgMovie.visibility = View.VISIBLE
                } else itemBinding.imgMovie.visibility = View.GONE
            }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemMovieListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val context = parent.context
        return ViewHolder(view, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movieList = movieList[position])
        holder.itemView.setOnClickListener {
            movieList[position].id?.let { idMovie -> onClickListener.onClick(idMovie.toString()) }
        }
    }

    override fun getItemCount() = movieList.size
}