package net.ginteam.carmen.kotlin

/**
 * Created by eugene_shcherbinock on 2/14/17.
 */

object Constants {

    object Preferences {
        const val NAME = "name"
        const val USER_TOKEN = "user_token"
        const val USER_LOCATION = "user_location"
        const val CITY = "city"
        const val LAUNCH = "launch"
    }

    object Realm {
        const val DATABASE_NAME = "carmen_db.realm"
        const val INIT_FILE_PATH = "realm_init/init_file.txt"

        object History {
            const val ID = "id"
            const val DATE = "date"
            const val PRICE = "price"
            const val ODOMETER = "odometer"
            const val COMMENT = "comment"
            const val COST_TYPE = "costType"
        }

        object CostType {
            const val ID = "id"
            const val NAME = "name"
            const val ICON = "iconId"
            const val COLOR = "color"
            const val ATTRIBUTES = "attributes"
        }

        object CostTypeAttribute {
            const val ID = "id"
            const val TITLE = "title"
            const val TYPE = "type"
        }

        object AttributesHistory {
            const val ID = "id"
            const val VALUE = "value"
            const val COST_ATTRIBUTE = "costAttribute"
            const val HISTORY = "history"
        }

    }

    const val SEARCH_BUTTON_ID: Int = android.support.v7.appcompat.R.id.search_button
    const val PRIVACY_POLICY_URL = "http://car-men.com.ua/privacy-policy.html"

}