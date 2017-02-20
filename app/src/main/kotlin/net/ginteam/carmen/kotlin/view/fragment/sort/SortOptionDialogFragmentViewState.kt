package net.ginteam.carmen.kotlin.view.fragment.sort

import android.content.Context
import net.ginteam.carmen.CarmenApplication
import net.ginteam.carmen.kotlin.contract.SortContract

/**
 * Created by eugene_shcherbinock on 2/20/17.
 */
object SortOptionDialogFragmentViewState : SortContract.SortViewState {

    private const val NAME = "sort_view_state"
    private const val CATEGORY_ID = "category_id"
    private const val CHECKED_OPTION_INDEX = "checked_option_index"
    private const val SORT_FIELD_NAME = "sort_field_name"
    private const val SORT_TYPE = "sort_type"

    private val mPreferences
            = CarmenApplication
            .getContext()
            .getSharedPreferences(NAME, Context.MODE_PRIVATE)

    override var categoryId: Int
        get() = mPreferences.getInt(CATEGORY_ID, -1)
        set(value) {
            mPreferences.edit().putInt(CATEGORY_ID, value).apply()
        }

    override var checkedOptionIndex: Int
        get() = mPreferences.getInt(CHECKED_OPTION_INDEX, 0)
        set(value) {
            mPreferences.edit().putInt(CHECKED_OPTION_INDEX, value).apply()
        }

    override var sortFieldName: String
        get() = mPreferences.getString(SORT_FIELD_NAME, "rating")
        set(value) {
            mPreferences.edit().putString(SORT_FIELD_NAME, value).apply()
        }

    override var sortType: String
        get() = mPreferences.getString(SORT_TYPE, "desc")
        set(value) {
            mPreferences.edit().putString(SORT_TYPE, value).apply()
        }

}