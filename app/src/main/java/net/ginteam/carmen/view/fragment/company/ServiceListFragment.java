package net.ginteam.carmen.view.fragment.company;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import net.ginteam.carmen.R;
import net.ginteam.carmen.model.category.CategoryModel;
import net.ginteam.carmen.view.adapter.ServiceListAdapter;
import net.ginteam.carmen.view.fragment.BaseFetchingFragment;


public class ServiceListFragment extends BaseFetchingFragment {

    private static final String CATEGORY_ARG = "category";
    private final static int VISIBLE_SERVICES_COUNT = 5;

    private CategoryModel mCurrentCategory;

    private RecyclerView mRecyclerViewServices;
    private ServiceListAdapter mServiceListAdapter;

    private TextView mTextViewServiceCategoryName;
    private ToggleButton mToggleButtonAllServices;

    public ServiceListFragment() {
    }

    public static ServiceListFragment newInstance(CategoryModel categoryModel) {
        ServiceListFragment fragment = new ServiceListFragment();
        Bundle args = new Bundle();
        args.putSerializable(CATEGORY_ARG, categoryModel);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCurrentCategory = (CategoryModel) getArguments().getSerializable(CATEGORY_ARG);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflateBaseFragment(R.layout.fragment_service_list, inflater, container, savedInstanceState);

        updateDependencies();
        mServiceListAdapter = new ServiceListAdapter(getContext(),
                mCurrentCategory.getServices().size() < VISIBLE_SERVICES_COUNT
                        ? mCurrentCategory.getServices().size()
                        : VISIBLE_SERVICES_COUNT,
                mCurrentCategory.getServices());
        mRecyclerViewServices.setAdapter(mServiceListAdapter);

        return mRootView;
    }

    private void updateDependencies() {
        mRecyclerViewServices = (RecyclerView) mRootView.findViewById(R.id.recycler_view_services);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerViewServices.setLayoutManager(layoutManager);

        mTextViewServiceCategoryName = (TextView) mRootView.findViewById(R.id.text_view_service_category_name);
        mTextViewServiceCategoryName.setText(mCurrentCategory.getName());
        mToggleButtonAllServices = (ToggleButton) mRootView.findViewById(R.id.button_all_services);
        mToggleButtonAllServices
                .setVisibility(mCurrentCategory.getServices().size() <= VISIBLE_SERVICES_COUNT ?
                        View.GONE : View.VISIBLE);
        mToggleButtonAllServices.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mServiceListAdapter
                        .setVisibleServiceCount(isChecked ? VISIBLE_SERVICES_COUNT :
                                mCurrentCategory.getServices().size());
                mServiceListAdapter.notifyDataSetChanged();
            }
        });
    }

}
