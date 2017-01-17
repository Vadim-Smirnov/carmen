package net.ginteam.carmen.view.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import net.ginteam.carmen.R;

/**
 * Created by vadik on 05.01.17.
 */

public class CustomEditText extends LinearLayout {

    private static final int IMAGE_FILLED_TEXT = R.drawable.ic_checkbox_enable;
    private static final int IMAGE_CLEAR_TEXT = R.drawable.ic_clear;

    private Context mContext;
    private ImageView mImageViewFilledEditText;
    private ImageView mImageViewClearEditText;
    private TextInputLayout mTextInputLayoutFilter;
    private EditText mEditTextFilter;

    private String mFilterText;
    private String mFilterHint;
    private int mImageFilledText;
    private int mImageClearText;
    private boolean mImageFilledTextVisibility;
    private boolean mImageClearTextVisibility;

    public CustomEditText(Context context) {
        super(context);
        initializeCustomEditText(context, null);
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeCustomEditText(context, attrs);
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeCustomEditText(context, attrs);
    }

    public int getImageClearText() {
        return mImageClearText;
    }

    public void setImageClearText(int imageClearText) {
        mImageClearText = imageClearText;
    }

    public int getImageFilledText() {
        return mImageFilledText;
    }

    public void setImageFilledText(int imageFilledText) {
        mImageFilledText = imageFilledText;
    }

    public String getFilterHint() {
        return mFilterHint;
    }

    public void setFilterHint(String filterHint) {
        mFilterHint = filterHint;
    }

    public String getFilterText() {
        return mFilterText;
    }

    public void setFilterText(String filterText) {
        mFilterText = filterText;
    }

    public boolean isImageFilledTextVisibility() {
        return mImageFilledTextVisibility;
    }

    public void setImageFilledTextVisibility(boolean imageFilledTextVisibility) {
        mImageFilledTextVisibility = imageFilledTextVisibility;
    }

    public boolean isImageClearTextVisibility() {
        return mImageClearTextVisibility;
    }

    public void setImageClearTextVisibility(boolean imageClearTextVisibility) {
        mImageClearTextVisibility = imageClearTextVisibility;
    }

    private void initializeCustomEditText(Context context, AttributeSet attrs) {
        mContext = context;

        mImageFilledText = IMAGE_FILLED_TEXT;
        mImageClearText = IMAGE_CLEAR_TEXT;

        TypedArray attributes = context.obtainStyledAttributes(attrs,
                R.styleable.CustomEditText, 0, 0);
        try {
            mFilterText = attributes.getString(R.styleable.CustomEditText_filterText);
            mFilterHint = attributes.getString(R.styleable.CustomEditText_filterHint);
            mImageFilledText = attributes.getResourceId(R.styleable.CustomEditText_imageFilledText, 0);
            mImageClearText = attributes.getResourceId(R.styleable.CustomEditText_imageClearText, IMAGE_CLEAR_TEXT);
            mImageClearTextVisibility = attributes.getBoolean(R.styleable.CustomEditText_imageClearTextVisibility, true);
            mImageFilledTextVisibility = attributes.getBoolean(R.styleable.CustomEditText_imageFilledTextVisibility, true);
        } finally {
            attributes.recycle();
        }

        initializeComponents();
    }

    private void initializeComponents() {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.custom_edit_text, this, true);

        mImageViewFilledEditText = (ImageView) findViewById(R.id.image_view_filled_edit_text);
        mImageViewClearEditText = (ImageView) findViewById(R.id.image_view_clear_edit_text);
        mTextInputLayoutFilter = (TextInputLayout) findViewById(R.id.text_input_layout_filter);
        mEditTextFilter = (EditText) findViewById(R.id.edit_text_filter);

        mImageViewFilledEditText.setImageResource(mImageFilledText);
        mImageViewClearEditText.setImageResource(mImageClearText);
        mTextInputLayoutFilter.setHint(mFilterHint);
        mEditTextFilter.setText(mFilterText);
        mImageViewClearEditText.setVisibility(mImageClearTextVisibility ? VISIBLE : GONE);
        mImageViewFilledEditText.setVisibility(mImageFilledTextVisibility ? VISIBLE : GONE);

        mImageViewClearEditText.setOnClickListener(buttonClearListener);
        mEditTextFilter.addTextChangedListener(onEditTextChange);
    }

    private final OnClickListener buttonClearListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            mEditTextFilter.getText().clear();
        }
    };

    private final TextWatcher onEditTextChange = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            mImageViewClearEditText
                    .setVisibility(mEditTextFilter
                            .getText()
                            .toString()
                            .isEmpty() ? INVISIBLE : VISIBLE);

            mImageViewFilledEditText
                    .setVisibility(mEditTextFilter
                            .getText()
                            .toString()
                            .isEmpty() ? INVISIBLE : VISIBLE);

        }
    };

}
