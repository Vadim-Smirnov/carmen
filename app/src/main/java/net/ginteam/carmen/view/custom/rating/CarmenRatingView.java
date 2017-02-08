package net.ginteam.carmen.view.custom.rating;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatRatingBar;
import android.util.AttributeSet;

import net.ginteam.carmen.R;
import net.ginteam.carmen.utils.DisplayUtils;
import net.ginteam.carmen.utils.ScalingUtils;

/**
 * Created by eugene_shcherbinock on 2/3/17.
 */

public class CarmenRatingView extends AppCompatRatingBar {

    private int mWidth;
    private int mHeight;

    private float mViewSpacing;

    private boolean isDrawablesSet;

    // circles
    private int mRadius;
    private Paint mEmptyCircle;
    private Paint mFillCircle;
    protected int mCirclesColor;

    // drawable
    private Paint mDrawablePaint;
    private Bitmap mScaledEmptyIndicatorBitmap;
    private Bitmap mEmptyIndicatorBitmap;
    private Bitmap mScaledFillIndicatorBitmap;
    private Bitmap mFillIndicatorBitmap;

    public CarmenRatingView(Context context) {
        super(context);
        initializeView(context, null);
    }

    public CarmenRatingView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        initializeView(context, attrs);
    }

    public CarmenRatingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeView(context, attrs);
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int desiredWidth = (getNumStars() * getIndicatorWidth()) + (getNumStars() * (int) mViewSpacing);
        int desiredHeight = getIndicatorHeight();

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            mWidth = Math.min(desiredWidth, widthSize);
        } else {
            mWidth = desiredWidth;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            mHeight = Math.min(desiredHeight, heightSize);
        } else {
            mHeight = desiredHeight;
        }

        if (!isDrawablesSet) {
            mRadius = Math.min(mWidth, mHeight) / 2;
            mEmptyCircle.setStrokeWidth(mHeight / 10);
        } else {
            mScaledEmptyIndicatorBitmap = ScalingUtils.createScaledBitmap(mEmptyIndicatorBitmap,
                    mFillIndicatorBitmap.getWidth(), mHeight, ScalingUtils.ScalingLogic.FIT);

            mScaledFillIndicatorBitmap = ScalingUtils.createScaledBitmap(mFillIndicatorBitmap,
                    mFillIndicatorBitmap.getWidth(), mHeight, ScalingUtils.ScalingLogic.FIT);

            mWidth = (getNumStars() * getIndicatorWidth()) + (getNumStars() * (int) mViewSpacing);
        }

        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        float currentPosition = getFirstPosition();
        for (int i = 0; i < getNumStars(); i++) {
            if (getRating() - 1 >= i) {
                drawFillIndicator(canvas, currentPosition);
            } else {
                drawEmptyIndicator(canvas, currentPosition);
            }
            currentPosition += getIndicatorWidth();
            if (i < (getNumStars() - 1)) {
                currentPosition += mViewSpacing;
            }
        }
    }

    private float getFirstPosition() {
        if (!isDrawablesSet) {
            return mRadius;
        }
        return 0;
    }

    private void drawFillIndicator(Canvas canvas, float position) {
        if (!isDrawablesSet) {
            canvas.drawCircle(position, getHeight() / 2, mRadius, mFillCircle);
            return;
        }
        canvas.drawBitmap(mScaledFillIndicatorBitmap, position, 0, mDrawablePaint);
    }

    private void drawEmptyIndicator(Canvas canvas, float position) {
        if (!isDrawablesSet) {
            canvas.drawCircle(position, getHeight() / 2, mRadius, mEmptyCircle);
            return;
        }
        canvas.drawBitmap(mScaledEmptyIndicatorBitmap, position, 0, mDrawablePaint);
    }

    private int getIndicatorWidth() {
        if (!isDrawablesSet) {
            return mRadius * 2;
        }
        return mScaledFillIndicatorBitmap != null ? mScaledFillIndicatorBitmap.getWidth() : mFillIndicatorBitmap.getWidth();
    }

    private int getIndicatorHeight() {
        if (!isDrawablesSet) {
            return mRadius * 2;
        }
        return mScaledFillIndicatorBitmap != null ? mScaledFillIndicatorBitmap.getHeight() : mFillIndicatorBitmap.getHeight();
    }

    private void initializeView(Context context, AttributeSet attrs) {
        int emptyDrawableId, fillDrawableId;

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.CarmenRatingView, 0, 0);
        try {
            mViewSpacing = attributes.getDimension(R.styleable.CarmenRatingView_crv_indicatorSpacing, 0f);
            mCirclesColor = attributes.getColor(R.styleable.CarmenRatingView_crv_indicatorColor, Color.parseColor("#3EC7C2"));
            emptyDrawableId = attributes.getResourceId(R.styleable.CarmenRatingView_crv_emptyDrawable, 0);
            fillDrawableId = attributes.getResourceId(R.styleable.CarmenRatingView_crv_fillDrawable, 0);
        } finally {
            attributes.recycle();
        }

        if (emptyDrawableId == 0 || fillDrawableId == 0) {
            isDrawablesSet = false;
            updateCirclesDependencies();
            return;
        }
        isDrawablesSet = true;
        updateDrawableDependencies(emptyDrawableId, fillDrawableId);
    }

    private void updateCirclesDependencies() {
        mRadius = DisplayUtils.dpToPx(5);

        mFillCircle = new Paint();
        mFillCircle.setStyle(Paint.Style.FILL);
        mFillCircle.setColor(mCirclesColor);
        mFillCircle.setAntiAlias(true);

        mEmptyCircle = new Paint();
        mEmptyCircle.setStyle(Paint.Style.STROKE);
        mEmptyCircle.setColor(mCirclesColor);
        mEmptyCircle.setAntiAlias(true);
    }

    private void updateDrawableDependencies(int emptyDrawable, int fillDrawable) {
        mDrawablePaint = new Paint();
        mDrawablePaint.setAntiAlias(true);
        mDrawablePaint.setFilterBitmap(true);
        mDrawablePaint.setDither(true);

        mEmptyIndicatorBitmap = BitmapFactory.decodeResource(getResources(), emptyDrawable);
        mFillIndicatorBitmap = BitmapFactory.decodeResource(getResources(), fillDrawable);
    }

}
