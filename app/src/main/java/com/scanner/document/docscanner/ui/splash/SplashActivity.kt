package com.scanner.document.docscanner.ui.splash

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.scanner.document.docscanner.R
import com.scanner.document.docscanner.ui.home.HomeActivity

/**
 * Created by AnthonyCAS on 8/20/18.
 */

class SplashActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val secondsDelayed = 1
        Handler().postDelayed(Runnable {
            startActivity(HomeActivity.newIntent(this))
            finish()
        }, (secondsDelayed * 1500).toLong())
    }

}