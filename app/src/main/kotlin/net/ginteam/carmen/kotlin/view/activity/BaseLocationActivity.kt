package net.ginteam.carmen.kotlin.view.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import net.ginteam.carmen.kotlin.contract.LocationContract

abstract class BaseLocationActivity <in V : LocationContract.View, T : LocationContract.Presenter <V>>
    : BaseActivity <V, T>(), LocationContract.View {

    override fun getActivity(): AppCompatActivity = this

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mPresenter.onActivityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        mPresenter.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

}
