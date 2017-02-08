package net.ginteam.carmen.view.fragment.company;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.ginteam.carmen.R;
import net.ginteam.carmen.model.company.Comfort;
import net.ginteam.carmen.view.adapter.AdditionalServiceListAdapter;
import net.ginteam.carmen.view.fragment.BaseFetchingFragment;

import java.util.ArrayList;
import java.util.List;


public class AdditionalServicesFragment extends BaseFetchingFragment {

    private static final String COMFORTS_KEY = "comforts";

    private List<Comfort> mComforts;

    private RecyclerView mRecyclerViewAdditionalServices;
    private AdditionalServiceListAdapter mAdditionalServiceListAdapter;

    public AdditionalServicesFragment() {
    }

    public static AdditionalServicesFragment newInstance(List<Comfort> comforts) {
        AdditionalServicesFragment fragment = new AdditionalServicesFragment();
        Bundle args = new Bundle();
        args.putSerializable(COMFORTS_KEY, new ArrayList<>(comforts));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mComforts = (ArrayList<Comfort>) getArguments().getSerializable(COMFORTS_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflateBaseFragment(R.layout.fragment_additional_services, inflater, container, savedInstanceState);

        updateDependencies();
        mAdditionalServiceListAdapter = new AdditionalServiceListAdapter(getContext(), mComforts);
        mRecyclerViewAdditionalServices.setAdapter(mAdditionalServiceListAdapter);

        return mRootView;
    }

    private void updateDependencies() {
        mRecyclerViewAdditionalServices = (RecyclerView) mRootView.findViewById(R.id.recycler_view_additional_services);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerViewAdditionalServices.setLayoutManager(layoutManager);
    }

}
