package com.zibfit

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.zibfit.Game7Activity.Companion.GAME7
import kotlinx.android.synthetic.main.activity_cure3.*

class Cure3Activity : AppCompatActivity() {
    companion object {
        const val CURE3 = "names"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cure3)
        val mp = MediaPlayer.create(this, R.raw.cure);
        mp.start()

        val nameList = intent.getStringArrayListExtra(GAME7)
        val nameVirus3 = intent.getStringExtra(Virus3Activity.RANDOMNAME3)
        val questVirus3 = intent.getStringExtra(Virus3Activity.RANDOMQUEST3)

        if (questVirus3.contains("عروسک") ) {
            txtcCure3.text = " \"$nameVirus3 \"، تو دیگه عروسک گردان نیستی.یکم به دستات استراحت بده."
        }
        if (questVirus3.contains("قهرمان")) {
            txtcCure3.text = " \"$nameVirus3 \"، دوران قهرمانیت تموم شد.برگرد به زندگی عادیت."
        }
        if (questVirus3.contains("شاه")) {
            txtcCure3.text = "دیگه بهتره برگردین به اسمهای خودتون.لقب هارو فراموش کنین."
        }


        var touchCount = 0
        cure3.setOnClickListener{
            touchCount  = touchCount  + 1;
            if (touchCount  == 2){
                touchCount=0;
                val intent =  Intent(this,Game8Activity::class.java)
                intent.putExtra(CURE3, nameList)
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