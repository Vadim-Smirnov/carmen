package net.ginteam.carmen.kotlin.presenter.category

import net.ginteam.carmen.kotlin.api.response.ModelSubscriber
import net.ginteam.carmen.kotlin.contract.CategoriesContract
import net.ginteam.carmen.kotlin.manager.PreferencesManager
import net.ginteam.carmen.kotlin.manager.SharedPreferencesManager
import net.ginteam.carmen.kotlin.model.CategoryModel
import net.ginteam.carmen.kotlin.presenter.BasePresenter
import net.ginteam.carmen.kotlin.provider.CategoryDataSourceProvider
import net.ginteam.carmen.kotlin.provider.OnlineCategoryDataSourceProvider

/**
 * Created by eugene_shcherbinock on 2/16/17.
 */
class CategoriesPresenter : BasePresenter <CategoriesContract.View>(), CategoriesContract.Presenter {

    private val mCategoriesProvider: CategoryDataSourceProvider = OnlineCategoryDataSourceProvider()

    override fun fetchCategories() {
        mView?.showLoading(true)

        val preferences: PreferencesManager = SharedPreferencesManager
        mCategoriesProvider
                // it can not be null in this place
                .fetchCategories(preferences.userCityModel!!.id)
                .subscribe(object : ModelSubscriber <List <CategoryModel>>() {
                    override fun success(model: List<CategoryModel>) {
                        mView?.showLoading(false)
                        mView?.showCategories(model)
                    }

                    override fun error(message: String, isNetworkError: Boolean) {
                        mView?.showError(message, isNetworkError)
                    }
                })
    }

}