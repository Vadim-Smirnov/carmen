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

        public static final String COMPANIES_BY_CATEGORY = CATEGORY_BY_ID + "/companies";

        public static final String CITIES = CATALOG + "cities";

        public static final String CITIES_BY_POINT = CITIES + "/findByPoint";

        public static final String LAT = "lat";

        public static final String LON = "lon";
    }

    public class AUTH {

        private static final String AUTH = "auth/";

        private static final String USERS = AUTH + "users/";

        public static final String NAME = "name";

        public static final String EMAIL = "email";

        public static final String PASSWORD = "password";

        public static final String LOGIN = USERS + "login";

        public static final String REGISTER = USERS + "register";

        public static final String AUTH_HEADER = "Authorization";

        public static final String GET_CURRENT_USER = USERS + "me";

    }

}
