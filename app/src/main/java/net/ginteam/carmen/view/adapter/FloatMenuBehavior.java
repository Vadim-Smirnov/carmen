package net.ginteam.carmen.view.adapter;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v13.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import net.ginteam.carmenfloatbutton.FloatingActionMenu;

public class FloatMenuBehavior extends CoordinatorLayout.Behavior<FloatingActionMenu> {

    public FloatMenuBehavior(Context context, AttributeSet attributeSet) {
        super();
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionMenu child, View directTargetChild, View target, int nestedScrollAxes) {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionMenu child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
        child.close(true);
        if (dyConsumed > 0) {
            child
                    .animate()
                    .alpha(0)
                    .setDuration(300)
                    .setInterpolator(new LinearInterpolator())
                    .start();
        } else if (dyConsumed < 0) {
            child
                    .animate()
                    .alpha(1)
                    .setDuration(300)
                    .setInterpolator(new LinearInterpolator())
                    .start();
        }
    }

}