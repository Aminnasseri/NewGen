package com.zibfit.diax


import android.content.Intent
import android.content.res.AssetManager
import android.graphics.Bitmap
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_take_blood.*
import kotlinx.android.synthetic.main.activity_watch_video.*
import kotlinx.android.synthetic.main.toolbar_open_kit.*


class WatchVideoActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_watch_video)

        home_toolbar_open_kit.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    MainActivity::class.java
                )
            )
            overridePendingTransition(R.anim.left_in, R.anim.right_out)
            finish()
        }

        if (webView != null) {
            val webViewSetting = webView.settings
            webViewSetting.javaScriptEnabled = true

            webView.webViewClient = WebViewClient()
            webView.webChromeClient = WebChromeClient()
            webView.loadUrl("http://racordiax.eu/tutorial?platform=mobile/")

            webView.webViewClient = object : WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {

                    progressBarWatch.visibility = View.VISIBLE
                    super.onPageStarted(view, url, favicon)


                }

                override fun onPageFinished(view: WebView?, url: String?) {

                    progressBarWatch.visibility = View.GONE
                    super.onPageFinished(view, url)


                }
            }
        }

    }
    override fun getAssets(): AssetManager {
        return resources.assets
    }
}




