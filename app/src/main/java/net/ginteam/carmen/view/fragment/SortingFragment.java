package net.ginteam.carmen.view.fragment;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import net.ginteam.carmen.R;
import net.ginteam.carmen.contract.SortingContract;
import net.ginteam.carmen.manager.SortViewStateManager;
import net.ginteam.carmen.model.SortingModel;
import net.ginteam.carmen.presenter.SortingPresenter;

import java.util.ArrayList;
import java.util.List;

public class SortingFragment extends BaseFetchingFragment implements SortingContract.View, View.OnClickListener {

    private static final String SORTING_ARG = "sorting_arg";
    private static final int SORT_FIELD = R.string.sort_field_tag_id;
    private static final int SORT_TYPE = R.string.sort_type_tag_id;

    private SortingContract.Presenter mPresenter;

    private List<RadioButton> mRadioButtonList;
    private RadioGroup mRadioGroup;

    private int mCategoryId;
    private String mSortedField;
    private String mSortedType;
    private OnSortTypeSelectedListener mSortTypeSelectedListener;

    public SortingFragment() {}

    public static SortingFragment newInstance(int categoryId) {
        SortingFragment sortingFragment = new SortingFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(SORTING_ARG, categoryId);
        sortingFragment.setArguments(bundle);
        return sortingFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCategoryId = getArguments().getInt(SORTING_ARG);
        setStyle(STYLE_NO_TITLE, R.style.DialogStyle);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mSortTypeSelectedListener = (OnSortTypeSelectedListener) context;
        } catch (ClassCastException exception) {
            Log.e("SortingFragment", "Parent context does not confirm to OnSortTypeSelectedListener!");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflateBaseFragment(R.layout.fragment_sorting, inflater, container, savedInstanceState);

        updateDependencies();

        mPresenter = new SortingPresenter();
        mPresenter.attachView(this);
        mPresenter.fetchSortingForCategory(mCategoryId);

        return mRootView;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_confirm_sort) {
            // Find checked radio button
            int checkedItemId = mRadioGroup.getCheckedRadioButtonId();
            RadioButton checkedView = (RadioButton) mRootView.findViewById(checkedItemId);

            if (checkedView == null) {
                return;
            }

            // Get sort params from checked view
            int checkedItemIndex = mRadioGroup.indexOfChild(checkedView);
            mSortedField = checkedView.getTag(SORT_FIELD).toString();
            mSortedType = checkedView.getTag(SORT_TYPE).toString();

            if (mSortTypeSelectedListener != null) {
                mSortTypeSelectedListener.onSortSelected(mSortedField, mSortedType);
            }

            mPresenter.saveViewState(mCategoryId, checkedItemIndex, mSortedField, mSortedType);
        }
        getDialog().dismiss();
    }

    @Override
    public void showSorting(List<SortingModel> sortingModels) {
        mRadioButtonList = new ArrayList<>();

        int sortItemMargin = (int) getResources().getDimension(R.dimen.sort_item_margin);
        int sortTitlePadding = (int) getResources().getDimension(R.dimen.sort_title_left_margin);

        RadioGroup.LayoutParams layoutParams =
                new RadioGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
        layoutParams.topMargin = sortItemMargin;

        for (SortingModel currentFilter : sortingModels) {
            RadioButton radioButton = new RadioButton(getContext());
            radioButton.setText(currentFilter.getName());
            radioButton.setButtonDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.radiobutton_selector));
            radioButton.setLayoutParams(layoutParams);
            radioButton.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), getString(R.string.open_sans_font)));
            radioButton.setPadding(sortTitlePadding, 0, 0, 0);

            radioButton.setTag(SORT_FIELD, currentFilter.getSortedField());
            radioButton.setTag(SORT_TYPE, currentFilter.getSortedType());

            mRadioButtonList.add(radioButton);
        }
        updateSortingDependencies();
    }

    private void updateSortingDependencies() {
        for (RadioButton currentView : mRadioButtonList) {
            mRadioGroup.addView(currentView);
        }

        SortViewStateManager.SortViewState sortViewState = mPresenter.restoreViewState(mCategoryId);
        if (sortViewState != null) {
            mSortedField = sortViewState.getSortField();
            mSortedType = sortViewState.getSortType();
            mRadioButtonList.get(sortViewState.getCheckedViewIndex()).setChecked(true);
        }
    }

    private void updateDependencies() {
        mRadioGroup = (RadioGroup) mRootView.findViewById(R.id.radio_group_sorting);
        mRootView.findViewById(R.id.button_sorting_dialog_cancel).setOnClickListener(this);
        mRootView.findViewById(R.id.button_confirm_sort).setOnClickListener(this);
    }

    public interface OnSortTypeSelectedListener {

        void onSortSelected(String sortField, String sortType);

    }

}
