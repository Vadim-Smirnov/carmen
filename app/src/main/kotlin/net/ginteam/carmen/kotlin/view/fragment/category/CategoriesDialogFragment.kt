package net.ginteam.carmen.kotlin.view.fragment.category

import net.ginteam.carmen.R
import net.ginteam.carmen.kotlin.model.CategoryModel

/**
 * Created by eugene_shcherbinock on 2/16/17.
 */

class CategoriesDialogFragment : CategoriesFragment() {

    override fun getLayoutResId(): Int = R.layout.fragment_dialog_category_list

    /**
     * onCategoryItemClick implementation for {@link CategoriesAdapter}
     */
    override fun invoke(category: CategoryModel) {
        super.invoke(category)
        dialog.dismiss()
    }

    override fun updateViewDependencies() {
        super.updateViewDependencies()
        mFragmentView.findViewById(R.id.button_category_dialog_cancel).setOnClickListener {
            dialog.dismiss()
        }
    }

}