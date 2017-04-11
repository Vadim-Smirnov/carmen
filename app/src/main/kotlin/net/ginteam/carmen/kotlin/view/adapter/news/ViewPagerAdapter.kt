package net.ginteam.carmen.kotlin.view.adapter.news

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import net.ginteam.carmen.kotlin.view.fragment.news.BaseNewsFragment
import java.util.*

/**
 * Created by vadik on 09.03.17.
 */
class ViewPagerAdapter(val fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    private val mFragmentList: MutableList<BaseNewsFragment<*, *, *>> = ArrayList<BaseNewsFragment<*, *, *>>()
    private val mTitleList: MutableList<String> = ArrayList<String>()

    override fun getItem(position: Int): BaseNewsFragment<*, *, *> = mFragmentList[position]

    override fun getCount(): Int = mFragmentList.size

    override fun getPageTitle(position: Int): CharSequence = mTitleList[position]

    fun addFragment(fragment: BaseNewsFragment<*, *, *>, title: String) {
        mFragmentList.add(fragment)
        mTitleList.add(title)
    }

}