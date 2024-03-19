package com.zibfit

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.zibfit.Game4Activity.Companion.GAME4
import kotlinx.android.synthetic.main.activity_cure2.*

class Cure2Activity : AppCompatActivity() {
    companion object {
        const val CURE2 = "names"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cure2)
        val mp = MediaPlayer.create(this, R.raw.cure);
        mp.start()

        val nameList = intent.getStringArrayListExtra(GAME4)
        val nameVirus2 = intent.getStringExtra(Virus2Activity.RANDOMNAME2)
        val questVirus2 = intent.getStringExtra(Virus2Activity.RANDOMQUEST2)

        if (questVirus2.contains("چاله") ) {
            txtcCure2.text = " \"$nameVirus2 \"، برگرد سر زندگی عادیت دیگه نیازی نیست سیاه چاله باشی"
        }
        if (questVirus2.contains("تکرار")) {
            txtcCure2.text = " \"$nameVirus2 \"، راحت باش دیگه لازم نیست تکرار کنی."
        }
        if (questVirus2.contains("دستی")) {
            txtcCure2.text = "خبر رسید کشور روسیه واکسن دست رو درست کرده، میتونین با هر دستی که دوست دارین هرکاری میخواین بکنین"
        }


        var touchCount = 0
        cure2.setOnClickListener{
            touchCount  = touchCount  + 1;
            if (touchCount  == 2){
                touchCount=0;
                val intent =  Intent(this,Game5Activity::class.java)
                intent.putExtra(CURE2, nameList)
                startActivity(intent);
            }
        }
    }
    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "یه بار دیگه دکمه برگشت رو بزن تا از برنامه خارج شی", Toast.LENGTH_SHORT).show()

        Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
    }
}
