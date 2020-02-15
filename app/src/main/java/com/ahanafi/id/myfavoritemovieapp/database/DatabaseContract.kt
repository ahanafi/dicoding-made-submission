package com.ahanafi.id.myfavoritemovieapp.database

import android.provider.BaseColumns

internal class DatabaseContract {
    internal class MyMovieColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "movies"
            const val _ID = "_id"
            const val TITLE = "title"
            const val OVERVIEW = "overview"
            const val RELEASE_DATE = "release_date"
            const val LANGUAGE = "language"
            const val POSTER_PATH = "poster_path"
        }
    }

    internal class MyTvShowColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "tv_shows"
            const val _ID = "_id"
            const val TITLE = "title"
            const val OVERVIEW = "overview"
            const val RELEASE_DATE = "release_date"
            const val LANGUAGE = "language"
            const val POSTER_PATH = "poster_path"
        }
    }
}