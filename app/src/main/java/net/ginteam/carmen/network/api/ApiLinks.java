package net.ginteam.carmen.network.api;

/**
 * Created by Eugene on 12/23/16.
 */

public class ApiLinks {

    public class CATALOG {

        private static final String CATALOG = "catalog/";

        public static final String ID = "id";

        public static final String CATEGORIES = CATALOG + "categories";

        public static final String CATEGORY_BY_ID = CATEGORIES + "/{" + ID + "}";

        public static final String CITIES = CATALOG + "cities";

    }

}
