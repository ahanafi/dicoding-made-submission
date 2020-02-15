package com.ahanafi.id.myfavoritemovieapp.fragments.movie


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ahanafi.id.myfavoritemovieapp.R
import com.ahanafi.id.myfavoritemovieapp.adapters.MovieFavoriteAdapter
import com.ahanafi.id.myfavoritemovieapp.helper.MappingHelper
import com.ahanafi.id.myfavoritemovieapp.helper.MovieHelper
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_movie_favorite.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 */
class MovieFavoriteFragment : Fragment() {
    private lateinit var favoriteMovieAdapter : MovieFavoriteAdapter
    private lateinit var movieHelper : MovieHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_favorite_movie.setHasFixedSize(true)
        rv_favorite_movie.layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, isInLayout)
        favoriteMovieAdapter = MovieFavoriteAdapter()
        rv_favorite_movie.adapter = favoriteMovieAdapter

        //Load Helper
        movieHelper = MovieHelper.getInstance(activity!!.applicationContext)
        movieHelper.open()

        loadFavoriteMovies()

        if (savedInstanceState == null) {
            loadFavoriteMovies()
        } else {
            favoriteMovieAdapter = MovieFavoriteAdapter()
            rv_favorite_movie.adapter = favoriteMovieAdapter
        }
    }

    private fun loadFavoriteMovies() {
        GlobalScope.launch(Dispatchers.Main){
            progressbar_favorite_movie.visibility = View.VISIBLE
            val deferredMovie = async(Dispatchers.IO) {
                val movieCursor = movieHelper.queryAll()
                MappingHelper.mapMovieCursorToArrayList(movieCursor)
            }
            val movies = deferredMovie.await()
            progressbar_favorite_movie.visibility = View.GONE
            if(movies.size > 0) {
                favoriteMovieAdapter.listFavoriteMovies = movies
                rv_favorite_movie.adapter = favoriteMovieAdapter
            } else {
                favoriteMovieAdapter.listFavoriteMovies = ArrayList()
                Snackbar.make(rv_favorite_movie, getString(R.string.no_favorite_movie), Snackbar.LENGTH_SHORT).show()
            }
        }
    }


}
