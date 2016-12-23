package net.ginteam.carmen.view.fragment.category;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.ginteam.carmen.R;
import net.ginteam.carmen.contract.category.CategoriesContract;
import net.ginteam.carmen.model.category.CategoryModel;
import net.ginteam.carmen.presenter.category.CategoriesPresenter;
import net.ginteam.carmen.view.fragment.BaseFetchingFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryListFragment extends BaseFetchingFragment implements CategoriesContract.View {

    @BindView(R.id.recycler_view_categories)
    protected RecyclerView mRecyclerViewCategories;

    private CategoriesContract.Presenter mPresenter;

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

        mUnbinder = ButterKnife.bind(this, mRootView);

        mPresenter = new CategoriesPresenter();
        mPresenter.attachView(this);
        mPresenter.fetchData();

        return mRootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mPresenter.detachView();
        mUnbinder.unbind();
    }

    @Override
    public void showCategories(List<CategoryModel> categories) {
        showError("Categories: " + categories.size());
    }

}
