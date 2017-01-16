package net.ginteam.carmen.contract.filter;

import net.ginteam.carmen.contract.BaseContract;
import net.ginteam.carmen.model.filter.FilterModel;

import java.util.List;

/**
 * Created by Eugene on 1/15/17.
 */

public interface FilterContract {

    interface View extends BaseContract.View {

        void showLoading(boolean isLoading);

        void showError(String message);

        void showResultsCount(int count);

        void showFilters(List <FilterModel> filters);

    }

    interface Presenter extends BaseContract.Presenter <View> {

        void fetchFiltersForCategory(int categoryId);

        void updateResultsWithFilter(int categoryId, String filter);

    }

}
