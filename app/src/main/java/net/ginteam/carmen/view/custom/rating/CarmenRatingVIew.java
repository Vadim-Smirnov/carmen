package net.ginteam.carmen.view.custom.rating;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatRatingBar;
import android.util.AttributeSet;

import net.ginteam.carmen.R;

/**
 * Created by eugene_shcherbinock on 2/3/17.
 */

public class CarmenRatingVIew extends AppCompatRatingBar {

    private int mHeight;

    private float mViewSpacing;

    private int mEmptyIndicatorId;
    private int mFillIndicatorId;

    private Paint mPaint;
    private Bitmap mEmptyIndicatorBitmap;
    private Bitmap mFillIndicatorBitmap;

    public CarmenRatingVIew(Context context) {
        super(context);
        initializeView(context, null);
    }

    public CarmenRatingVIew(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        initializeView(context, attrs);
    }

    public CarmenRatingVIew(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeView(context, attrs);
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // TODO: 2/3/17 Try to fix measuring image by setup height
        setMeasuredDimension(
                getPaddingLeft() + getPaddingRight() + (mEmptyIndicatorBitmap.getWidth() + (int) mViewSpacing) * getNumStars(),
                getPaddingBottom() + getPaddingTop() + mEmptyIndicatorBitmap.getHeight());
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        float currentPosition = 0;
        Bitmap drawingViewBitmap;
        for (int i = 0; i < getNumStars(); i++) {
            if (getRating() - 1 >= i) {
                drawingViewBitmap = mFillIndicatorBitmap;
            } else {
                drawingViewBitmap = mEmptyIndicatorBitmap;
            }
            canvas.drawBitmap(drawingViewBitmap, currentPosition, 0, mPaint);
            currentPosition += drawingViewBitmap.getWidth();
            if (i < (getNumStars() - 1)) {
                currentPosition += mViewSpacing;
            }
        }
    }

    private void initializeView(Context context, AttributeSet attrs) {
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.CarmenRatingView, 0, 0);
        try {
            mViewSpacing = attributes.getDimension(R.styleable.CarmenRatingView_indicatorSpacing, 0);
            mEmptyIndicatorId = attributes.getResourceId(R.styleable.CarmenRatingView_emptyIndicator, R.drawable.ic_small_empty_rating);
            mFillIndicatorId = attributes.getResourceId(R.styleable.CarmenRatingView_fillIndicator, R.drawable.ic_small_fill_rating);
        } finally {
            attributes.recycle();
        }
        updateDependencies();
    }

    private void updateDependencies() {
        mPaint = new Paint();
        mEmptyIndicatorBitmap = BitmapFactory.decodeResource(getResources(), mEmptyIndicatorId);
        mFillIndicatorBitmap = BitmapFactory.decodeResource(getResources(), mFillIndicatorId);
    }

}
