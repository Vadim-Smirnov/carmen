package net.ginteam.carmen.view.adapter;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.DimenRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Eugene on 1/6/17.
 */

public class RecyclerListVerticalItemDecorator extends RecyclerView.ItemDecoration {

    private int mItemSpacing;

    public RecyclerListVerticalItemDecorator(Context context, @DimenRes int itemSpacing) {
        mItemSpacing = context.getResources().getDimensionPixelOffset(itemSpacing);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int currentItemPosition = parent.getChildAdapterPosition(view);
        if (currentItemPosition == 0) {
            outRect.top = mItemSpacing;
        }
        outRect.left = mItemSpacing;
        outRect.right = mItemSpacing;
        outRect.bottom = mItemSpacing;
    }

}
