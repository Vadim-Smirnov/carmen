package net.ginteam.carmen.view.adapter.filter;

import android.view.View;
import android.widget.TextView;

import net.ginteam.carmen.R;

/**
 * Created by Eugene on 1/13/17.
 */

public class FilterItemViewHolder {

    private View mView;
    private TextView mTextViewOption;

    public FilterItemViewHolder(View view) {
        mView = view;
        mTextViewOption = (TextView) mView.findViewById(R.id.text_view_filter_option);
    }

    public TextView getTextViewOption() {
        return mTextViewOption;
    }

}
