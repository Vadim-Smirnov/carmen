package net.ginteam.carmen.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;

import net.ginteam.carmen.R;
import net.ginteam.carmen.model.company.CompanyModel;

/**
 * Created by Eugene on 1/22/17.
 */

public class CompanyClusterRenderer extends DefaultClusterRenderer<CompanyModel> {

    private Context mContext;
    private IconGenerator mIconGenerator;
    private TextView mTextViewPrice;

    public CompanyClusterRenderer(Context context, GoogleMap map, ClusterManager<CompanyModel> clusterManager) {
        super(context, map, clusterManager);

        mContext = context;
        mIconGenerator = new IconGenerator(mContext);
        mIconGenerator.setBackground(null);

        View markerView = LayoutInflater.from(mContext).inflate(R.layout.layout_marker, null);
        mTextViewPrice = (TextView) markerView.findViewById(R.id.text_view_marker_price);
        mIconGenerator.setContentView(markerView);
    }

    @Override
    protected void onBeforeClusterItemRendered(CompanyModel item, MarkerOptions markerOptions) {
        mTextViewPrice.setText(String.valueOf(item.getPrice()));
        Bitmap bitmap = mIconGenerator.makeIcon();
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(bitmap));
    }

    @Override
    protected void onBeforeClusterRendered(Cluster<CompanyModel> cluster, MarkerOptions markerOptions) {
        super.onBeforeClusterRendered(cluster, markerOptions);
    }

}
