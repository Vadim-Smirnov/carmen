package net.ginteam.carmen.view.custom.rating;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;

import net.ginteam.carmen.R;

/**
 * Created by eugene_shcherbinock on 2/2/17.
 */

public class CircleRatingView extends BaseRatingView {

    protected static final int DEFAULT_CIRCLES_COLOR = Color.parseColor("#3EC7C2");

    protected int mCirclesColor;

    public CircleRatingView(Context context) {
        super(context);
    }

    public CircleRatingView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleRatingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    public CircleRatingView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    protected float getCircleRadius() {
        return (Math.min(mUsableWidth, mUsableHeight) / 2); // mViewsCount
    }

    protected float getCircleY() {
        return getPaddingTop() + (getMeasuredHeight() / 2) - getPaddingBottom();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    protected void drawRatingView(Canvas canvas, int viewPosition) {
        canvas.drawCircle(
                mViewX,
                getCircleY(),
                getCircleRadius(),
                (viewPosition < mRating) || (mUserInteractionEnabled && mViewX <= mTouchX) ?
                        getFillViewPaint() : getEmptyViewPaint()
        );
    }

    @Override
    protected float calculateFirstX() {
        return getCircleRadius() + mViewsSpacing;
    }

    @Override
    protected float getViewWidth() {
        return getCircleRadius() * 2;
    }

    protected Paint getFillViewPaint() {
        Paint fillCirclePaint = new Paint();
        fillCirclePaint.setStyle(Paint.Style.FILL);
        fillCirclePaint.setColor(mCirclesColor);
        fillCirclePaint.setAntiAlias(true);

        return fillCirclePaint;
    }

    protected Paint getEmptyViewPaint() {
        Paint emptyCirclePaint = new Paint();
        emptyCirclePaint.setStyle(Paint.Style.STROKE);
        emptyCirclePaint.setColor(mCirclesColor);
        emptyCirclePaint.setStrokeWidth(getCircleRadius() / 10);
        emptyCirclePaint.setAntiAlias(true);

        return emptyCirclePaint;
    }

    @Override
    protected void initializeRatingView(Context context, AttributeSet attributeSet) {
        TypedArray attributes = context.obtainStyledAttributes(attributeSet, R.styleable.RatingView, 0, 0);
        try {
            mCirclesColor = attributes.getColor(R.styleable.RatingView_circlesColor, DEFAULT_CIRCLES_COLOR);
        } finally {
            attributes.recycle();
        }
        super.initializeRatingView(context, attributeSet);
    }

}