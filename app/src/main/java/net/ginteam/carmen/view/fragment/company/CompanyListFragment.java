package net.ginteam.carmen.view.fragment.company;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.ginteam.carmen.R;
import net.ginteam.carmen.contract.company.CompaniesContract;
import net.ginteam.carmen.model.company.CompanyModel;
import net.ginteam.carmen.presenter.company.CompaniesPresenter;
import net.ginteam.carmen.view.adapter.company.CompanyItemViewHolder;
import net.ginteam.carmen.view.adapter.company.CompanyRecyclerListAdapter;
import net.ginteam.carmen.view.adapter.company.CompanyRecyclerListItemDecorator;
import net.ginteam.carmen.view.adapter.company.CompanyRecyclerManagerFactory;
import net.ginteam.carmen.view.fragment.BaseFetchingFragment;

import java.util.List;

/**
 * Created by Eugene on 12/27/16.
 */

public class CompanyListFragment extends BaseFetchingFragment implements CompaniesContract.View, CompanyItemViewHolder.OnCompanyItemClickListener {

    public enum COMPANY_LIST_TYPE {
        RECENTLY_WATCHED,
        POPULAR,
        BY_CATEGORY
    }

    private static final String TYPE_ARGUMENT = "type";
    private static final String CATEGORY_ARGUMENT = "category";

    private CompaniesContract.Presenter mPresenter;

    private COMPANY_LIST_TYPE mListType;
    private int mCategoryId;

    private RecyclerView mRecyclerViewCompanies;
    private CompanyRecyclerListAdapter mRecyclerListAdapter;

    private OnCompanySelectedListener mCompanySelectedListener;

    public CompanyListFragment() {
    }

    public static CompanyListFragment newInstance(COMPANY_LIST_TYPE type, @Nullable int categoryId) {
        CompanyListFragment fragment = new CompanyListFragment();

        Bundle arguments = new Bundle();
        arguments.putSerializable(TYPE_ARGUMENT, type);
        arguments.putInt(CATEGORY_ARGUMENT, categoryId);
        fragment.setArguments(arguments);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = getArguments();
        mListType = (COMPANY_LIST_TYPE) arguments.getSerializable(TYPE_ARGUMENT);
        mCategoryId = arguments.getInt(CATEGORY_ARGUMENT, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflateBaseFragment(R.layout.fragment_company_list, inflater, container, savedInstanceState);
        updateDependencies();

        mPresenter = new CompaniesPresenter();
        mPresenter.attachView(this);
        fetchCompanies();

        return mRootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCompanySelectedListener = (OnCompanySelectedListener) context;
        } catch (ClassCastException exception) {
            Log.e("CompanyListFragment", "Parent context does not confirm to OnCompanySelectedListener!");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mPresenter.detachView();
    }

    @Override
    public void onCompanyItemClick(CompanyModel company) {
        if (mCompanySelectedListener == null) {
            Log.e("CompanyListFragment", "onCompanySelected listener is null!");
            return;
        }
        mCompanySelectedListener.onCompanySelected(company);
    }

    @Override
    public void showCompanies(List<CompanyModel> companies) {
        mRecyclerListAdapter = new CompanyRecyclerListAdapter(getContext(), companies, mListType);
        mRecyclerListAdapter.setOnCompanyItemClickListener(this);
        mRecyclerViewCompanies.setAdapter(mRecyclerListAdapter);
    }

    private void updateDependencies() {
        mRecyclerViewCompanies = (RecyclerView) mRootView.findViewById(R.id.recycler_view_companies);
        mRecyclerViewCompanies.addItemDecoration(new CompanyRecyclerListItemDecorator(getContext(), R.dimen.company_item_spacing));
        mRecyclerViewCompanies.setLayoutManager(CompanyRecyclerManagerFactory.createManagerForListType(mListType));
    }

    private void fetchCompanies() {
        switch (mListType) {
            case RECENTLY_WATCHED:
                mPresenter.fetchRecentlyWatchedCompanies();
                break;
            case POPULAR:
                mPresenter.fetchPopularCompanies();
                break;
            default:
                mPresenter.fetchCompaniesForCategory(mCategoryId);
        }
    }

    public interface OnCompanySelectedListener {

        void onCompanySelected(CompanyModel company);

    }

}
