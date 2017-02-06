package net.ginteam.carmen.view.fragment.city;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.ginteam.carmen.R;
import net.ginteam.carmen.contract.city.CityContract;
import net.ginteam.carmen.model.city.CityModel;
import net.ginteam.carmen.presenter.city.CitiesPresenter;
import net.ginteam.carmen.view.adapter.city.CityItemViewHolder;
import net.ginteam.carmen.view.adapter.city.CityRecyclerListAdapter;
import net.ginteam.carmen.view.fragment.BaseFetchingFragment;

import java.util.List;

/**
 * Created by Eugene on 12/27/16.
 */

public class CityListFragment extends BaseFetchingFragment implements CityContract.View, CityItemViewHolder.OnCityItemClickListener {

    private CityContract.Presenter mPresenter;

    private RecyclerView mRecyclerViewCities;
    private CityRecyclerListAdapter mRecyclerListAdapter;

    private OnCitySelectedListener mCitySelectedListener;

    public CityListFragment() {}

    public static CityListFragment newInstance() {
        return new CityListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.DialogStyle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflateBaseFragment(R.layout.fragment_city_list, inflater, container, savedInstanceState);
        updateDependencies();

        mPresenter = new CitiesPresenter();
        mPresenter.attachView(this);
        mPresenter.fetchCities();

        return mRootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().setCancelable(false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCitySelectedListener = (OnCitySelectedListener) context;
        } catch (ClassCastException exception) {
            Log.e("CityListFragment", "Parent context does not confirm to OnCitySelectedListener!");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mPresenter.detachView();
    }

    @Override
    public void showCities(List<CityModel> cities) {
        mRecyclerListAdapter = new CityRecyclerListAdapter(getContext(), cities);
        mRecyclerListAdapter.setOnCityItemClickListener(this);
        mRecyclerViewCities.setAdapter(mRecyclerListAdapter);
    }

    @Override
    public void onCityItemClick(CityModel city) {
        if (mCitySelectedListener == null) {
            Log.e("CityListFragment", "OnCitySelected listener is null!");
//            return;
        }
        mCitySelectedListener.onCitySelected(city);
        mPresenter.saveCity(city);
    }

    private void updateDependencies() {
        mRecyclerViewCities = (RecyclerView) mRootView.findViewById(R.id.recycler_view_cities);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerViewCities.setLayoutManager(layoutManager);
    }

    public interface OnCitySelectedListener {

        void onCitySelected(CityModel city);

    }

}
