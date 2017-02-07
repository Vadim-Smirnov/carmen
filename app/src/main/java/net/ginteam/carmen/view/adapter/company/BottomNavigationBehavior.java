package net.ginteam.carmen.view.adapter.company;

import android.content.Context;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v13.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;

/**
 * Created by Eugene on 1/16/17.
 */

public class BottomNavigationBehavior extends CoordinatorLayout.Behavior<LinearLayout> {

    public BottomNavigationBehavior(Context context, AttributeSet attributeSet) {
        super();
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, LinearLayout child,
                                       View directTargetChild, View target, int nestedScrollAxes) {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, LinearLayout child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);

        if (dyConsumed > 0) {
            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
            int navigationViewMargin = layoutParams.bottomMargin;
            child
                    .animate()
                    .translationY(child.getHeight() + navigationViewMargin)
                    .setInterpolator(new LinearInterpolator())
                    .start();
        } else if (dyConsumed < 0) {
            child
                    .animate()
                    .translationY(0)
                    .setInterpolator(new LinearInterpolator())
                    .start();
        }
    }
}
