package net.ginteam.carmen.kotlin.view.activity.company

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import net.ginteam.carmen.R
import net.ginteam.carmen.kotlin.contract.CompanyRatingsContract
import net.ginteam.carmen.kotlin.model.RatingModel
import net.ginteam.carmen.kotlin.presenter.company.detail.CompanyRatingsPresenter
import net.ginteam.carmen.kotlin.view.activity.BaseActivity
import net.ginteam.carmen.view.adapter.ReviewsAdapter

/**
 * Created by eugene_shcherbinock on 2/27/17.
 */

class CompanyRatingsActivity : BaseActivity <CompanyRatingsContract.View, CompanyRatingsContract.Presenter>(),
        CompanyRatingsContract.View {

    override var mPresenter: CompanyRatingsContract.Presenter = CompanyRatingsPresenter()

    private lateinit var mCompanyRatings: List <RatingModel>

    companion object {
        const val COMPANY_RATINGS_ARGUMENT = "ratings"
    }

    override fun getLayoutResId(): Int = R.layout.activity_all_reviews

    override fun updateDependencies() {
        super.updateDependencies()
        mCompanyRatings = intent.getSerializableExtra(COMPANY_RATINGS_ARGUMENT) as List <RatingModel>
    }

    override fun updateViewDependencies() {
        super.updateViewDependencies()

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_back_button)

        setToolbarTitle(getString(R.string.all_review_title))
        setToolbarSubtitle(String.format("(%s %s)", getString(R.string.all_review_subtitle), mCompanyRatings.size))

        val ratingsRecyclerView: RecyclerView = findViewById(R.id.recycler_view_reviews) as RecyclerView
        ratingsRecyclerView.layoutManager = LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false)
        ratingsRecyclerView.adapter = ReviewsAdapter(getContext(), mCompanyRatings, true)
    }
}