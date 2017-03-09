package net.ginteam.carmen.kotlin.listener

import android.support.v4.view.ViewPager

/**
 * Created by vadik on 09.03.17.
 */
abstract class ViewPagerListener : ViewPager.OnPageChangeListener {

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    abstract override fun onPageSelected(position: Int)

}