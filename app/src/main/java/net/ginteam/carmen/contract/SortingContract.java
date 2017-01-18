package net.ginteam.carmen.contract;

import net.ginteam.carmen.model.SortingModel;

import java.util.List;

/**
 * Created by vadik on 18.01.17.
 */

public interface SortingContract {

    interface View extends FetchContract.View {

        void showSorting(List<SortingModel> sortingModels);

    }

    interface Presenter extends FetchContract.Presenter <View> {

        void fetchSortingForCategory(int categoryId);

    }

}
