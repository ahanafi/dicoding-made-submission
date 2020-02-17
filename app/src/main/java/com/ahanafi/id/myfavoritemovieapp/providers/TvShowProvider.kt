package com.ahanafi.id.myfavoritemovieapp.providers

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.ahanafi.id.myfavoritemovieapp.database.DatabaseContract.AUTHORITY
import com.ahanafi.id.myfavoritemovieapp.database.DatabaseContract.MyTvShowColumns.Companion.CONTENT_URI
import com.ahanafi.id.myfavoritemovieapp.database.DatabaseContract.MyTvShowColumns.Companion.TABLE_NAME
import com.ahanafi.id.myfavoritemovieapp.helper.TvShowHelper

class TvShowProvider : ContentProvider() {

    companion object{
        private const val TV_SHOW = 1
        private const val TV_SHOW_ID = 2
        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        private lateinit var tvShowHelper: TvShowHelper

        init {
            sUriMatcher.addURI(AUTHORITY, TABLE_NAME, TV_SHOW)
            sUriMatcher.addURI(AUTHORITY, "$TABLE_NAME/#", TV_SHOW_ID)
        }
    }

    override fun onCreate(): Boolean {
        tvShowHelper = TvShowHelper.getInstance(context as Context)
        tvShowHelper.open()
        return true
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        val updated: Int = when (TV_SHOW_ID) {
            sUriMatcher.match(uri) -> tvShowHelper.update(uri.lastPathSegment.toString(),values)
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
            TV_SHOW -> tvShowHelper.queryAll()
            TV_SHOW_ID-> tvShowHelper.queryById(uri.lastPathSegment.toString())
            else -> null
        }
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val added : Long = when (TV_SHOW) {
            sUriMatcher.match(uri) -> tvShowHelper.insert(values!!)
            else -> 0
        }

        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return Uri.parse("$CONTENT_URI/$added")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val deleted: Int = when (TV_SHOW_ID) {
            sUriMatcher.match(uri) -> tvShowHelper.deleteById(uri.lastPathSegment.toString())
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return deleted
    }
}
