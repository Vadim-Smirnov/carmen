package net.ginteam.carmen.view.fragment.category;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.ginteam.carmen.R;
import net.ginteam.carmen.contract.category.CategoriesContract;
import net.ginteam.carmen.model.category.CategoryModel;
import net.ginteam.carmen.presenter.category.CategoriesPresenter;
import net.ginteam.carmen.view.adapter.category.CategoryRecyclerListAdapter;
import net.ginteam.carmen.view.adapter.category.CategoryRecyclerListItemDecorator;
import net.ginteam.carmen.view.adapter.view_holder.category.CategoryItemViewHolder;
import net.ginteam.carmen.view.fragment.BaseFetchingFragment;

import java.util.List;

public class CategoryListFragment extends BaseFetchingFragment implements CategoriesContract.View,
        CategoryItemViewHolder.OnCategoryItemClickListener {

    private static final int CATEGORY_LIST_COLUMNS_COUNT = 2;

    protected RecyclerView mRecyclerViewCategories;

    private CategoriesContract.Presenter mPresenter;
    private CategoryRecyclerListAdapter mRecyclerListAdapter;

    private OnCategorySelectedListener mCategorySelectedListener;

    public CategoryListFragment() {}

    public static CategoryListFragment newInstance() {
        return new CategoryListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflateBaseFragment(R.layout.fragment_category_list, inflater, container, savedInstanceState);
        updateDependencies();

        mPresenter = new CategoriesPresenter();
        mPresenter.attachView(this);
        mPresenter.fetchData();

        return mRootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCategorySelectedListener = (OnCategorySelectedListener) context;
        } catch (ClassCastException exception) {
            Log.e("CategoryListFragment", "Parent context does not confirm to OnCategorySelectedListener!");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mPresenter.detachView();
    }

    @Override
    public void showCategories(List<CategoryModel> categories) {
        mRecyclerListAdapter = new CategoryRecyclerListAdapter(getContext(), categories);
        mRecyclerListAdapter.setOnCategoryClickListener(this);
        mRecyclerViewCategories.setAdapter(mRecyclerListAdapter);
    }

    @Override
    public void onCategoryItemClick(CategoryModel category) {
        if (mCategorySelectedListener == null) {
            Log.e("CategoryListFragment", "OnCategorySelected listener is null!");
            return;
        }
        mCategorySelectedListener.onCategorySelect(category);
    }

    private void updateDependencies() {
        mRecyclerViewCategories = (RecyclerView) mRootView.findViewById(R.id.recycler_view_categories);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), CATEGORY_LIST_COLUMNS_COUNT);
        mRecyclerViewCategories.addItemDecoration(new CategoryRecyclerListItemDecorator(getContext(), R.dimen.category_item_spacing));
        mRecyclerViewCategories.setLayoutManager(gridLayoutManager);
    }

    public interface OnCategorySelectedListener {

        void onCategorySelect(CategoryModel category);

    }

}
