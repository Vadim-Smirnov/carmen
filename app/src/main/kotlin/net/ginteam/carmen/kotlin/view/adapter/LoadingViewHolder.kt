package net.ginteam.carmen.kotlin.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ProgressBar
import net.ginteam.carmen.R

/**
 * Created by eugene_shcherbinock on 3/7/17.
 */

class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val mProgressBar: ProgressBar
            = itemView.findViewById(R.id.progress_bar_item) as ProgressBar

}