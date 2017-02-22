package net.ginteam.carmen.kotlin.view.activity.map

import android.app.Activity
import android.content.Intent
import android.os.Bundle
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
import net.ginteam.carmen.kotlin.view.activity.filter.FiltersActivity
import net.ginteam.carmen.kotlin.view.fragment.company.BaseCompaniesFragment
import net.ginteam.carmen.kotlin.view.fragment.company.map.MapCompaniesFragment

class MapActivity : BaseLocationActivity <MapActivityContract.View, MapActivityContract.Presenter>(),
        MapActivityContract.View, BaseCompaniesFragment.OnCompanySelectedListener,
        MapCompaniesFragment.OnShowFiltersActivityListener {

    override var mPresenter: MapActivityContract.Presenter = MapActivityPresenter()

    private lateinit var mSelectedCategory: CategoryModel
    private lateinit var mMapFragment: MapCompaniesFragment

    companion object {
        const val CATEGORY_ARGUMENT = "category"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter.fetchUserLocation()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        super.onBackPressed()
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            FiltersActivity.FILTER_CONFIRM_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    // set filters for map fragment
                    (mMapFragment as? Filterable)?.setFilterQuery(
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

    }

    override fun onShowFiltersActivity(category: CategoryModel) {
        val intent = Intent(getContext(), FiltersActivity::class.java)
        intent.putExtra(FiltersActivity.CATEGORY_ARGUMENT, category)
        startActivityForResult(intent, FiltersActivity.FILTER_CONFIRM_REQUEST_CODE)
    }

    override fun getLayoutResId(): Int = R.layout.activity_map_new

    override fun showMapFragment(startLocation: LatLng) {
        mMapFragment = MapCompaniesFragment.newInstance(mSelectedCategory, startLocation)
        prepareFragment(R.id.main_fragment_container, mMapFragment)
    }

    override fun updateViewDependencies() {
        super.updateViewDependencies()

        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_close_map)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun updateDependencies() {
        super.updateDependencies()
        mSelectedCategory = intent.getSerializableExtra(CATEGORY_ARGUMENT) as CategoryModel
    }
}
