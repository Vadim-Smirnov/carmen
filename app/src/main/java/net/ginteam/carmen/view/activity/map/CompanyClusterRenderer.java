package net.ginteam.carmen.view.activity.map;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;

import net.ginteam.carmen.R;
import net.ginteam.carmen.model.company.MapCompanyModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Eugene on 1/22/17.
 */

public class CompanyClusterRenderer extends DefaultClusterRenderer<MapCompanyModel> {

    private Context mContext;
    private IconGenerator mIconGenerator;
    private ClusterManager<MapCompanyModel> mClusterManager;
    private Map<MapCompanyModel, Marker> mClusterMap;

    public CompanyClusterRenderer(Context context, GoogleMap map, ClusterManager<MapCompanyModel> clusterManager) {
        super(context, map, clusterManager);

        mContext = context;
        mIconGenerator = new IconGenerator(mContext);
        mIconGenerator.setBackground(null);

        mClusterManager = clusterManager;
        mClusterMap = new HashMap<>();
    }

    @Override
    protected void onBeforeClusterItemRendered(MapCompanyModel item, MarkerOptions markerOptions) {
        View markerView;
        TextView textViewPrice;

        if (item.isSelected()) {
            markerView = LayoutInflater.from(mContext).inflate(R.layout.layout_selected_marker, null);
        } else {
            markerView = LayoutInflater.from(mContext).inflate(R.layout.layout_marker, null);
        }

        mIconGenerator.setContentView(markerView);
        textViewPrice = (TextView) markerView.findViewById(R.id.text_view_marker_price);
        textViewPrice.setText(String.valueOf(item.getPrice()));

        Bitmap bitmap = mIconGenerator.makeIcon();
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(bitmap));
    }

    @Override
    protected void onClusterItemRendered(MapCompanyModel clusterItem, Marker marker) {
        super.onClusterItemRendered(clusterItem, marker);

        mClusterMap.remove(clusterItem);
        cleanCache();
    }

    @Override
    protected void onClusterRendered(Cluster<MapCompanyModel> cluster, Marker marker) {
        super.onClusterRendered(cluster, marker);

        for (MapCompanyModel clusterItem : cluster.getItems()) {
            mClusterMap.put(clusterItem, marker);
        }
        cleanCache();
    }

    public void updateClusterItem(MapCompanyModel clusterItem) {
        Marker marker = getMarker(clusterItem);
        boolean isCluster = false;

        if (marker == null) {
            marker = mClusterMap.get(clusterItem);
            isCluster = marker != null;
        }

        if (marker != null) {
            MarkerOptions options = getMarkerOptionsFromMarker(marker);
            if (isCluster) {
                Cluster <MapCompanyModel> cluster = getCluster(marker);
                onBeforeClusterRendered(cluster, options);
            } else {
                onBeforeClusterItemRendered(clusterItem, options);
            }
            loadMarkerWithMarkerOptions(marker, options);
        }
    }

    private void cleanCache() {
        ArrayList<MapCompanyModel> deleteQueue = new ArrayList<>();
        Collection<Marker> clusterMarkers = mClusterManager.getClusterMarkerCollection().getMarkers();

        for (MapCompanyModel clusterItem : mClusterMap.keySet()) {
            if (!clusterMarkers.contains(mClusterMap.get(clusterItem))) {
                deleteQueue.add(clusterItem);
            }
        }

        for (MapCompanyModel clusterItem : deleteQueue) {
            mClusterMap.remove(clusterItem);
        }

        deleteQueue.clear();
    }

    private MarkerOptions getMarkerOptionsFromMarker(@NonNull Marker marker) {
        MarkerOptions options = new MarkerOptions();

        options.alpha(marker.getAlpha());
        options.draggable(marker.isDraggable());
        options.flat(marker.isFlat());
        options.position(marker.getPosition());
        options.rotation(marker.getRotation());
        options.title(marker.getTitle());
        options.snippet(marker.getSnippet());
        options.visible(marker.isVisible());
        options.zIndex(marker.getZIndex());

        return options;
    }

    private void loadMarkerWithMarkerOptions(@NonNull Marker marker, @NonNull MarkerOptions options) {
        marker.setAlpha(options.getAlpha());
        marker.setDraggable(options.isDraggable());
        marker.setFlat(options.isFlat());
        marker.setPosition(options.getPosition());
        marker.setRotation(options.getRotation());
        marker.setTitle(options.getTitle());
        marker.setSnippet(options.getSnippet());
        marker.setVisible(options.isVisible());
        marker.setZIndex(options.getZIndex());
        marker.setIcon(options.getIcon());
        marker.setAnchor(options.getAnchorU(), options.getAnchorV());
        marker.setInfoWindowAnchor(options.getInfoWindowAnchorU(), options.getInfoWindowAnchorV());
    }

}