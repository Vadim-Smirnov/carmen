package net.ginteam.carmen.view.fragment.company;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import net.ginteam.carmen.R;
import net.ginteam.carmen.kotlin.model.CategoryModel;
import net.ginteam.carmen.view.fragment.BaseFetchingFragment;

import java.util.ArrayList;
import java.util.List;

public class ServiceCategoryListFragment extends BaseFetchingFragment {

    private static final String CATEGORIES_ARG = "categories";

    private List<CategoryModel> mCategories;
    private LinearLayout mLinearLayoutFragments;

    public ServiceCategoryListFragment() {}

    public static ServiceCategoryListFragment newInstance(List<CategoryModel> categories) {
        ServiceCategoryListFragment fragment = new ServiceCategoryListFragment();
        Bundle args = new Bundle();
        args.putSerializable(CATEGORIES_ARG, new ArrayList<>(categories));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
           mCategories = (ArrayList<CategoryModel>) getArguments().getSerializable(CATEGORIES_ARG);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflateBaseFragment(R.layout.fragment_service_category_list, inflater, container, savedInstanceState);

        updateDependencies();
        showServiceCategories();

        return mRootView;
    }

    private void updateDependencies() {
        mLinearLayoutFragments = (LinearLayout) mRootView.findViewById(R.id.linear_layout_fragments);
    }

    private void showServiceCategories() {
        for (CategoryModel currentCategory : mCategories) {
            if (!currentCategory.getServices().isEmpty()) {
                LinearLayout rowLayout = new LinearLayout(getContext());
                FragmentManager fragMan = getFragmentManager();
                FragmentTransaction fragTransaction = fragMan.beginTransaction();
                rowLayout.setId(View.generateViewId());
                mLinearLayoutFragments.addView(rowLayout);
                BaseFetchingFragment myFrag = ServiceListFragment.newInstance(currentCategory);
                fragTransaction.add(rowLayout.getId(), myFrag, "fragment");
                fragTransaction.commit();
            }
        }
    }

}
