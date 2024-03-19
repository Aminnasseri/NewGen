package com.zibfit.vaxdiax

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.toolbar_open_kit.*

class VideoTutorialActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_tutorial)
        home_toolbar_open_kit.setOnClickListener {  startActivity(Intent(this,HomeActivity::class.java))
            overridePendingTransition(R.anim.left_in, R.anim.right_out)
            finish()
        }
    }
}