package net.ginteam.carmen.kotlin.view.activity.company

import android.view.Menu
import android.view.MenuItem
import android.widget.RatingBar
import android.widget.TextView
import net.ginteam.carmen.R
import net.ginteam.carmen.kotlin.contract.CompanyRatingUpdateContract
import net.ginteam.carmen.kotlin.model.CompanyModel
import net.ginteam.carmen.kotlin.model.RatingModel
import net.ginteam.carmen.kotlin.presenter.company.detail.CompanyRatingUpdatePresenter
import net.ginteam.carmen.kotlin.view.activity.BaseActivity
import net.ginteam.carmen.view.custom.FilterEditText
import net.ginteam.carmen.view.custom.rating.CarmenRatingView

/**
 * Created by eugene_shcherbinock on 2/27/17.
 */

class CompanyRatingUpdateActivity : BaseActivity <CompanyRatingUpdateContract.View, CompanyRatingUpdateContract.Presenter>(),
        CompanyRatingUpdateContract.View, RatingBar.OnRatingBarChangeListener {

    override var mPresenter: CompanyRatingUpdateContract.Presenter = CompanyRatingUpdatePresenter()

    private lateinit var mSelectedCompany: CompanyModel
    private lateinit var mUpdatingRating: RatingModel

    private lateinit var mRatingViewCompany: CarmenRatingView
    private lateinit var mRatingViewCompanyPrice: CarmenRatingView

    companion object {
        const val COMPANY_ARGUMENT = "company"
        const val RATING_ARGUMENT = "rating"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.let { menuInflater.inflate(R.menu.vote_menu, menu) }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_item_send -> sendUpdatedRating()
            else -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onRatingChanged(ratingBar: RatingBar?, rating: Float, fromUser: Boolean) {
        if (rating < 1) {
            ratingBar?.rating = 1f
            return
        }

        when (ratingBar) {
            mRatingViewCompany -> {
                (findViewById(R.id.text_view_rating) as TextView).text =
                        resources.getTextArray(R.array.rating_array)[rating.toInt() - 1]
            }
            mRatingViewCompanyPrice -> {
                (findViewById(R.id.text_view_price_rating) as TextView).text =
                        resources.getTextArray(R.array.rating_price_array)[rating.toInt() - 1]
            }
        }
    }

    override fun getLayoutResId(): Int = R.layout.activity_vote_object

    override fun dismiss() {
        finish()
    }

    override fun updateDependencies() {
        super.updateDependencies()

        mSelectedCompany = intent.getSerializableExtra(COMPANY_ARGUMENT) as CompanyModel
        mUpdatingRating = intent.getSerializableExtra(RATING_ARGUMENT) as RatingModel
    }

    override fun updateViewDependencies() {
        super.updateViewDependencies()

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_back_button)

        setToolbarTitle(getString(R.string.reviews_title_text))
        setToolbarSubtitle(mSelectedCompany.name)

        mRatingViewCompany = findViewById(R.id.rating_view_company) as CarmenRatingView
        mRatingViewCompany.onRatingBarChangeListener = this
        mRatingViewCompany.rating = mUpdatingRating.totalRating

        mRatingViewCompanyPrice = findViewById(R.id.rating_view_company_price) as CarmenRatingView
        mRatingViewCompanyPrice.onRatingBarChangeListener = this
        mRatingViewCompanyPrice.rating = 1f

        findViewById(R.id.button_send).setOnClickListener { sendUpdatedRating() }
    }

    private fun sendUpdatedRating() {
        mUpdatingRating.totalRating = mRatingViewCompany.rating
        mUpdatingRating.price = mRatingViewCompanyPrice.rating.toInt()
        mUpdatingRating.title = (findViewById(R.id.filter_edit_text_title) as FilterEditText).text
        mUpdatingRating.text = (findViewById(R.id.filter_edit_text_review) as FilterEditText).text

        val senderName: String = (findViewById(R.id.filter_edit_text_name) as FilterEditText).text
        if (senderName.isNotEmpty()) {
            mUpdatingRating.displayName = senderName
        }

        mPresenter.sendCompanyRating(mUpdatingRating)
    }
}