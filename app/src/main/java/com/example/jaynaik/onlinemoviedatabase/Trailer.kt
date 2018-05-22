package com.example.jaynaik.onlinemoviedatabase

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView

class Trailer : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trailer)



        var url = intent.getSerializableExtra("link") as String
        var webview = findViewById<WebView>(R.id.webview)
webview.settings.javaScriptEnabled = true
        webview.loadUrl(url)
    }
}
