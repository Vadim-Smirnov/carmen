package net.ginteam.carmen.data.provider.company.favorites;

import net.ginteam.carmen.data.provider.DataSourceProvider;
import net.ginteam.carmen.data.provider.company.CompaniesSourceProvider;

/**
 * Created by eugene_shcherbinock on 2/7/17.
 */

public interface FavoritesSourceProvider extends DataSourceProvider {

    void fetchFavorites(String lat, String lng, final CompaniesSourceProvider.CompaniesCallback callback);

    void addToFavorites(int id, final AddToFavoriteCallback callback);

    void removeFromFavorites(int id, final RemoveFromFavoriteCallback callback);

    interface AddToFavoriteCallback {

        void success();

        void error();

    }

    interface RemoveFromFavoriteCallback extends AddToFavoriteCallback {}

}
