package net.ginteam.carmen.kotlin.view.activity.map

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.MenuItem
import com.google.android.gms.maps.model.LatLng
import net.ginteam.carmen.R
import net.ginteam.carmen.kotlin.contract.MapActivityContract
import net.ginteam.carmen.kotlin.interfaces.Filterable
import net.ginteam.carmen.kotlin.model.CategoryModel
import net.ginteam.carmen.kotlin.model.CompanyModel
import net.ginteam.carmen.kotlin.prepareFragment
import net.ginteam.carmen.kotlin.presenter.company.map.MapActivityPresenter
import net.ginteam.carmen.kotlin.view.activity.BaseLocationActivity
import net.ginteam.carmen.kotlin.view.activity.CompanyDetailsActivity
import net.ginteam.carmen.kotlin.view.activity.filter.FiltersActivity
import net.ginteam.carmen.kotlin.view.fragment.company.BaseCompaniesFragment
import net.ginteam.carmen.kotlin.view.fragment.company.map.MapCompaniesFragment
import net.ginteam.carmen.kotlin.view.fragment.company.map.MapCompanyFragment

class MapActivity : BaseLocationActivity <MapActivityContract.View, MapActivityContract.Presenter>(),
        MapActivityContract.View, BaseCompaniesFragment.OnCompanySelectedListener,
        MapCompaniesFragment.OnShowFiltersActivityListener {

    override var mPresenter: MapActivityContract.Presenter = MapActivityPresenter()

    private var mSelectedCategory: CategoryModel? = null
    private var mSelectedCompany: CompanyModel? = null

    private lateinit var mCurrentFragment: Fragment

    companion object {
        const val CATEGORY_ARGUMENT = "category"
        const val COMPANY_ARGUMENT = "company"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // if we receive category
        mSelectedCategory?.let {
            // then we need to fetch location
            // and show MapCompaniesFragment
            mPresenter.fetchUserLocation()
        }

        // if we receive company
        mSelectedCompany?.let {
            // then we need to show MapCompanyFragment
            // with only one company
            mCurrentFragment = MapCompanyFragment.newInstance(it)
            prepareFragment(R.id.main_fragment_container, mCurrentFragment)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        finish()
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            FiltersActivity.FILTER_CONFIRM_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    // set filters for map fragment
                    (mCurrentFragment as? Filterable)?.setFilterQuery(
                            data!!.getStringExtra(FiltersActivity.RESULT_FILTER_ARGUMENT)
                    )
                }
            }
            else -> {
                super.onActivityResult(requestCode, resultCode, data)
            }
        }
    }

    override fun onCompanySelected(company: CompanyModel) {
        val intent = Intent(getContext(), CompanyDetailsActivity::class.java)
        intent.putExtra(CompanyDetailsActivity.COMPANY_ARGUMENT, company)
        startActivity(intent)
    }

    override fun onShowFiltersActivity(category: CategoryModel) {
        val intent = Intent(getContext(), FiltersActivity::class.java)
        intent.putExtra(FiltersActivity.CATEGORY_ARGUMENT, category)
        startActivityForResult(intent, FiltersActivity.FILTER_CONFIRM_REQUEST_CODE)
    }

    override fun getLayoutResId(): Int = R.layout.activity_map_new

    override fun showCompaniesMapFragment(startLocation: LatLng) {
        mCurrentFragment = MapCompaniesFragment.newInstance(mSelectedCategory!!, startLocation)
        prepareFragment(R.id.main_fragment_container, mCurrentFragment)
    }

    override fun updateViewDependencies() {
        super.updateViewDependencies()

        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_close_map)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun updateDependencies() {
        super.updateDependencies()

        mSelectedCategory = intent.getSerializableExtra(CATEGORY_ARGUMENT) as CategoryModel?
        mSelectedCompany = intent.getSerializableExtra(COMPANY_ARGUMENT) as? CompanyModel?
    }
}
