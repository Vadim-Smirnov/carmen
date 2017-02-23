package net.ginteam.carmen.view.adapter.category;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.DimenRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Eugene on 12/25/16.
 */

public class CategoryRecyclerListItemDecorator extends RecyclerView.ItemDecoration {

    private int mItemCounter;
    private int mItemSpacing;

    public CategoryRecyclerListItemDecorator(Context context, @DimenRes int itemSpacing) {
        mItemSpacing = context.getResources().getDimensionPixelOffset(itemSpacing);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        if (mItemCounter++ % 2 != 0) {
            outRect.left = mItemSpacing;
        }
        outRect.bottom = mItemSpacing;
    }
}
