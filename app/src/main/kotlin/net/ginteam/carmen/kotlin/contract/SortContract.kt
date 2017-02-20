package net.ginteam.carmen.kotlin.contract

import net.ginteam.carmen.kotlin.model.CategoryModel
import net.ginteam.carmen.kotlin.model.SortModel

/**
 * Created by eugene_shcherbinock on 2/20/17.
 */
object SortContract {

    interface SortViewState : ViewStateContract.ViewState {

        var checkedOptionIndex: Int
        var sortFieldName: String
        var sortType: String

    }

    interface View : ViewStateContract.View <SortViewState> {

        fun showSortOptions(options: List <SortModel>)

    }

    interface Presenter : ViewStateContract.Presenter <SortViewState, View> {

        fun fetchSortOptions(category: CategoryModel)

    }

}