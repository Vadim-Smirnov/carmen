package net.ginteam.carmen.kotlin.contract

import net.ginteam.carmen.kotlin.model.CategoryModel
import net.ginteam.carmen.kotlin.model.FilterModel
import net.ginteam.carmen.view.custom.FilterEditText

/**
 * Created by eugene_shcherbinock on 2/20/17.
 */

object FiltersContract {

    interface ViewState : ViewStateContract.ViewState {

        var filterQuery: String
        var fieldsList: List <FilterEditText>
        fun restoreFieldsState(fields: List <FilterEditText>) {}

    }

    interface View : ViewStateContract.View <ViewState> {

        fun showCounterLoading(show: Boolean)
        fun updateFilterResultsCount(count: Int)
        fun showFilters(filters: List <FilterModel>)

    }

    interface Presenter : ViewStateContract.Presenter <ViewState, View> {

        fun fetchFilters(category: CategoryModel)
        fun updateResultsCount(category: CategoryModel, filterQuery: String)

    }

}