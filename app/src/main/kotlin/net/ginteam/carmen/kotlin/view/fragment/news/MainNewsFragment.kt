package net.ginteam.carmen.kotlin.view.fragment.news

import android.content.Intent
import android.os.Handler
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import net.ginteam.carmen.R
import net.ginteam.carmen.kotlin.contract.MainNewsFragmentContract
import net.ginteam.carmen.kotlin.listener.ViewPagerListener
import net.ginteam.carmen.kotlin.presenter.news.MainNewsFragmentPresenter
import net.ginteam.carmen.kotlin.view.activity.authentication.SignInActivity
import net.ginteam.carmen.kotlin.view.adapter.news.ViewPagerAdapter
import net.ginteam.carmen.kotlin.view.fragment.BaseFragment

/**
 * Created by vadik on 09.03.17.
 */
class MainNewsFragment : BaseFragment<MainNewsFragmentContract.View, MainNewsFragmentContract.Presenter>(),
        MainNewsFragmentContract.View {

    override var mPresenter: MainNewsFragmentContract.Presenter = MainNewsFragmentPresenter()

    private lateinit var mViewPagerNewsAdapter: ViewPagerAdapter

    private var mCurrentViewPagerPosition: Int = PopularNewsFragment.POSITION
    private val mUiThreadHandler: Handler = Handler()

    companion object {

        fun newInstance(): MainNewsFragment = MainNewsFragment()

    }

    override fun getLayoutResId(): Int = R.layout.fragment_main_news

    override fun updateDependencies() {
        super.updateDependencies()

        mViewPagerNewsAdapter = ViewPagerAdapter(childFragmentManager)
        mViewPagerNewsAdapter.addFragment(RecentNewsFragment.newInstance(), getString(R.string.news_tab_title_new))
        mViewPagerNewsAdapter.addFragment(PopularNewsFragment.newInstance(), getString(R.string.news_tab_title_popular))
        mViewPagerNewsAdapter.addFragment(FavoritesNewsFragment.newInstance(), getString(R.string.news_tab_title_favorites))
    }

    override fun updateViewDependencies() {
        super.updateViewDependencies()
        setHasOptionsMenu(true)

        val viewPagerNews = mFragmentView.findViewById(R.id.view_pager_news) as ViewPager
        viewPagerNews.adapter = mViewPagerNewsAdapter
        viewPagerNews.offscreenPageLimit = 3
        viewPagerNews.currentItem = mCurrentViewPagerPosition

        viewPagerNews.addOnPageChangeListener(object : ViewPagerListener() {
            override fun onPageSelected(position: Int) {
                if (position == FavoritesNewsFragment.POSITION && !mPresenter.isUserSignedIn()) {
                    showError(R.string.access_denied_message) {
                        startSignInActivityForResult()
                    }
                    mUiThreadHandler.postDelayed({
                        viewPagerNews.currentItem = mCurrentViewPagerPosition
                    }, 50)
                    return
                }
                mViewPagerNewsAdapter.getItem(position).fetchNews()
                mCurrentViewPagerPosition = position
            }
        })

        val tabLayoutNews = mFragmentView.findViewById(R.id.tab_layout_news) as TabLayout
        tabLayoutNews.setupWithViewPager(viewPagerNews)
    }

    private fun startSignInActivityForResult() {
        val intent = Intent(context, SignInActivity::class.java)
        startActivityForResult(intent, SignInActivity.SIGN_IN_REQUEST_CODE)
    }
}