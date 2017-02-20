package net.ginteam.carmen.kotlin.presenter.sort

import net.ginteam.carmen.kotlin.contract.SortContract
import net.ginteam.carmen.kotlin.manager.PreferencesManager
import net.ginteam.carmen.kotlin.manager.SharedPreferencesManager
import net.ginteam.carmen.kotlin.model.CategoryModel
import net.ginteam.carmen.kotlin.model.SortModel
import net.ginteam.carmen.kotlin.presenter.BasePresenter
import net.ginteam.carmen.kotlin.provider.OfflineSortOptionsDataSourceProvider
import net.ginteam.carmen.kotlin.provider.SortOptionsDataSourceProvider
import net.ginteam.carmen.kotlin.view.fragment.sort.SortOptionDialogFragmentViewState

/**
 * Created by eugene_shcherbinock on 2/20/17.
 */
class SortPresenter : BasePresenter <SortContract.View>(), SortContract.Presenter {

    private val mPreferences: PreferencesManager = SharedPreferencesManager
    private val mSortOptionsDataSourceProvider: SortOptionsDataSourceProvider = OfflineSortOptionsDataSourceProvider()

    override fun fetchSortOptions(category: CategoryModel) {
        mView?.showLoading(true)

        mSortOptionsDataSourceProvider
                .fetchSortOptions(category)
                .subscribe({
                    val filteredOptions: List <SortModel> = removeDistanceOptionIfUserLocationNotExist(it)
                    mView?.showLoading(false)
                    mView?.showSortOptions(filteredOptions)
                })
    }

    override fun saveCurrentViewState() {
        mView?.let {
            val currentViewState: SortContract.SortViewState = it.getCurrentViewState()
            val viewStateSaver: SortContract.SortViewState = SortOptionDialogFragmentViewState

            viewStateSaver.categoryId = currentViewState.categoryId
            viewStateSaver.checkedOptionIndex = currentViewState.checkedOptionIndex
            viewStateSaver.sortFieldName = currentViewState.sortFieldName
            viewStateSaver.sortType = currentViewState.sortType
        }
    }

    override fun tryToRestoreViewState(category: CategoryModel): SortContract.SortViewState? {
        val sortViewState: SortContract.SortViewState = SortOptionDialogFragmentViewState
        if (category.id == sortViewState.categoryId) {
            return sortViewState
        }
        return null
    }

    private fun removeDistanceOptionIfUserLocationNotExist(originalOptions: List <SortModel>): List <SortModel> {
        val mutableOriginalOptions: MutableList <SortModel> = originalOptions.toMutableList()
        if (mPreferences.userLocation == null) {
            val distanceOption: SortModel? = mutableOriginalOptions.find {
                it.sortField == "distance"
            }
            distanceOption?.let {
                mutableOriginalOptions.remove(it)
            }
        }
        return mutableOriginalOptions
    }

}