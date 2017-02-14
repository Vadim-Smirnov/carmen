package net.ginteam.carmen.kotlin.contract

import net.ginteam.carmen.kotlin.model.CategoryModel

/**
 * Created by eugene_shcherbinock on 2/13/17.
 */
object CategoriesContract {

    interface View : BaseContract.View {

        fun showCategories(categories: List <CategoryModel>)

    }

    interface Presenter : BaseContract.Presenter<View> {

        fun fetchCategories()

    }

}