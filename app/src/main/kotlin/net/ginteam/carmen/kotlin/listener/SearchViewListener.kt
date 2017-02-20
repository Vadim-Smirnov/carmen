package net.ginteam.carmen.kotlin.listener

import android.support.v7.widget.SearchView

/**
 * Created by eugene_shcherbinock on 2/20/17.
 */

abstract class SearchViewListener : SearchView.OnQueryTextListener {

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override abstract fun onQueryTextChange(newText: String?): Boolean
}