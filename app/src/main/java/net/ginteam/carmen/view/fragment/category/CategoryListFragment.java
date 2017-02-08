package net.ginteam.carmen.view.fragment.category;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.ginteam.carmen.R;
import net.ginteam.carmen.contract.category.CategoriesContract;
import net.ginteam.carmen.model.category.CategoryModel;
import net.ginteam.carmen.presenter.category.CategoriesPresenter;
import net.ginteam.carmen.view.adapter.category.CategoryItemViewHolder;
import net.ginteam.carmen.view.adapter.category.CategoryRecyclerListAdapter;
import net.ginteam.carmen.view.adapter.category.CategoryRecyclerListItemDecorator;
import net.ginteam.carmen.view.fragment.BaseFetchingFragment;

import java.util.List;

public class CategoryListFragment extends BaseFetchingFragment implements CategoriesContract.View,
        CategoryItemViewHolder.OnCategoryItemClickListener {

    private static final int CATEGORY_LIST_COLUMNS_COUNT = 2;
    private static final String DIALOG_ARG = "dialog_arg";

    private RecyclerView mRecyclerViewCategories;

    private CategoriesContract.Presenter mPresenter;
    private CategoryRecyclerListAdapter mRecyclerListAdapter;

    private OnCategorySelectedListener mCategorySelectedListener;

    private TextView mTextViewCategoryDialogTitle;
    private TextView mButtonCancelCategoryDialog;

    private boolean mIsDialog;

    public CategoryListFragment() {}

    public static CategoryListFragment newInstance(boolean isDialog) {
        CategoryListFragment categoryListFragment = new CategoryListFragment();

        Bundle bundle = new Bundle();
        bundle.putBoolean(DIALOG_ARG, isDialog);
        categoryListFragment.setArguments(bundle);

        return categoryListFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.DialogStyle);
        mIsDialog = getArguments().getBoolean(DIALOG_ARG);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (isNotNestedFragment() && !mIsDialog) {
            setToolbarTitle(getString(R.string.category_item_text), "");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflateBaseFragment(mIsDialog ? R.layout.fragment_dialog_category_list :
                R.layout.fragment_category_list, inflater, container, savedInstanceState);

        updateDependencies();

        if (mIsDialog) {
            initializeDialogElements();
        }

        mPresenter = new CategoriesPresenter();
        mPresenter.attachView(this);
        mPresenter.fetchCategories();

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
    public void showCategories(List<CategoryModel> categories) {
        mRecyclerListAdapter = new CategoryRecyclerListAdapter(getContext(), categories, mIsDialog);
        mRecyclerListAdapter.setOnCategoryClickListener(this);
        mRecyclerViewCategories.setAdapter(mRecyclerListAdapter);
    }

    @Override
    public void onCategoryItemClick(CategoryModel category) {
        if (mCategorySelectedListener == null) {
            Log.e("CategoryListFragment", "OnCategorySelected listener is null!");
            return;
        }
        if (getDialog() != null) {
            getDialog().cancel();
        }
        mCategorySelectedListener.onCategorySelected(category, mIsDialog);
    }

    private void updateDependencies() {
        mRecyclerViewCategories = (RecyclerView) mRootView.findViewById(R.id.recycler_view_categories);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), CATEGORY_LIST_COLUMNS_COUNT);
        mRecyclerViewCategories.addItemDecoration(new CategoryRecyclerListItemDecorator(getContext(), R.dimen.category_item_spacing));
        mRecyclerViewCategories.setLayoutManager(gridLayoutManager);
    }

    private void initializeDialogElements() {
        mTextViewCategoryDialogTitle = (TextView) mRootView.findViewById(R.id.text_view_category_dialog_title);
        mButtonCancelCategoryDialog = (TextView) mRootView.findViewById(R.id.button_category_dialog_cancel);
        mButtonCancelCategoryDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().cancel();
            }
        });
    }

    public interface OnCategorySelectedListener {

        void onCategorySelected(CategoryModel category, boolean fromDialog);

    }

}
