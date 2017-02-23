package net.ginteam.carmen.kotlin.listener

import android.support.v7.widget.RecyclerView
import android.view.MotionEvent
import android.view.View

/**
 * Created by eugene_shcherbinock on 2/22/17.
 */

abstract class PageAdapterListener <out T : RecyclerView.Adapter <RecyclerView.ViewHolder>> (val adapter: T)
    : View.OnTouchListener {

    private var mTouchPosition: Float = 0f
    private var mCurrentAdapterPosition: Int = 0

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        when (event!!.action) {
            MotionEvent.ACTION_DOWN -> {
                mTouchPosition = event.x
            }
            MotionEvent.ACTION_UP -> {
                if (mTouchPosition > event.x) {
                    if (mCurrentAdapterPosition + 1 < adapter.itemCount) {
                        mCurrentAdapterPosition++
                    }
                } else if (mTouchPosition < event.x) {
                    if (mCurrentAdapterPosition - 1 >= 0) {
                        mCurrentAdapterPosition--
                    }
                }
                adapterPositionChanged(mCurrentAdapterPosition)
            }
        }
        return false
    }

    abstract fun adapterPositionChanged(position: Int)
}