package net.ginteam.carmen.kotlin.api

/**
 * Created by eugene_shcherbinock on 2/9/17.
 */

object ApiSettings {

    object Catalog {
        private const val CATALOG = "catalog"

        object Params {
            const val ID = "id"
            const val SEARCH = "search"
            const val ORDER_BY = "orderBy"
            const val SORTED_BY = "sortedBy"
            const val SEARCH_FIELD_TYPE = "searchFields"
            const val PAGE = "page"
            const val LIMIT = "limit"
            const val CITY_ID = "cityId"
            const val BOUNDS = "bound"
            const val LATITUDE = "lat"
            const val LONGITUDE = "lng"
        }

        object Relations {
            const val WITH = "with"
            const val COMFORTS = "comforts"
            const val CATEGORIES = "categories"
            const val DETAILS = "detail"
            const val RATINGS = "ratings"
            const val USER_RATING = "ratingByUser"
        }

        const val GET_CATEGORIES = "$CATALOG/categories"
        const val GET_CATEGORY_BY_ID = "$GET_CATEGORIES/{${Params.ID}}"

        const val GET_COMPANIES = "$GET_CATEGORY_BY_ID/companies"
        const val GET_COMPANY_BY_ID = "$CATALOG/companies/{${Params.ID}}"
        const val GET_POPULAR_COMPANIES_BY_CITY_ID = "$CATALOG/companies/popularByCity/{${Params.CITY_ID}}"

        const val GET_CITIES = "$CATALOG/cities"
        const val GET_CITY_BY_POINT = "$GET_CITIES/findByPoint"

        const val CREATE_RATING = "$CATALOG/companies/ratings"
        const val UPDATE_RATING = "$CREATE_RATING/{${Params.ID}}"

    }

    object Auth {
        private const val AUTH = "auth"
        private const val USERS = "$AUTH/users"

        object Params {
            const val ID = "id"
            const val DEVICE_ID = "deviceId"
            const val PUSH_TOKEN = "pushToken"
            const val DEVICE_TYPE = "deviceType"
            const val NAME = "name"
            const val EMAIL = "email"
            const val PASSWORD = "password"
            const val AUTH_HEADER = "Authorization"
        }

        const val USER_LOGIN = "$USERS/login"
        const val USER_REGISTER = "$USERS/register"

        const val GET_CURRENT_USER = "$USERS/me"
        const val GET_USER_FAVORITE_COMPANIES = "$GET_CURRENT_USER/companies/favorites"
        const val GET_FAVORITE_COMPANY_BY_ID = "$GET_CURRENT_USER/companies/{${Params.ID}}/favorites"
        const val GET_USER_RECENTLY_WATCHED = "$GET_CURRENT_USER/companies/lastViewed"

        const val DEVICE_INIT = "$AUTH/devices/init"
    }

    object News {
        private const val NEWS = "news"

        object Params {
            const val ID = "id"
            const val PAGE = "page"
            const val LIMIT = "limit"
        }

        const val ARTICLES = "$NEWS/articles"

        const val GET_NEWS_BY_ID = "$ARTICLES/{${Params.ID}}"

    }

}