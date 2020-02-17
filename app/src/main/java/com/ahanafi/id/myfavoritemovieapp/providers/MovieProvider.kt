package com.ahanafi.id.myfavoritemovieapp.providers

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.ahanafi.id.myfavoritemovieapp.database.DatabaseContract.AUTHORITY
import com.ahanafi.id.myfavoritemovieapp.database.DatabaseContract.MyMovieColumns.Companion.CONTENT_URI
import com.ahanafi.id.myfavoritemovieapp.database.DatabaseContract.MyMovieColumns.Companion.TABLE_NAME
import com.ahanafi.id.myfavoritemovieapp.helper.MovieHelper

class MovieProvider : ContentProvider() {

    companion object{
        private const val MOVIE = 1
        private const val MOVIE_ID = 2
        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        private lateinit var movieHelper: MovieHelper

        init {
            sUriMatcher.addURI(
                AUTHORITY,
                TABLE_NAME, MOVIE)
            sUriMatcher.addURI(AUTHORITY, "${TABLE_NAME}/#", MOVIE_ID)
        }
    }

    override fun onCreate(): Boolean {
        movieHelper = MovieHelper.getInstance(context as Context)
        movieHelper.open()
        return true
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        val updated: Int = when (MOVIE_ID) {
            sUriMatcher.match(uri) -> movieHelper.update(uri.lastPathSegment.toString(),values)
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return updated
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {

        return when(sUriMatcher.match(uri)) {
            MOVIE -> movieHelper.queryAll()
            MOVIE_ID-> movieHelper.queryById(uri.lastPathSegment.toString())
            else -> null
        }
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val added : Long = when (MOVIE) {
            sUriMatcher.match(uri) -> movieHelper.insert(values)
            else -> 0
        }

        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return Uri.parse("${CONTENT_URI}/$added")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val deleted: Int = when (MOVIE_ID) {
            sUriMatcher.match(uri) -> movieHelper.deleteById(uri.lastPathSegment.toString())
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return deleted
    }
}
