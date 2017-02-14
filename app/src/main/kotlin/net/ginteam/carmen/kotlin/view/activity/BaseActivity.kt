package net.ginteam.carmen.kotlin.view.activity

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.TextView
import android.widget.Toast
import net.ginteam.carmen.R
import net.ginteam.carmen.kotlin.contract.BaseContract
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

abstract class BaseActivity <in V : BaseContract.View, T : BaseContract.Presenter <V>>
    : AppCompatActivity(), BaseContract.View {

    protected abstract var mPresenter: T

    protected var mToolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter.attachView(this as V)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        initializeToolbar()
    }

    override fun attachBaseContext(newBase: Context?)
            = super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))

    override fun getContext(): Context = this

    override fun showError(message: String?)
            = Toast.makeText(this, message, Toast.LENGTH_LONG).show()

    override fun showError(messageResId: Int)
            = Toast.makeText(this, messageResId, Toast.LENGTH_LONG).show()

    override fun showMessage(message: String)
            = Toast.makeText(this, message, Toast.LENGTH_LONG).show()

    override fun showMessage(messageResId: Int)
            = Toast.makeText(this, messageResId, Toast.LENGTH_LONG).show()

    override fun showLoading(show: Boolean)
            = Toast.makeText(this, "Loading show: $show", Toast.LENGTH_LONG).show()

    protected fun setTitle(title: String)
            = (mToolbar?.findViewById(R.id.text_view_toolbar_title) as TextView).setText(title)

    protected fun setSubtitle(subtitle: String)
            = (mToolbar?.findViewById(R.id.text_view_toolbar_subtitle) as TextView).setText(subtitle)

    private fun initializeToolbar() {
        mToolbar = findViewById(R.id.toolbar) as Toolbar
        mToolbar?.let {
            setSupportActionBar(mToolbar)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }
    }

}
