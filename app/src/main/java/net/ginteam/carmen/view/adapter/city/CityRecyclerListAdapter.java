package net.ginteam.carmen.view.adapter.city;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.ginteam.carmen.R;
import net.ginteam.carmen.model.city.CityModel;

import java.util.List;

/**
 * Created by Eugene on 12/27/16.
 */

public class CityRecyclerListAdapter extends RecyclerView.Adapter <CityItemViewHolder> {

    private Context mContext;
    private List <CityModel> mCities;

    private CityItemViewHolder.OnCityItemClickListener mCityItemClickListener;

    public CityRecyclerListAdapter(Context context, List <CityModel> cities) {
        mContext = context;
        mCities = cities;
    }

    public void setOnCityItemClickListener(CityItemViewHolder.OnCityItemClickListener listener) {
        mCityItemClickListener = listener;
    }

    @Override
    public CityItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View cityItemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.list_item_city, parent, false);
        return new CityItemViewHolder(cityItemView);
    }

    @Override
    public void onBindViewHolder(CityItemViewHolder holder, int position) {
        CityModel currentCity = mCities.get(position);
        holder.getTextViewName().setText(currentCity.getName());

        if (mCityItemClickListener == null) {
            Log.e("CityRecyclerListAdapter", "OnCityClickListener does not set!");
            return;
        }
        holder.setOnCityClickListener(currentCity, mCityItemClickListener);
    }

    @Override
    public int getItemCount() {
        return mCities.size();
    }

}
