package net.ginteam.carmen.kotlin.view.fragment.city

import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import net.ginteam.carmen.R
import net.ginteam.carmen.kotlin.contract.CitiesContract
import net.ginteam.carmen.kotlin.model.CityModel
import net.ginteam.carmen.kotlin.presenter.city.CitiesPresenter
import net.ginteam.carmen.kotlin.view.adapter.city.CitiesAdapter
import net.ginteam.carmen.kotlin.view.fragment.BaseFragment

/**
 * Created by eugene_shcherbinock on 2/16/17.
 */

class CitiesDialogFragment : BaseFragment <CitiesContract.View, CitiesContract.Presenter>(), CitiesContract.View {

    override var mPresenter: CitiesContract.Presenter = CitiesPresenter()

    private var mCitySelectedListener: OnCitySelectedListener? = null

    private lateinit var mRecyclerViewCities: RecyclerView
    private lateinit var mCitiesAdapter: CitiesAdapter

    companion object {
        fun newInstance(): CitiesDialogFragment = CitiesDialogFragment()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mCitySelectedListener = context as OnCitySelectedListener?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.DialogStyle)
    }

    override fun onStart() {
        super.onStart()
        dialog.setCancelable(false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        mPresenter.fetchCities()
    }

    override fun showLoading(show: Boolean, messageResId: Int) {
        if (show) {
            mFragmentView.findViewById(R.id.progress_bar).visibility = View.VISIBLE
            (mFragmentView.findViewById(R.id.text_view_loading_message) as TextView).text = getString(messageResId)
            mFragmentView.findViewById(R.id.layout_content).visibility = View.GONE
            return
        }
        mFragmentView.findViewById(R.id.progress_bar).visibility = View.GONE
        mFragmentView.findViewById(R.id.layout_content).visibility = View.VISIBLE
    }

    override fun getLayoutResId(): Int = R.layout.fragment_city_list

    override fun showCities(cities: List<CityModel>) {
        mCitiesAdapter = CitiesAdapter(cities, {
            mPresenter.saveSelectedCity(it)
            mCitySelectedListener?.onCitySelected(it)
        })
        mRecyclerViewCities.adapter = mCitiesAdapter
    }

    override fun updateViewDependencies() {
        super.updateViewDependencies()
        mRecyclerViewCities = mFragmentView.findViewById(R.id.recycler_view_cities) as RecyclerView
        mRecyclerViewCities.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    interface OnCitySelectedListener {

        fun onCitySelected(city: CityModel)

    }

}