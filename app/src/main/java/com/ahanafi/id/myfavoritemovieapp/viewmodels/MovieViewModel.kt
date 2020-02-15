package com.ahanafi.id.myfavoritemovieapp.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ahanafi.id.myfavoritemovieapp.models.Movie
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.lang.Exception
import com.ahanafi.id.myfavoritemovieapp.BuildConfig

class MovieViewModel : ViewModel() {
    companion object {
        private const val API_KEY = BuildConfig.TheMovieDB_ApiKey
    }

    val listMovies = MutableLiveData<ArrayList<Movie>>()

    internal fun setMovie() {
        val client = AsyncHttpClient()
        val listMovie = ArrayList<Movie>()
        val url = "https://api.themoviedb.org/3/discover/movie?api_key=$API_KEY&language=en-US"

        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray
            ) {
                try {
                    val result = String(responseBody)
                    val responsObject = JSONObject(result)
                    val listData = responsObject.getJSONArray("results")
                    for (i in 0 until listData.length()) {
                        val movieData = listData.getJSONObject(i)
                        val movie = Movie()
                        movie.id = movieData.getInt("id")
                        movie.title = movieData.getString("title")
                        movie.overview = movieData.getString("overview")
                        movie.releaseDate = movieData.getString("release_date")
                        movie.language = movieData.getString("original_language")
                        val posterName = movieData.getString("poster_path")
                        val backdropName = movieData.getString("backdrop_path")
                        movie.posterPath = "https://image.tmdb.org/t/p/w185/$posterName"
                        movie.backdropPath = "https://image.tmdb.org/t/p/w342/$backdropName"
                        movie.voteCount = movieData.getInt("vote_count")
                        movie.voteAverage = movieData.getInt("vote_average")
                        movie.popularity = movieData.getInt("popularity")
                        listMovie.add(movie)
                    }

                    listMovies.postValue(listMovie)
                } catch (e: Exception) {
                    Log.d("Exception: ", e.message.toString())
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                Log.d("onFailure", error.message.toString())
            }
        })
    }

    internal fun getMovies() : LiveData<ArrayList<Movie>> {
        return listMovies
    }
}