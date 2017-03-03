package net.ginteam.carmen.kotlin.view.fragment

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.DownloadListener
import android.webkit.WebView
import android.webkit.WebViewClient
import net.ginteam.carmen.R
import net.ginteam.carmen.kotlin.contract.WebViewContract
import net.ginteam.carmen.kotlin.presenter.WebViewPresenter

/**
 * Created by vadik on 03.03.17.
 */

class WebViewFragment : BaseFragment<WebViewContract.View, WebViewContract.Presenter>(), WebViewContract.View {

    override var mPresenter: WebViewContract.Presenter = WebViewPresenter()

    private lateinit var mUrl: String

    protected lateinit var mWebView: WebView

    companion object {
        const val URL_ARGUMENT = "url"

        fun newInstance(url: String) : WebViewFragment {
            val bundle = Bundle()
            bundle.putString(URL_ARGUMENT, url)

            val instance: WebViewFragment = WebViewFragment()
            instance.arguments = bundle
            return instance
        }
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mWebView.loadUrl(mUrl)
    }

    override fun getLayoutResId(): Int = R.layout.fragment_web_view

    override fun updateViewDependencies() {
        super.updateViewDependencies()

        mWebView = mFragmentView.findViewById(R.id.web_view) as WebView
        mWebView.setWebViewClient(object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                showLoading(true)
            }
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                showLoading(false)
            }
        })
    }

    override fun updateDependencies() {
        super.updateDependencies()
        mUrl = arguments.getString(URL_ARGUMENT)
    }
}