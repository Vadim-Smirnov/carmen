package net.ginteam.carmen.view.fragment;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import net.ginteam.carmen.R;
import net.ginteam.carmen.contract.SortingContract;
import net.ginteam.carmen.model.SortingModel;
import net.ginteam.carmen.presenter.SortingPresenter;

import java.util.ArrayList;
import java.util.List;

public class SortingFragment extends BaseFetchingFragment implements SortingContract.View {

    private static final String SORTING_ARG = "sorting_arg";

    private SortingContract.Presenter mPresenter;

    private List<RadioButton> mRadioButtonList;
    private RadioGroup mRadioGroup;

    private int mCategoryId;

    private Button mButtonCancelDialog;

    public SortingFragment() {}

    public static SortingFragment newInstance(int categoryId) {
        SortingFragment sortingFragment = new SortingFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(SORTING_ARG, categoryId);
        sortingFragment.setArguments(bundle);
        return sortingFragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() == null) {
            return;
        }
        int dialogWidth = (int) getResources().getDimension(R.dimen.dialog_fragment_width);
        int dialogHeight = (int) getResources().getDimension(R.dimen.dialog_fragment_height);

        getDialog().getWindow().setLayout(dialogWidth, dialogHeight);
        getDialog().setCancelable(false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCategoryId = getArguments().getInt(SORTING_ARG);
        setStyle(STYLE_NO_TITLE, R.style.DialogStyle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflateBaseFragment(R.layout.fragment_sorting, inflater, container, savedInstanceState);

        initialize();
        mPresenter = new SortingPresenter(this);
        mPresenter.attachView(this);
        mPresenter.fetchSortingForCategory(mCategoryId);

        return mRootView;
    }

    @Override
    public void showSorting(List<SortingModel> sortingModels) {
        mRadioButtonList = new ArrayList<>();
        for (SortingModel currentFilter : sortingModels) {
            RadioButton radioButton = new RadioButton(getContext());
            radioButton.setText(currentFilter.getName());
            radioButton.setButtonDrawable(ContextCompat.getDrawable(getActivity(),
                    R.drawable.radiobutton_selector));
            mRadioButtonList.add(radioButton);
        }
        updateSortingDependencies();
    }

    private void updateSortingDependencies() {
        for (RadioButton currentFilter : mRadioButtonList) {
            mRadioGroup.addView(currentFilter);
        }
        mRadioButtonList.get(0).setChecked(true);
    }

    private void initialize() {
        mRadioGroup = (RadioGroup) mRootView.findViewById(R.id.radio_group_sorting);
        mButtonCancelDialog = (Button) mRootView.findViewById(R.id.button_sorting_dialog_cancel);
        mButtonCancelDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().cancel();
            }
        });
    }

}
