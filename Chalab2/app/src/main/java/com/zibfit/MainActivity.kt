package com.zibfit

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startLottie.setAnimation(R.raw.start)

        val background = object : Thread() {
            override fun run() = try {
                Thread.sleep(3000)

                startActivity(Intent(this@MainActivity, SelectPlayerActivity::class.java))
            } catch (e: Exception) {
                println(e)
            }
        }
        background.start()


    }
}
