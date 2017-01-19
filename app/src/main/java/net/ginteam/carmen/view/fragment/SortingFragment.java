package net.ginteam.carmen.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.ginteam.carmen.R;
import net.ginteam.carmen.contract.SortingContract;
import net.ginteam.carmen.model.SortingModel;
import net.ginteam.carmen.presenter.SortingPresenter;
import net.ginteam.carmen.view.adapter.SortingListAdapter;

import java.util.List;

public class SortingFragment extends BaseFetchingFragment implements SortingContract.View {

    private static final String SORTING_ARG = "sorting_arg";

    private SortingContract.Presenter mPresenter;

    private SortingListAdapter mRecyclerListAdapter;
    private RecyclerView mRecyclerViewSorting;

    private int mCategoryId;

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
        updateDependencies();

        mPresenter = new SortingPresenter(this);
        mPresenter.attachView(this);
        mPresenter.fetchSortingForCategory(mCategoryId);

        return mRootView;
    }

    @Override
    public void showSorting(List<SortingModel> sortingModels) {
        mRecyclerListAdapter = new SortingListAdapter(getContext(), sortingModels);
        mRecyclerViewSorting.setAdapter(mRecyclerListAdapter);
    }

    private void updateDependencies() {
        mRecyclerViewSorting = (RecyclerView) mRootView.findViewById(R.id.recycler_view_sorting);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerViewSorting.setLayoutManager(layoutManager);
    }
}
