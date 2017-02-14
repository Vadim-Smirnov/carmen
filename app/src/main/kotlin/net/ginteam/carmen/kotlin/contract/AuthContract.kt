package net.ginteam.carmen.kotlin.contract

import android.content.Intent
import android.support.v7.app.AppCompatActivity

/**
 * Created by eugene_shcherbinock on 2/14/17.
 */

object AuthContract {

    interface View : LocationContract.View {

        fun showCitiesDialog()
        fun showMainActivity()
        fun showSignInActivity()

    }

    interface Presenter : LocationContract.Presenter <View> {

        fun checkUserStatus()

    }

}

object SignInContract {

    interface View : BaseContract.View {

        fun showMainActivity()

    }

    interface Presenter : BaseContract.Presenter <View> {

        fun signIn(email: String, password: String)

    }

}

object SignUpContract {

    interface View : SignInContract.View

    interface Presenter : BaseContract.Presenter <View> {

        fun signUp(name: String, email: String, password: String)

    }

}