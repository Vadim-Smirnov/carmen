package net.ginteam.carmen.kotlin.contract

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

object BaseSignContract {

    interface View : BaseContract.View {

        fun showMainActivity()

    }

    interface Presenter<in V : View> : BaseContract.Presenter <V>

}

object SignInContract {

    interface View : BaseSignContract.View

    interface Presenter : BaseSignContract.Presenter <View> {

        fun signIn(email: String, password: String)
        fun facebookSignIn()
        fun googleSignIn()

        fun isUserSignedInSuccessfully(): Boolean

    }

}

object SignUpContract {

    interface View : SignInContract.View

    interface Presenter : BaseSignContract.Presenter <View> {

        fun signUp(name: String, email: String, password: String)

    }

}