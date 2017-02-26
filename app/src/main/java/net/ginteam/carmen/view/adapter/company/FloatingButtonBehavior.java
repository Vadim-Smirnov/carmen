package net.ginteam.carmen.view.adapter.company;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v13.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;

/**
 * Created by Eugene on 1/16/17.
 */

public class FloatingButtonBehavior extends CoordinatorLayout.Behavior<ImageButton> {

    public FloatingButtonBehavior(Context context, AttributeSet attributeSet) {
        super();
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, ImageButton child,
                                       View directTargetChild, View target, int nestedScrollAxes) {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, ImageButton child,
                               View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);

        if (dyConsumed > 0) {
            child
                    .animate()
                    .scaleX(0)
                    .scaleY(0)
                    .setInterpolator(new LinearInterpolator())
                    .start();
        } else if (dyConsumed < 0) {
            child
                    .animate()
                    .scaleX(1)
                    .scaleY(1)
                    .setInterpolator(new LinearInterpolator())
                    .start();
        }
    }

}
