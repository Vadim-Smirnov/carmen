package net.ginteam.carmen.kotlin.listener

import android.support.v7.widget.SearchView
import android.text.TextUtils

/**
 * Created by eugene_shcherbinock on 2/20/17.
 */

abstract class SearchViewListener : SearchView.OnQueryTextListener {

    override fun onQueryTextChange(newText: String?): Boolean {
        if (TextUtils.isEmpty(newText)) {
            onQueryTextSubmit(newText)
        }
        return true
    }

    override abstract fun onQueryTextSubmit(query: String?): Boolean

}