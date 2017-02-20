package net.ginteam.carmen.kotlin.view.fragment.sort

import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import net.ginteam.carmen.R
import net.ginteam.carmen.kotlin.contract.SortContract
import net.ginteam.carmen.kotlin.model.CategoryModel
import net.ginteam.carmen.kotlin.model.SortModel
import net.ginteam.carmen.kotlin.presenter.sort.SortPresenter
import net.ginteam.carmen.kotlin.view.fragment.BaseFragment
import java.util.*

/**
 * Created by eugene_shcherbinock on 2/20/17.
 */
class SortOptionsDialogFragment : BaseFragment <SortContract.View, SortContract.Presenter>(), SortContract.View {

    override var mPresenter: SortContract.Presenter = SortPresenter()

    private lateinit var mSelectedCategory: CategoryModel

    // default sort values
    private var mCheckedOptionIndex: Int = 0
    private var mSortFieldName: String = "rating"
    private var mSortType: String = "desc"

    private lateinit var mRadioGroupSortOptions: RadioGroup
    private lateinit var mRadioGroupItems: MutableList <RadioButton>

    private var mSortOptionSelectedListener: OnSortOptionSelectedListener? = null

    companion object {
        private const val SORT_FIELD_TAG = R.string.sort_field_tag_id
        private const val SORT_TYPE_TAG = R.string.sort_type_tag_id
        private const val CATEGORY_ARGUMENT = "category"

        fun newInstance(category: CategoryModel): SortOptionsDialogFragment {
            val bundle = Bundle()
            bundle.putSerializable(CATEGORY_ARGUMENT, category)

            val instance: SortOptionsDialogFragment = SortOptionsDialogFragment()
            instance.arguments = bundle
            return instance
        }

    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mSortOptionSelectedListener = context as OnSortOptionSelectedListener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSelectedCategory = arguments.getSerializable(CATEGORY_ARGUMENT) as CategoryModel
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPresenter.fetchSortOptions(mSelectedCategory)
    }

    override fun getCurrentViewState(): SortContract.ViewState {
        return object : SortContract.ViewState {
            override var categoryId: Int = mSelectedCategory.id
            override var checkedOptionIndex: Int = mCheckedOptionIndex
            override var sortFieldName: String = mSortFieldName
            override var sortType: String = mSortType
        }
    }

    override fun showSortOptions(options: List<SortModel>) {
        mRadioGroupItems = ArrayList <RadioButton>()
        for ((name, sortField, sortType) in options) {
            val currentOptionButton: RadioButton = initializeRadioButton()
            currentOptionButton.text = name
            currentOptionButton.setTag(SORT_FIELD_TAG, sortField)
            currentOptionButton.setTag(SORT_TYPE_TAG, sortType)

            mRadioGroupItems.add(currentOptionButton)
            mRadioGroupSortOptions.addView(currentOptionButton)
        }
        updateSortOptionsDependencies()
    }

    override fun getLayoutResId(): Int = R.layout.fragment_sorting

    override fun updateViewDependencies() {
        super.updateViewDependencies()

        mRadioGroupSortOptions = mFragmentView.findViewById(R.id.radio_group_sorting) as RadioGroup

        mFragmentView.findViewById(R.id.button_sorting_dialog_cancel).setOnClickListener {
            dialog.dismiss()
        }
        mFragmentView.findViewById(R.id.button_confirm_sort).setOnClickListener {
            confirmSortOption()
            dialog.dismiss()
        }
    }

    private fun initializeRadioButton(): RadioButton {
        val buttonMargin = resources.getDimension(R.dimen.sort_item_margin).toInt()
        val buttonTitlePadding = resources.getDimension(R.dimen.sort_title_left_margin).toInt()
        val radioGroupLayoutParams: RadioGroup.LayoutParams
                = RadioGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        radioGroupLayoutParams.topMargin = buttonMargin

        val radioButton: RadioButton = RadioButton(context)
        radioButton.buttonDrawable = ContextCompat.getDrawable(activity, R.drawable.radiobutton_selector)
        radioButton.layoutParams = radioGroupLayoutParams
        radioButton.typeface = Typeface.createFromAsset(activity.assets, getString(R.string.open_sans_font))
        radioButton.setPadding(buttonTitlePadding, 0, 0, 0)

        return radioButton
    }

    private fun updateSortOptionsDependencies() {
        val restoredViewState: SortContract.ViewState? = mPresenter.tryToRestoreViewState(mSelectedCategory)
        if (restoredViewState == null) {
            mRadioGroupItems[0].isChecked = true
            return
        }
        with(restoredViewState) {
            mSortFieldName = sortFieldName
            mSortType = sortType
            mRadioGroupItems[checkedOptionIndex].isChecked = true
        }
    }

    private fun confirmSortOption() {
        val checkedOptionId: Int = mRadioGroupSortOptions.checkedRadioButtonId
        val checkedOptionButton: RadioButton? = mFragmentView.findViewById(checkedOptionId) as RadioButton
        checkedOptionButton?.let {
            mCheckedOptionIndex = mRadioGroupSortOptions.indexOfChild(it)
            mSortFieldName = it.getTag(SORT_FIELD_TAG).toString()
            mSortType = it.getTag(SORT_TYPE_TAG).toString()

            mPresenter.saveCurrentViewState()
            mSortOptionSelectedListener?.let {
                it.onSortOptionSelected(mSortFieldName, mSortType)
            }
        }
    }

    interface OnSortOptionSelectedListener {

        fun onSortOptionSelected(field: String, type: String)

    }

}