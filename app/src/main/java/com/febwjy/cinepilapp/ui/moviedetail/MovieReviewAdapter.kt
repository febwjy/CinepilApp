package com.febwjy.cinepilapp.ui.moviedetail

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.febwjy.cinepilapp.data.model.dto.MovieReviewResponse
import com.febwjy.cinepilapp.databinding.ItemReviewBinding

/**
 * Created by Febby Wijaya on 24/05/22.
 */
class MovieReviewAdapter(
    private val movieReview: MutableList<MovieReviewResponse.Result>,
) : RecyclerView.Adapter<MovieReviewAdapter.ViewHolder>(){

    fun updateList(mMovieReview: List<MovieReviewResponse.Result>) {
        movieReview.clear()
        movieReview.addAll(mMovieReview)
        notifyDataSetChanged()
    }

    inner class ViewHolder (private val itemBinding: ItemReviewBinding, private val context: Context)
        : RecyclerView.ViewHolder(itemBinding.root){

            fun bind(movieReview: MovieReviewResponse.Result) {
                if (movieReview.content != null) {
                    itemBinding.txtAuthor.text = movieReview.author
                    itemBinding.txtRating.text = movieReview.author_details!!.rating.toString() + "/10"
                    itemBinding.txtReview.text = movieReview.content
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val context = parent.context
        return ViewHolder(view, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movieReview = movieReview[position])
    }

    override fun getItemCount() = movieReview.size
}