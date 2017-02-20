package net.ginteam.carmen.kotlin.view.fragment.category

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import net.ginteam.carmen.R
import net.ginteam.carmen.kotlin.contract.CategoriesContract
import net.ginteam.carmen.kotlin.model.CategoryModel
import net.ginteam.carmen.kotlin.presenter.category.CategoriesPresenter
import net.ginteam.carmen.kotlin.view.adapter.category.CategoriesAdapter
import net.ginteam.carmen.kotlin.view.fragment.BaseFragment
import net.ginteam.carmen.view.adapter.category.CategoryRecyclerListItemDecorator

/**
 * Created by eugene_shcherbinock on 2/16/17.
 */

open class CategoriesFragment : BaseFragment <CategoriesContract.View, CategoriesContract.Presenter>(),
        CategoriesContract.View, (CategoryModel) -> Unit {

    private val ITEMS_IN_ROW: Int = 2

    override var mPresenter: CategoriesContract.Presenter = CategoriesPresenter()

    protected var mCategorySelectedListener: OnCategorySelectedListener? = null

    private lateinit var mRecyclerViewCategories: RecyclerView
    private lateinit var mCategoriesAdapter: CategoriesAdapter

    companion object {
        fun newInstance(isDialog: Boolean): CategoriesFragment {
            return if (!isDialog) {
                CategoriesFragment()
            } else {
                CategoriesDialogFragment()
            }
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mCategorySelectedListener = context as OnCategorySelectedListener?
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        mPresenter.fetchCategories()
    }

    override fun getLayoutResId(): Int = R.layout.fragment_category_list

    override fun getNetworkErrorAction(): (() -> Unit)? = {
        mPresenter.fetchCategories()
    }

    override fun showCategories(categories: List<CategoryModel>) {
        mCategoriesAdapter = CategoriesAdapter(R.layout.list_item_category, categories, this)
        mRecyclerViewCategories.adapter = mCategoriesAdapter
    }

    /**
     * onCategoryItemClick implementation for {@link CategoriesAdapter}
     */
    override fun invoke(category: CategoryModel) {
        mCategorySelectedListener?.onCategorySelected(category, false)
    }

    override fun updateViewDependencies() {
        super.updateViewDependencies()

        mRecyclerViewCategories = mFragmentView.findViewById(R.id.recycler_view_categories) as RecyclerView
        mRecyclerViewCategories.addItemDecoration(CategoryRecyclerListItemDecorator(context, R.dimen.category_item_spacing))
        mRecyclerViewCategories.layoutManager = initializeGridLayoutManager()
    }

    private fun initializeGridLayoutManager(): GridLayoutManager {
        val gridLayoutManager = GridLayoutManager(context, ITEMS_IN_ROW)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                if (position == mCategoriesAdapter.itemCount - 1
                        && mCategoriesAdapter.itemCount % 2 != 0) {
                    return ITEMS_IN_ROW
                }
                return 1
            }
        }
        return gridLayoutManager
    }

    interface OnCategorySelectedListener {

        fun onCategorySelected(category: CategoryModel, fromDialogSelection: Boolean)

    }

}