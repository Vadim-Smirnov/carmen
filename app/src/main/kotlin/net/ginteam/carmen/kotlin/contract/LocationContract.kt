package net.ginteam.carmen.kotlin.contract

import android.content.Intent
import android.support.v7.app.AppCompatActivity

/**
 * Created by eugene_shcherbinock on 2/14/17.
 */

object LocationContract {

    interface View : BaseContract.View {

        fun getActivity(): AppCompatActivity

    }

    interface Presenter<in V : View> : BaseContract.Presenter <V> {

        fun fetchUserLocation()

        fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
        fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray)

    }

}