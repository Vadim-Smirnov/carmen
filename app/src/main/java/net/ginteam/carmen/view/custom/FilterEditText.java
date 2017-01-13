package net.ginteam.carmen.view.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import net.ginteam.carmen.R;
import net.ginteam.carmen.model.filter.FilterModel;
import net.ginteam.carmen.model.filter.FilterOptionModel;
import net.ginteam.carmen.view.adapter.filter.FilterOptionsListAdapter;

import java.util.List;

/**
 * Created by vadik on 05.01.17.
 */

public class FilterEditText extends LinearLayout {

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

    private String mFilterType;
    private FilterModel mFilterModel;
    private FilterOptionModel mSelectedFilterOption;
    private AlertDialog mFilterDialog;

    private OnFilterChangeListener mFilterChangeCompleteListener;

    private final OnClickListener mButtonClearClickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            mEditTextFilter.getText().clear();
            mEditTextFilter.clearFocus();
            mSelectedFilterOption = null;

            callListener();
        }
    };
    private final OnTouchListener mEditTextClickListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mFilterDialog.show();
            view.requestFocus();
            return false;
        }
    };
    private final OnFocusChangeListener mEditTextFocusChangeListener = new OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean b) {
            if (!b) {
                callListener();
            }
        }
    };
    private final TextWatcher mEditTextChangeListener = new TextWatcher() {
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

    // --------------------------- Constructors -----------------------------------

    public FilterEditText(Context context) {
        super(context);
        initializeCustomEditText(context, null);
    }

    public FilterEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeCustomEditText(context, attrs);
    }

    public FilterEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeCustomEditText(context, attrs);
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    public FilterEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initializeCustomEditText(context, attrs);
    }

    // ----------------------------------------------------------------------------

    // -------------------------- Getters and Setters -----------------------------

    public void setOnFilterChangeListener(OnFilterChangeListener listener) {
        mFilterChangeCompleteListener = listener;
    }

    public void setFilterModel(FilterModel filter) {
        mFilterModel = filter;
        updateDependenciesWithFilter(mFilterModel);
    }

    public String getStringFilter() {
        String filter = mFilterType + "=";
        if (mFilterModel.getFilterOptions() == null) {
            filter += mEditTextFilter.getText();
        } else {
            filter += (mSelectedFilterOption != null ? mSelectedFilterOption.getKey() : "");
        }
        return filter + ";";
    }

    public int getImageClearText() {
        return mImageClearText;
    }

    public void setImageClearText(int imageClearText) {
        mImageClearText = imageClearText;
        mImageViewClearEditText.setImageResource(mImageClearText);
    }

    public int getImageFilledText() {
        return mImageFilledText;
    }

    public void setImageFilledText(int imageFilledText) {
        mImageFilledText = imageFilledText;
        mImageViewFilledEditText.setImageResource(mImageFilledText);
    }

    public String getFilterHint() {
        return mFilterHint;
    }

    public void setFilterHint(String filterHint) {
        mFilterHint = filterHint;
        mTextInputLayoutFilter.setHint(mFilterHint);
    }

    public String getFilterText() {
        return mFilterText;
    }

    public void setFilterText(String filterText) {
        mFilterText = filterText;
        mEditTextFilter.setText(mFilterText);
    }

    // ----------------------------------------------------------------------------

    // -------------------------- Private Methods ---------------------------------

    private void initializeCustomEditText(Context context, AttributeSet attrs) {
        mContext = context;

        mImageFilledText = IMAGE_FILLED_TEXT;
        mImageClearText = IMAGE_CLEAR_TEXT;

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.FilterEditText, 0, 0);
        try {
            mFilterText = attributes.getString(R.styleable.FilterEditText_filterText);
            mFilterHint = attributes.getString(R.styleable.FilterEditText_filterHint);
            mImageFilledText = attributes.getResourceId(R.styleable.FilterEditText_imageFilledText, IMAGE_FILLED_TEXT);
            mImageClearText = attributes.getResourceId(R.styleable.FilterEditText_imageClearText, IMAGE_CLEAR_TEXT);
        } finally {
            attributes.recycle();
        }

        initializeComponents();
    }

    private void initializeComponents() {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.filter_edit_text, this, true);

        mImageViewFilledEditText = (ImageView) findViewById(R.id.image_view_filled_edit_text);
        mImageViewClearEditText = (ImageView) findViewById(R.id.image_view_clear_edit_text);
        mTextInputLayoutFilter = (TextInputLayout) findViewById(R.id.text_input_layout_filter);
        mEditTextFilter = (EditText) findViewById(R.id.edit_text_filter);

        mImageViewFilledEditText.setImageResource(mImageFilledText);
        mImageViewClearEditText.setImageResource(mImageClearText);
        mTextInputLayoutFilter.setHint(mFilterHint);
        mEditTextFilter.setText(mFilterText);

        mImageViewClearEditText.setOnClickListener(mButtonClearClickListener);
    }

    private void updateDependenciesWithFilter(FilterModel filter) {
        mFilterType = filter.getType();
        setFilterHint(filter.getName());

        if (filter.getFilterOptions() != null) {
            createFilterDialog(filter.getFilterOptions());
            disableEditingForEditText(mEditTextFilter);
        }

        mEditTextFilter.setOnFocusChangeListener(mEditTextFocusChangeListener);
        mEditTextFilter.addTextChangedListener(mEditTextChangeListener);
    }

    private void createFilterDialog(List<FilterOptionModel> options) {
        View dialogOptionsView = LayoutInflater
                .from(mContext)
                .inflate(R.layout.filter_dialog_layout, null, false);

        initializeDialogListView(dialogOptionsView, options);

        mFilterDialog = new AlertDialog.Builder(mContext)
                .setCancelable(false)
                .setView(dialogOptionsView)
                .create();
    }

    private void disableEditingForEditText(EditText editText) {
        editText.setInputType(InputType.TYPE_NULL);
        editText.setCursorVisible(false);
        editText.setOnTouchListener(mEditTextClickListener);
    }

    private void initializeDialogListView(View view, List <FilterOptionModel> options) {
        ListView optionsListView = (ListView) view.findViewById(R.id.list_view_dialog_options);
        final FilterOptionsListAdapter optionsListAdapter = new FilterOptionsListAdapter(mContext, options);
        optionsListView.setAdapter(optionsListAdapter);
        optionsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mSelectedFilterOption = optionsListAdapter.getItem(i);
                mEditTextFilter.setText(mSelectedFilterOption.getValue());
                mEditTextFilter.clearFocus();
                mFilterDialog.dismiss();
            }
        });
    }

    private void callListener() {
        if (mFilterChangeCompleteListener != null) {
            mFilterChangeCompleteListener.onFilterChanged(this);
        }
    }

    // ----------------------------------------------------------------------------

    // ------------------------------ Listeners -----------------------------------

    public interface OnFilterChangeListener {

        void onFilterChanged(FilterEditText filterEditText);

    }

    // ----------------------------------------------------------------------------

}