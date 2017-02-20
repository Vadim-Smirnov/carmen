package net.ginteam.carmen.kotlin.view.activity.filter

import android.content.Context
import android.content.SharedPreferences
import net.ginteam.carmen.CarmenApplication
import net.ginteam.carmen.kotlin.contract.FiltersContract
import net.ginteam.carmen.view.custom.FilterEditText

/**
 * Created by eugene_shcherbinock on 2/20/17.
 */
object FiltersActivityViewState : FiltersContract.ViewState {

    private const val NAME = "filters_view_state"
    private const val CATEGORY_ID = "category_id"
    private const val FILTER_QUERY = "filter_query"
    private const val CURRENT_FIELD_QUERY = "query"

    private val mPreferences
            = CarmenApplication
            .getContext()
            .getSharedPreferences(NAME, Context.MODE_PRIVATE)

    override var categoryId: Int
        get() = mPreferences.getInt(CATEGORY_ID, -1)
        set(value) {
            mPreferences.edit().putInt(CATEGORY_ID, value).apply()
        }

    override var filterQuery: String
        get() = mPreferences.getString(FILTER_QUERY, "")
        set(value) {
            mPreferences.edit().putString(FILTER_QUERY, value).apply()
        }

    override var fieldsList: List<FilterEditText>
        get() = fieldsList
        set(value) {
            val editor: SharedPreferences.Editor = mPreferences.edit()
            for (currentFilter in value) {
                val fieldKey: String = currentFilter.hint
                val fieldValue: String = currentFilter.text

                // save query for field
                editor.putString("${fieldKey}_$CURRENT_FIELD_QUERY", currentFilter.filterQuery)

                // save text for field
                editor.putString(fieldKey, fieldValue)
            }
            editor.apply()
        }

    override fun restoreFieldsState(fields: List<FilterEditText>) {
        for (currentField in fields) {
            val fieldKey = currentField.hint
            val fieldValue = mPreferences.getString(fieldKey, "")

            currentField.filterQuery = mPreferences.getString("${fieldKey}_$CURRENT_FIELD_QUERY", "")
            currentField.text = fieldValue
        }
    }
}