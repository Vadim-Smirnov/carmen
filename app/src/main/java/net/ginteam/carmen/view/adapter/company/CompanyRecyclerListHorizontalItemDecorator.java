package net.ginteam.carmen.view.adapter.company;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.DimenRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Eugene on 12/29/16.
 */

public class CompanyRecyclerListHorizontalItemDecorator extends RecyclerView.ItemDecoration {

    private int mItemSpacing;

    public CompanyRecyclerListHorizontalItemDecorator(Context context, @DimenRes int itemSpacing) {
        mItemSpacing = context.getResources().getDimensionPixelOffset(itemSpacing);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int currentItemPosition = parent.getChildAdapterPosition(view);
        if (currentItemPosition == state.getItemCount() - 1) {
            outRect.right = mItemSpacing;
        }
        outRect.left = mItemSpacing;
    }
}
