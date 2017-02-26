//package net.ginteam.carmen.presenter.company;
//
//import android.support.v4.content.ContextCompat;
//import android.util.Log;
//import android.view.Menu;
//import android.view.View;
//
//import net.ginteam.carmen.R;
//import net.ginteam.carmen.contract.company.CompanyDetailContract;
//import net.ginteam.carmen.kotlin.provider.AuthenticationProvider;
//import net.ginteam.carmen.model.Rating;
//import net.ginteam.carmen.model.company.Comfort;
//import net.ginteam.carmen.model.company.CompanyModel;
//import net.ginteam.carmen.model.filter.CreateRating;
//import net.ginteam.carmen.provider.ModelCallback;
//import net.ginteam.carmen.provider.company.CompaniesProvider;
//import net.ginteam.carmen.provider.company.FavoritesProvider;
//import net.ginteam.carmen.provider.company.RatingProvider;
//import net.ginteam.carmen.view.fragment.BaseFetchingFragment;
//import net.ginteam.carmen.view.fragment.company.AdditionalServicesFragment;
//import net.ginteam.carmen.view.fragment.company.CompanyListFragment;
//import net.ginteam.carmen.view.fragment.company.ReviewsFragment;
//import net.ginteam.carmen.view.fragment.company.ServiceCategoryListFragment;
//
//import java.util.List;
//
///**
// * Created by vadik on 16.01.17.
// */
//
//public class CompanyDetailPresenter implements CompanyDetailContract.Presenter {
//
//    private CompanyDetailContract.View mView;
//
//    public CompanyDetailPresenter() {
//    }
//
//    @Override
//    public void fetchCompanyDetail(int companyId) {
//        mView.showLoading(true);
//
//        CompaniesProvider
//                .getInstance()
//                .fetchCompanyDetail(companyId, new ModelCallback<CompanyModel>() {
//                    @Override
//                    public void onSuccess(CompanyModel resultModel) {
//                        mView.showLoading(false);
//                        mView.showCompanyDetail(resultModel);
//                        BaseFetchingFragment popularCompaniesFragment = CompanyListFragment.newInstance(
//                                CompanyListFragment.COMPANY_LIST_TYPE.HORIZONTAL,
//                                CompanyListFragment.FETCH_COMPANY_TYPE.POPULAR,
//                                null
//                        );
//                        mView.showFragment(R.id.popular_companies_fragment_container, popularCompaniesFragment);
//                        Log.d("DETAIL_PRESENTER", "Fetch detail success" + resultModel.getName());
//                        List<Comfort> comforts = resultModel.getComforts();
//                        if (!comforts.isEmpty()) {
//                            mView.showFragment(R.id.additional_services_fragment_container,
//                                    AdditionalServicesFragment.newInstance(comforts));
//                        }
//                        List<Rating> ratings = resultModel.getRatings();
//                        if (!ratings.isEmpty()) {
//                            mView.showFragment(R.id.reviews_fragment_container,
//                                    ReviewsFragment.newInstance(ratings));
//                        }
//                        mView.showFragment(R.id.service_category_fragment_container,
//                                ServiceCategoryListFragment.newInstance(resultModel.getCategory()));
//                    }
//
//                    @Override
//                    public void onFailure(String message) {
//                        mView.showLoading(false);
//                        mView.showError(message);
//                        Log.e("DETAIL_PRESENTER", "Fetch detail error: " + message);
//                    }
//                });
//    }
//
//    @Override
//    public void onClick(View selectedView) {
//        switch (selectedView.getId()) {
//            case R.id.button_open_navigate:
//                mView.openNavigator();
//                break;
//            case R.id.button_show_on_map:
//                mView.showMap();
//                break;
//            case R.id.action_button_call:
//                mView.call();
//                break;
//        }
//    }
//
//    @Override
//    public void addToFavoriteClick(CompanyModel companyModel, Menu menu) {
//        net.ginteam.carmen.kotlin.provider.AuthProvider authProvider = AuthenticationProvider.INSTANCE;
//        if (authProvider.getCurrentCachedUser() != null) {
//            if (companyModel.isFavorite()) {
//                removeFromFavorite(companyModel);
//                companyModel.setFavorite(false);
//            } else {
//                addToFavorite(companyModel);
//                companyModel.setFavorite(true);
//            }
//            menu.getItem(0).setIcon(ContextCompat.getDrawable(mView.getContext(),
//                    companyModel.isFavorite() ? R.drawable.ic_company_favorite_enable :
//                            R.drawable.ic_company_favorite_disable));
//            return;
//        }
//        mView.showError(mView.getContext().getResources().getString(R.string.access_denied_message));
//    }
//
//    @Override
//    public void addToFavorite(CompanyModel companyModel) {
//        FavoritesProvider
//                .getInstance()
//                .addToFavorites(companyModel, new ModelCallback<String>() {
//                    @Override
//                    public void onSuccess(String resultModel) {
//                        Log.d("DETAIL_PRESENTER", "Add to favorite success");
//                    }
//
//                    @Override
//                    public void onFailure(String message) {
//                        Log.e("DETAIL_PRESENTER", "Add to favorite error: " + message);
//                    }
//                });
//    }
//
//    @Override
//    public void removeFromFavorite(CompanyModel companyModel) {
//        FavoritesProvider
//                .getInstance()
//                .removeFromFavorites(companyModel, new ModelCallback<String>() {
//                    @Override
//                    public void onSuccess(String resultModel) {
//                        Log.d("DETAIL_PRESENTER", "Remove from favorite success");
//                    }
//
//                    @Override
//                    public void onFailure(String message) {
//                        Log.e("DETAIL_PRESENTER", "Remove from favorite error: " + message);
//                    }
//                });
//    }
//
//    @Override
//    public void createRating(float rating, int companyId) {
//        mView.showLoading(true);
//
//        CreateRating createRating = new CreateRating();
//        createRating.setCompanyId(companyId);
//        createRating.setRating(rating);
//        RatingProvider.getInstance().sendRating(createRating, new ModelCallback<Rating>() {
//            @Override
//            public void onSuccess(Rating resultModel) {
//                mView.showVoteObjectScreen(resultModel);
//            }
//
//            @Override
//            public void onFailure(String message) {
//                mView.showLoading(false);
//                mView.showError(message);
//            }
//        });
//
//    }
//
//    @Override
//    public void attachView(CompanyDetailContract.View view) {
//        mView = view;
//    }
//
//    @Override
//    public void detachView() {
//        mView = null;
//    }
//}
