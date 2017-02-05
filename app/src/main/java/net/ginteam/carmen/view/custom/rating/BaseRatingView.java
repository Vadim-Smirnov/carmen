package net.ginteam.carmen.view.custom.rating;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import net.ginteam.carmen.R;

/**
 * Created by eugene_shcherbinock on 2/2/17.
 */

public abstract class BaseRatingView extends View {

    protected static final int DEFAULT_VIEWS_COUNT = 5;
    protected static final int DEFAULT_VIEWS_SPACING = 0;
    protected static final int DEFAULT_RATING = 0;

    protected Context mContext;

    protected float mUsableWidth;
    protected int mUsableHeight;

    protected boolean mIsMeasureCalculated;

    protected int mViewsCount;
    protected float mViewsSpacing;
    protected int mRating;

    protected float mViewX;

    protected float mTouchX;
    protected boolean mUserInteractionEnabled;

    protected OnRatingChangeListener mRatingChangeListener;

    public BaseRatingView(Context context) {
        super(context);
        initializeRatingView(context, null);
    }

    public BaseRatingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeRatingView(context, attrs);
    }

    public BaseRatingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeRatingView(context, attrs);
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    public BaseRatingView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initializeRatingView(context, attrs);
    }

    public int getRating() {
        return mRating;
    }

    public void setRating(int rating) {
        mRating = rating;
        invalidate();
    }

    public boolean isUserInteractionEnabled() {
        return mUserInteractionEnabled;
    }

    public void setUserInteractionEnabled(boolean userInteractionEnabled) {
        mUserInteractionEnabled = userInteractionEnabled;
    }

    public OnRatingChangeListener getOnRatingChangeListener() {
        return mRatingChangeListener;
    }

    public void setOnRatingChangeListener(OnRatingChangeListener ratingChangeListener) {
        mRatingChangeListener = ratingChangeListener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int size;
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        int widthWithoutPadding = width - getPaddingLeft() - getPaddingRight();
        int heightWithoutPadding = height - getPaddingTop() - getPaddingBottom();

        if (widthWithoutPadding > heightWithoutPadding) {
            size = heightWithoutPadding;
        } else {
            size = widthWithoutPadding;
        }

        setMeasuredDimension(width + getPaddingLeft() + getPaddingRight(), height + getPaddingTop() + getPaddingBottom());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (!mIsMeasureCalculated) {
            calculateViewMeasure();
        }

        mViewX = calculateFirstX() + getPaddingLeft();
        int[] ratingArray = new int[mViewsCount];

        for (int i = 0; i < mViewsCount; i++) {
            drawRatingView(canvas, i);
            if (mUserInteractionEnabled) {
                ratingArray[i] = (mViewX < mTouchX) ? (i + 1) : 0;
            }
            mViewX += getViewWidth() + mViewsSpacing;
        }

        if (mUserInteractionEnabled && (mTouchX != -1)) {
            mRating = 0;
            for (int i = 0; i < mViewsCount && ratingArray[i] != 0; i++, mRating++) ;
            if (mRatingChangeListener != null) {
                mRatingChangeListener.onRatingChanged(this, mRating);
            }
        }
    }

    protected abstract void drawRatingView(Canvas canvas, int viewPosition);

    protected abstract float getViewWidth();

    protected abstract float calculateFirstX();

    protected void initializeRatingView(Context context, AttributeSet attributeSet) {
        mContext = context;
        mIsMeasureCalculated = false;
        mUserInteractionEnabled = false;
        mTouchX = -1;

        mRating = DEFAULT_RATING;

        TypedArray attributes = context.obtainStyledAttributes(attributeSet, R.styleable.RatingView, 0, 0);
        try {
            mViewsCount = attributes.getInteger(R.styleable.RatingView_viewsCount, DEFAULT_VIEWS_COUNT);
            mViewsSpacing = attributes.getDimension(R.styleable.RatingView_viewsSpacing, DEFAULT_VIEWS_SPACING);
            mRating = attributes.getInteger(R.styleable.RatingView_rating, DEFAULT_RATING);
        } finally {
            attributes.recycle();
        }
    }

    protected void calculateViewMeasure() {
        int leftPadding = getPaddingLeft();
        int rightPadding = getPaddingRight();
        int topPadding = getPaddingTop();
        int bottomPadding = getPaddingBottom();

        mUsableWidth = getWidth() - (leftPadding + rightPadding) - (mViewsSpacing * mViewsCount) - mViewsSpacing;
        mUsableHeight = getHeight() - (topPadding + bottomPadding);

        mIsMeasureCalculated = true;
    }

    public interface OnRatingChangeListener {

        void onRatingChanged(BaseRatingView ratingView, int rating);

    }

}
