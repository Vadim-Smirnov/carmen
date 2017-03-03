package net.ginteam.carmen.kotlin.contract

/**
 * Created by vadik on 03.03.17.
 */
object WebViewContract {

    interface View : BaseContract.View {

    }

    interface Presenter : BaseContract.Presenter<View> {

    }

}