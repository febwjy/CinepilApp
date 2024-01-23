package com.febwjy.cinepilapp.ui.popular

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.febwjy.cinepilapp.data.model.dto.PopularListResponse
import com.febwjy.cinepilapp.databinding.ItemPopularListBinding
import com.febwjy.cinepilapp.utils.Constant

/**
 * Created by Febby Wijaya on 22/01/2024.
 */
class PopularListAdapter(
    private val popularList: MutableList<PopularListResponse.Result>,
    private val onClickListener: OnClickListener
) : RecyclerView.Adapter<PopularListAdapter.ViewHolder>() {

    class OnClickListener(val clickListener: (idMovie: String) -> Unit) {
        fun onClick(idMovie: String) = clickListener(idMovie)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(mPopularList: List<PopularListResponse.Result>) {
        popularList.clear()
        popularList.addAll(mPopularList)
        notifyDataSetChanged()
    }

    inner class ViewHolder(
        private val itemBinding: ItemPopularListBinding,
        private val context: Context
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(popularList: PopularListResponse.Result) {
            if (popularList.title != null) {
                itemBinding.txtMovieTitle.text = popularList.title
                itemBinding.txtReleaseDate.text =
                    popularList.releaseDate.toString().substringBefore("-")
            } else {
                itemBinding.txtMovieTitle.text = popularList.name
                itemBinding.txtReleaseDate.text =
                    popularList.releaseDate.toString().substringBefore("-")
            }
            val imgPath = Constant.IMG_URL + popularList.posterPath
            val uri: Uri = Uri.parse(imgPath)
            Glide.with(context).load(uri)
                .into(itemBinding.imgMovie)
            itemBinding.imgMovie.visibility = View.VISIBLE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            ItemPopularListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val context = parent.context
        return ViewHolder(view, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(popularList = popularList[position])
        holder.itemView.setOnClickListener {
            popularList[position].id?.let { idMovie -> onClickListener.onClick(idMovie.toString()) }
        }
    }

    override fun getItemCount() = popularList.size

}