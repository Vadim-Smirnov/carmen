package net.ginteam.carmen.view.fragment;

import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.util.Log;

import net.ginteam.carmen.R;

/**
 * Created by Eugene on 1/27/17.
 */

public abstract class BaseFragment extends DialogFragment {

    protected OnSetToolbarTitleListener mSetToolbarTitleListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mSetToolbarTitleListener = (OnSetToolbarTitleListener) context;
        } catch (ClassCastException exception) {
            Log.e("BaseFragment", "Parent context does not confirm to OnSetToolbarTitleListener!");
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        if (getDialog() == null) {
            return;
        }
        int dialogWidth = (int) getResources().getDimension(R.dimen.dialog_fragment_width);
        int dialogHeight = (int) getResources().getDimension(R.dimen.dialog_fragment_height);

        getDialog().getWindow().setLayout(dialogWidth, dialogHeight);
        getDialog().setCancelable(false);
    }

    public boolean isNotNestedFragment() {
        return getParentFragment() == null;
    }

    public void setToolbarTitle(String title, String subtitle) {
        if (mSetToolbarTitleListener != null) {
            mSetToolbarTitleListener.setToolbarTitle(title, subtitle);
        }
    }

    public interface OnSetToolbarTitleListener {

        void setToolbarTitle(String title, String subtitle);

    }

}
