package com.ahanafi.id.myfavoritemovieapp.fragments.tvshow


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ahanafi.id.myfavoritemovieapp.R
import com.ahanafi.id.myfavoritemovieapp.adapters.TvShowFavoriteAdapter
import com.ahanafi.id.myfavoritemovieapp.helper.MappingHelper
import com.ahanafi.id.myfavoritemovieapp.helper.TvShowHelper
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_tv_show_favorite.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class TvShowFavoriteFragment : Fragment() {
    private lateinit var favoriteTvShowAdapter: TvShowFavoriteAdapter
    private lateinit var tvShowHelper: TvShowHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_show_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_favorite_tv_show.setHasFixedSize(true)
        rv_favorite_tv_show.layoutManager = LinearLayoutManager(this.activity, RecyclerView.HORIZONTAL, isInLayout)
        favoriteTvShowAdapter = TvShowFavoriteAdapter()
        rv_favorite_tv_show.adapter = favoriteTvShowAdapter

        tvShowHelper = TvShowHelper.getInstance(activity!!.applicationContext)
        tvShowHelper.open()

        loadFavoriteTvSows()
    }

    private fun loadFavoriteTvSows() {
        GlobalScope.launch(Dispatchers.Main){
            progressbar_favorite_tv_show.visibility = View.VISIBLE
            val deferredTvShow = async(Dispatchers.IO) {
                val tvShowCursor = tvShowHelper.queryAll()
                MappingHelper.mapTvShowCursorToArrayList(tvShowCursor)
            }
            val tvShow = deferredTvShow.await()
            progressbar_favorite_tv_show.visibility = View.GONE
            if(tvShow.size > 0) {
                favoriteTvShowAdapter.listFavoriteTvShow = tvShow
                rv_favorite_tv_show.adapter = favoriteTvShowAdapter
            } else {
                favoriteTvShowAdapter.listFavoriteTvShow = ArrayList()
                Snackbar.make(progressbar_favorite_tv_show, getString(R.string.no_favorite_tv_show), Snackbar.LENGTH_SHORT).show()
            }
        }
    }

}
