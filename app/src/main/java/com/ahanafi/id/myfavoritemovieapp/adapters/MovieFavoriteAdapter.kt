package com.ahanafi.id.myfavoritemovieapp.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.ahanafi.id.myfavoritemovieapp.R
import com.ahanafi.id.myfavoritemovieapp.details.DetailMovieActivity
import com.ahanafi.id.myfavoritemovieapp.helper.MovieHelper
import com.ahanafi.id.myfavoritemovieapp.models.Movie
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_favorite_movie.view.*
import kotlin.collections.ArrayList

class MovieFavoriteAdapter : RecyclerView.Adapter<MovieFavoriteAdapter.MovieFavoriteViewHolder>() {

    private lateinit var movieHelper : MovieHelper

    var listFavoriteMovies = ArrayList<Movie>()
    set(listFavoriteMovies) {
        if (listFavoriteMovies.size > 0) {
            this.listFavoriteMovies.clear()
        }
        this.listFavoriteMovies.addAll(listFavoriteMovies)
        notifyDataSetChanged()
    }

    inner class MovieFavoriteViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        private val imgPoster : ImageView = itemView.findViewById(R.id.img_item_photo)
        private val tvTitle : TextView = itemView.findViewById(R.id.tv_item_name)

        init {
            itemView.setOnClickListener{
                val movie = listFavoriteMovies[adapterPosition]
                val detailMovieActivity = Intent(itemView.context, DetailMovieActivity::class.java)
                detailMovieActivity.putExtra(DetailMovieActivity.EXTRA_MOVIE_DATA, movie)
                itemView.context.startActivity(detailMovieActivity)
            }
        }

        internal fun bind(movie : Movie) {
            Glide.with(itemView).load(movie.posterPath).into(imgPoster)
            tvTitle.text = movie.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieFavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_favorite_movie, parent, false)
        return MovieFavoriteViewHolder(view)
    }

    override fun getItemCount(): Int = this.listFavoriteMovies.size

    override fun onBindViewHolder(holder: MovieFavoriteViewHolder, position: Int) {
        val movie  = listFavoriteMovies[position]
        holder.bind(movie)
        movieHelper = MovieHelper.getInstance(holder.itemView.context.applicationContext)
        movieHelper.open()
        holder.itemView.btn_remove_movie.setOnClickListener{
           val dialog = MaterialDialog(holder.itemView.context)
               .title(R.string.confirm_delete)
               .message(R.string.confirm_delete_message)
               .positiveButton(R.string.yes){
                   val result = movieHelper.deleteById(movie.id.toString()).toLong()
                   if (result > 0) {
                       removeItem(position)
                       Toast.makeText(holder.itemView.context, R.string.success_deleted_movie, Toast.LENGTH_SHORT).show()
                   } else {
                       Toast.makeText(holder.itemView.context, R.string.failed_deleted_movie, Toast.LENGTH_SHORT).show()
                   }
               }
               .negativeButton(R.string.cancel){ materialDialog ->
                   materialDialog.cancel()
               }
            dialog.show()
        }
    }

    private fun removeItem(position: Int) {
        this.listFavoriteMovies.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.listFavoriteMovies.size)
    }

    fun addItem(movie: Movie) {
        this.listFavoriteMovies.add(movie)
        notifyItemInserted(this.listFavoriteMovies.size-1)
    }
}