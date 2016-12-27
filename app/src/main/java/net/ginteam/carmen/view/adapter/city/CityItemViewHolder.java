package net.ginteam.carmen.view.adapter.city;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import net.ginteam.carmen.R;
import net.ginteam.carmen.model.city.CityModel;

/**
 * Created by Eugene on 12/27/16.
 */

public class CityItemViewHolder extends RecyclerView.ViewHolder {

    private View mView;

    private TextView mTextViewName;

    public CityItemViewHolder(View view) {
        super(view);

        mView = view;
        mTextViewName = (TextView) view.findViewById(R.id.text_view_city_name);
    }

    public TextView getTextViewName() {
        return mTextViewName;
    }

    public void setOnCityClickListener(final CityModel forCity, final OnCityItemClickListener listener) {
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onCityItemClick(forCity);
            }
        });
    }

    public interface OnCityItemClickListener {

        void onCityItemClick(CityModel city);

    }

}
