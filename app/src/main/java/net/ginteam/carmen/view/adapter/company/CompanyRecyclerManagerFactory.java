package net.ginteam.carmen.view.adapter.company;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import net.ginteam.carmen.CarmenApplication;
import net.ginteam.carmen.view.fragment.company.CompanyListFragment;

/**
 * Created by Eugene on 1/5/17.
 */

public class CompanyRecyclerManagerFactory {

    public static RecyclerView.LayoutManager createManagerForListType(CompanyListFragment.COMPANY_LIST_TYPE type) {
        switch (type) {
            case RECENTLY_WATCHED: case POPULAR:
                return horizontalLayoutManager();
            default:
                return verticalLayoutManager();
        }
    }

    private static RecyclerView.LayoutManager horizontalLayoutManager() {
        return new LinearLayoutManager(CarmenApplication.getContext(), LinearLayoutManager.HORIZONTAL, false);
    }

    private static RecyclerView.LayoutManager verticalLayoutManager() {
        return new LinearLayoutManager(CarmenApplication.getContext(), LinearLayoutManager.VERTICAL, false);
    }

}
