package net.ginteam.carmen.view.fragment.company;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import net.ginteam.carmen.R;
import net.ginteam.carmen.contract.company.CompaniesContract;
import net.ginteam.carmen.manager.PreferencesManager;
import net.ginteam.carmen.model.Pagination;
import net.ginteam.carmen.model.category.CategoryModel;
import net.ginteam.carmen.model.company.CompanyModel;
import net.ginteam.carmen.presenter.company.CompaniesPresenter;
import net.ginteam.carmen.view.adapter.company.CompanyItemViewHolder;
import net.ginteam.carmen.view.adapter.company.CompanyRecyclerListAdapter;
import net.ginteam.carmen.view.adapter.company.CompanyRecyclerManagerFactory;
import net.ginteam.carmen.view.adapter.company.PaginationScrollListener;
import net.ginteam.carmen.view.fragment.BaseFetchingFragment;

import java.util.List;
import java.util.Locale;

/**
 * Created by Eugene on 12/27/16.
 */

public class CompanyListFragment extends BaseFetchingFragment implements CompaniesContract.View,
        CompanyItemViewHolder.OnCompanyItemClickListener, CompanyItemViewHolder.OnAddToFavoritesClickListener,
        View.OnClickListener {

    public enum COMPANY_LIST_TYPE {
        HORIZONTAL,
        VERTICAL
    }

    public enum FETCH_COMPANY_TYPE {
        POPULAR,
        RECENTLY_WATCHED,
        FAVORITE,
        FOR_CATEGORY
    }

    private static final String TYPE_ARGUMENT = "type";
    private static final String FETCH_TYPE_ARGUMENT = "fetch_type";
    private static final String CATEGORY_ARGUMENT = "category";

    private CompaniesContract.Presenter mPresenter;

    private COMPANY_LIST_TYPE mListType;
    private FETCH_COMPANY_TYPE mFetchType;

    private CategoryModel mSelectedCategory;
    private String mSearchFilter;
    private String mSortField;
    private String mSortType;
    private boolean mIsLoading;
    private int mCurrentPaginationPage;

    private TextView mTextViewCompanyListTitle;
    private RecyclerView mRecyclerViewCompanies;
    private LinearLayoutManager mLayoutManager;
    private CompanyRecyclerListAdapter mRecyclerListAdapter;

    private ImageButton mFloatingActionButton;

    private OnSelectedItemsListener mSelectedItemsListener;
    private OnCompanySelectedListener mCompanySelectedListener;

    private SearchView mSearchView;

    private int selectedCompanyPosition;

    public CompanyListFragment() {
    }

    public static CompanyListFragment newInstance(COMPANY_LIST_TYPE type, FETCH_COMPANY_TYPE fetchType, @Nullable CategoryModel categoryModel) {
        CompanyListFragment fragment = new CompanyListFragment();

        Bundle arguments = new Bundle();
        arguments.putSerializable(TYPE_ARGUMENT, type);
        arguments.putSerializable(FETCH_TYPE_ARGUMENT, fetchType);
        arguments.putSerializable(CATEGORY_ARGUMENT, categoryModel);
        fragment.setArguments(arguments);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        mListType = (COMPANY_LIST_TYPE) arguments.getSerializable(TYPE_ARGUMENT);
        mFetchType = (FETCH_COMPANY_TYPE) arguments.getSerializable(FETCH_TYPE_ARGUMENT);
        mSelectedCategory = (CategoryModel) arguments.getSerializable(CATEGORY_ARGUMENT);

        setHasOptionsMenu(mFetchType == FETCH_COMPANY_TYPE.FOR_CATEGORY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("CompanyList", "onCreateView()");

        mRootView = inflateBaseFragment(R.layout.fragment_company_list, inflater, container, savedInstanceState);

        updateDependencies();

        mPresenter = new CompaniesPresenter();
        mPresenter.attachView(this);

        return mRootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.company_list_menu, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem searchViewMenuItem = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) searchViewMenuItem.getActionView();
        int searchImgId = android.support.v7.appcompat.R.id.search_button;
        ImageView v = (ImageView) mSearchView.findViewById(searchImgId);
        v.setImageResource(R.drawable.ic_search);
    }

    @Override
    public void onStart() {
        super.onStart();

        updateTitleDependencies();
        fetchCompanies();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCompanySelectedListener = (OnCompanySelectedListener) context;
        } catch (ClassCastException exception) {
            Log.e("CompanyListFragment", "Parent context does not confirm to OnCompanySelectedListener!");
        }

        try {
            mSelectedItemsListener = (OnSelectedItemsListener) context;
        } catch (ClassCastException exception) {
            Log.e("CompanyListFragment", "Parent context does not confirm to OnSelectedItemsListener!");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bottom_nav_item_categories:
                mSelectedItemsListener.onShowCategoriesDialog();
                break;
            case R.id.bottom_nav_item_filters:
                mSelectedItemsListener.onShowFiltersActivity();
                break;
            case R.id.bottom_nav_item_sort:
                mSelectedItemsListener.onShowSortDialog();
                break;
            default:
                mSelectedItemsListener.onShowMap(mSelectedCategory.getId());
                break;
        }

    }

    public void setSearchFilter(String filter) {
        mSearchFilter = filter;
        mCurrentPaginationPage = 1;
        fetchCompanies();
    }

    public void setSortQuery(String sortField, String sortType) {
        mSortField = sortField;
        mSortType = sortType;
        mCurrentPaginationPage = 1;
        fetchCompanies();
    }

    @Override
    public void onCompanyItemClick(CompanyModel company, int position) {
        if (mCompanySelectedListener == null) {
            Log.e("CompanyListFragment", "onCompanySelected listener is null!");
            return;
        }
        mCompanySelectedListener.onCompanySelected(company);
        selectedCompanyPosition = position;
        mPresenter.selectCompany(company);
    }

    @Override
    public void onAddToFavoritesClick(CompanyModel company) {
        mPresenter.addToFavoriteClick(company);
    }

    @Override
    public void showLoading(boolean isLoading) {
        super.showLoading(isLoading);

        if (mRecyclerViewCompanies != null) {
            mRecyclerViewCompanies.setVisibility(isLoading ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void showCompanies(List<CompanyModel> companies, final Pagination paginationDetails) {
        if (mTextViewCompanyListTitle != null) {
            mTextViewCompanyListTitle.setVisibility(View.VISIBLE);
        }

        mRecyclerListAdapter = new CompanyRecyclerListAdapter(getContext(), companies, mListType);
        mRecyclerListAdapter.setOnCompanyItemClickListener(this);
        mRecyclerListAdapter.setOnAddToFavoritesClickListener(this);
        mRecyclerViewCompanies.setAdapter(mRecyclerListAdapter);

        if (paginationDetails != null) {
            mRecyclerViewCompanies.setOnScrollListener(initializePagination(paginationDetails));
        }
    }

    @Override
    public void showMoreCompanies(List<CompanyModel> companies) {
        mIsLoading = false;
        mRecyclerListAdapter.hideLoading();
        mRecyclerListAdapter.addCompanies(companies);
    }

    @Override
    public void addToFavorites() {
        mRecyclerListAdapter.notifyDataSetChanged();
    }

    @Override
    public void removeFromFavorites(CompanyModel companyModel) {
        if (mFetchType == FETCH_COMPANY_TYPE.FAVORITE) {
            mRecyclerListAdapter.removeItem(companyModel);
        }
        mRecyclerListAdapter.notifyDataSetChanged();
    }

    private void updateDependencies() {
        mCurrentPaginationPage = 1;

        mRootView.findViewById(R.id.bottom_nav_item_categories).setOnClickListener(this);
        mRootView.findViewById(R.id.bottom_nav_item_filters).setOnClickListener(this);
        mRootView.findViewById(R.id.bottom_nav_item_sort).setOnClickListener(this);

        if (mFetchType == FETCH_COMPANY_TYPE.FOR_CATEGORY) {
            mFloatingActionButton = (ImageButton) mRootView.findViewById(R.id.float_button_show_map);


            if (mSelectedItemsListener != null) {
                mFloatingActionButton.setOnClickListener(this);
            }

            mFloatingActionButton.setVisibility(View.VISIBLE);
            mRootView.findViewById(R.id.bottom_navigation_layout).setVisibility(View.VISIBLE);
        }

        mRecyclerViewCompanies = (RecyclerView) mRootView.findViewById(R.id.recycler_view_companies);
        mLayoutManager = (LinearLayoutManager) CompanyRecyclerManagerFactory.createManagerForListType(mListType);

        mRecyclerViewCompanies.addItemDecoration(CompanyRecyclerManagerFactory.createItemDecoratorForListType(mListType));
        mRecyclerViewCompanies.setLayoutManager(mLayoutManager);

        mIsLoading = false;

        mSortField = "rating";
        mSortType = "desc";
    }

    private void updateTitleDependencies() {
        if (mListType == COMPANY_LIST_TYPE.HORIZONTAL) {
            String title = null;

            switch (mFetchType) {
                case POPULAR:
                    title = String
                            .format(
                                    Locale.getDefault(),
                                    getString(R.string.popular_title),
                                    PreferencesManager.getInstance().getCity().getName());
                    break;
                case RECENTLY_WATCHED:
                    title = getString(R.string.recently_watched_title);
                    break;
            }

            if (title != null) {
                mTextViewCompanyListTitle = (TextView) mRootView.findViewById(R.id.text_view_company_list_title);
                mTextViewCompanyListTitle.setText(title);
            }
        } else {
            switch (mFetchType) {
                case RECENTLY_WATCHED:
                    setToolbarTitle(getString(R.string.recent_item_title), "");
                    break;
                case FAVORITE:
                    setToolbarTitle(getString(R.string.favorite_item_title), "");
                    break;
                case FOR_CATEGORY:
                    setToolbarTitle(mSelectedCategory.getName(), mPresenter.getUserCityName());
                    break;
            }
        }
    }

    private PaginationScrollListener initializePagination(final Pagination paginationDetails) {
        return new PaginationScrollListener(mLayoutManager) {
            @Override
            public void loadMoreItems() {
                Log.d("Pagination", "loadMoreItems()");

                mCurrentPaginationPage++;
                mIsLoading = true;
                mRecyclerListAdapter.showLoading();

                fetchCompanies();
            }

            @Override
            public boolean isLastPage() {
                Log.d("Pagination", "Page: " + mCurrentPaginationPage + " of " + paginationDetails.getTotalPages());
                return mCurrentPaginationPage == paginationDetails.getTotalPages();
            }

            @Override
            public boolean isLoading() {
                return mIsLoading;
            }

        };
    }

    private void fetchCompanies() {
        switch (mFetchType) {
            case POPULAR:
                mPresenter.fetchPopularCompanies();
                break;
            case RECENTLY_WATCHED:
                mPresenter.fetchRecentlyWatchedCompanies();
                break;
            case FAVORITE:
                mPresenter.fetchFavoriteCompanies();
                break;
            default:
                mPresenter.fetchCompaniesForCategory(mSelectedCategory.getId(), mSearchFilter, mSortField, mSortType, mCurrentPaginationPage);
        }

        if (selectedCompanyPosition != 0) {
            mRecyclerViewCompanies.scrollToPosition(selectedCompanyPosition);
        }
    }

    public interface OnCompanySelectedListener {

        void onCompanySelected(CompanyModel company);

    }

    public interface OnSelectedItemsListener {

        void onShowMap(int categoryId);

        void onShowCategoriesDialog();

        void onShowFiltersActivity();

        void onShowSortDialog();

    }

}
