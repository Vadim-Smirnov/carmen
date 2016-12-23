package net.ginteam.carmen.contract.category;

import net.ginteam.carmen.contract.FetchContract;
import net.ginteam.carmen.model.category.CategoryModel;

import java.util.List;

/**
 * Created by Eugene on 12/23/16.
 */

public interface CategoriesContract {

    interface View extends FetchContract.View {

        void showCategories(List <CategoryModel> categories);

    }

    interface Presenter extends FetchContract.Presenter <View> {

        void selectCategory(CategoryModel category);

    }

}
