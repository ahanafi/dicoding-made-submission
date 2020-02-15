package com.ahanafi.id.myfavoritemovieapp.helper

import android.database.Cursor
import com.ahanafi.id.myfavoritemovieapp.models.Movie
import com.ahanafi.id.myfavoritemovieapp.models.TvShow

import com.ahanafi.id.myfavoritemovieapp.database.DatabaseContract.MyMovieColumns.Companion._ID as movieId
import com.ahanafi.id.myfavoritemovieapp.database.DatabaseContract.MyMovieColumns.Companion.TITLE as movieTitle
import com.ahanafi.id.myfavoritemovieapp.database.DatabaseContract.MyMovieColumns.Companion.LANGUAGE as movieLanguage
import com.ahanafi.id.myfavoritemovieapp.database.DatabaseContract.MyMovieColumns.Companion.RELEASE_DATE as movieReleaseDate
import com.ahanafi.id.myfavoritemovieapp.database.DatabaseContract.MyMovieColumns.Companion.OVERVIEW as movieOverview
import com.ahanafi.id.myfavoritemovieapp.database.DatabaseContract.MyMovieColumns.Companion.POSTER_PATH as moviePosterPath

import com.ahanafi.id.myfavoritemovieapp.database.DatabaseContract.MyTvShowColumns.Companion._ID as tvShowId
import com.ahanafi.id.myfavoritemovieapp.database.DatabaseContract.MyTvShowColumns.Companion.TITLE as tvShowTitle
import com.ahanafi.id.myfavoritemovieapp.database.DatabaseContract.MyTvShowColumns.Companion.RELEASE_DATE as tvShowReleaseDate
import com.ahanafi.id.myfavoritemovieapp.database.DatabaseContract.MyTvShowColumns.Companion.LANGUAGE as tvShowLanguage
import com.ahanafi.id.myfavoritemovieapp.database.DatabaseContract.MyTvShowColumns.Companion.OVERVIEW as tvShowOverview
import com.ahanafi.id.myfavoritemovieapp.database.DatabaseContract.MyTvShowColumns.Companion.POSTER_PATH as tvShowPosterPath

object MappingHelper {
    fun mapMovieCursorToArrayList(movieCursor: Cursor) : ArrayList<Movie> {
        val movieList = ArrayList<Movie>()
        while (movieCursor.moveToNext()){
            val id = movieCursor.getInt(movieCursor.getColumnIndexOrThrow(movieId))
            val title = movieCursor.getString(movieCursor.getColumnIndexOrThrow(movieTitle))
            val overview = movieCursor.getString(movieCursor.getColumnIndexOrThrow(movieOverview))
            val releaseDate= movieCursor.getString(movieCursor.getColumnIndexOrThrow(movieReleaseDate))
            val posterPath= movieCursor.getString(movieCursor.getColumnIndexOrThrow(moviePosterPath))
            val language = movieCursor.getString(movieCursor.getColumnIndexOrThrow(movieLanguage))
            movieList.add(
                Movie(
                    title = title, posterPath = posterPath, language = language, releaseDate = releaseDate,
                    id = id, overview = overview
                )
            )
        }

        return movieList
    }

    fun mapTvShowCursorToArrayList(movieCursor: Cursor) : ArrayList<TvShow> {
        val tvShowList = ArrayList<TvShow>()
        while (movieCursor.moveToNext()) {
            val id = movieCursor.getInt(movieCursor.getColumnIndexOrThrow(tvShowId))
            val title = movieCursor.getString(movieCursor.getColumnIndexOrThrow(tvShowTitle))
            val overview = movieCursor.getString(movieCursor.getColumnIndexOrThrow(tvShowOverview))
            val releaseDate= movieCursor.getString(movieCursor.getColumnIndexOrThrow(tvShowReleaseDate))
            val posterPath= movieCursor.getString(movieCursor.getColumnIndexOrThrow(tvShowPosterPath))
            val language = movieCursor.getString(movieCursor.getColumnIndexOrThrow(tvShowLanguage))
            tvShowList.add(
                TvShow(
                    id = id, title = title, overview = overview, releaseDate = releaseDate,
                    posterPath = posterPath, language = language
                )
            )
        }
        return tvShowList
    }
}