package net.ginteam.carmen.kotlin.presenter.filter

import android.util.Log
import net.ginteam.carmen.kotlin.api.response.MetaSubscriber
import net.ginteam.carmen.kotlin.contract.FiltersContract
import net.ginteam.carmen.kotlin.manager.PreferencesManager
import net.ginteam.carmen.kotlin.manager.SharedPreferencesManager
import net.ginteam.carmen.kotlin.model.CategoryModel
import net.ginteam.carmen.kotlin.model.CompanyModel
import net.ginteam.carmen.kotlin.model.PaginationModel
import net.ginteam.carmen.kotlin.presenter.BasePresenter
import net.ginteam.carmen.kotlin.provider.FiltersDataSourceProvider
import net.ginteam.carmen.kotlin.provider.OfflineFiltersDataSourceProvider
import net.ginteam.carmen.kotlin.view.activity.filter.FiltersActivityViewState

/**
 * Created by eugene_shcherbinock on 2/20/17.
 */
class FiltersPresenter : BasePresenter <FiltersContract.View>(), FiltersContract.Presenter {

    private val mPreferences: PreferencesManager = SharedPreferencesManager
    private val mFiltersDataSourceProvider: FiltersDataSourceProvider = OfflineFiltersDataSourceProvider()

    override fun fetchFilters(category: CategoryModel) {
        mView?.showLoading(true)

        mFiltersDataSourceProvider
                .fetchFiltersForCategory(category.id)
                .subscribe({
                    Log.d("FiltersPresenter", "Filters count ${it.size}")
                    mView?.showLoading(false)
                    mView?.showFilters(it)
                })
    }

    override fun updateResultsCount(category: CategoryModel, filterQuery: String) {
        mView?.showCounterLoading(true)

        val filter = filterQuery.plus("city_id:${mPreferences.userCityModel!!.id}")
        mFiltersDataSourceProvider
                .fetchCompaniesCountWithParameters(category.id, filter)
                .subscribe(object : MetaSubscriber <List <CompanyModel>>() {
                    override fun success(model: List<CompanyModel>, pagination: PaginationModel) {
                        mView?.updateFilterResultsCount(pagination.totalItems)
                    }

                    override fun error(message: String, isNetworkError: Boolean) {
                        mView?.showError(message, isNetworkError)
                    }
                })
    }

    override fun saveCurrentViewState() {
        mView?.let {
            val currentViewState: FiltersContract.ViewState = it.getCurrentViewState()
            val viewStateSaver: FiltersContract.ViewState = FiltersActivityViewState

            with(currentViewState) {
                viewStateSaver.categoryId = categoryId
                viewStateSaver.filterQuery = filterQuery
                viewStateSaver.fieldsList = fieldsList
            }
        }
    }

    override fun tryToRestoreViewState(category: CategoryModel): FiltersContract.ViewState? {
        val filtersViewState: FiltersContract.ViewState = FiltersActivityViewState
        if (category.id == filtersViewState.categoryId) {
            return filtersViewState
        }
        return null
    }
}