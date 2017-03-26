package net.ginteam.carmen.view.activity.map;

import android.view.LayoutInflater;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import net.ginteam.carmen.R;

/**
 * Created by eugene_shcherbinock on 3/24/17.
 */

public class MarkerInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private LayoutInflater mLayoutInflater;

    public MarkerInfoWindowAdapter(LayoutInflater layoutInflater) {
        mLayoutInflater = layoutInflater;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return mLayoutInflater.inflate(R.layout.marker_info_window_layout, null);
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
