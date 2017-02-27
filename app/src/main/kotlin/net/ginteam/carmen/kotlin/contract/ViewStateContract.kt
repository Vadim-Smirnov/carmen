package net.ginteam.carmen.kotlin.contract

import net.ginteam.carmen.kotlin.model.CategoryModel

/**
 * Created by eugene_shcherbinock on 2/20/17.
 */

object ViewStateContract {

    interface ViewState {

        var categoryId: Int

        fun resetViewState() {
            categoryId = -1
        }

    }

    interface View<out T : ViewState> : BaseContract.View {

        fun getCurrentViewState(): T

    }

    interface Presenter<out T : ViewState, in V : View <T>> : BaseContract.Presenter <V> {

        fun saveCurrentViewState()
        fun tryToRestoreViewState(category: CategoryModel): T?

    }

}