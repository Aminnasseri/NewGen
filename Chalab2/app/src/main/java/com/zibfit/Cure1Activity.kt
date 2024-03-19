package com.zibfit

import android.annotation.SuppressLint
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.zibfit.Question2Activity.Companion.QUEST2
import kotlinx.android.synthetic.main.activity_cure1.*

class Cure1Activity : AppCompatActivity() {
    companion object {
        const val CURE1 = "names"
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cure1)
        val nameVirus1 = intent.getStringExtra(Virus1Activy.RANDOMNAME1)
        val questVirus1 = intent.getStringExtra(Virus1Activy.RANDOMQUEST1)
        val nameList = intent.getStringArrayListExtra(QUEST2)
        val mp = MediaPlayer.create(this, R.raw.cure);
        mp.start()


//"\"$nameVirus1\""

        if (questVirus1.contains("ترکی")) {
            txtcCure1.text =
                "\" $nameVirus1 \"، دیگه لازم نیست با لهجه ترکی ادامه بدی بر گرد به تنظیمات کارخانه، البته اگه خودت ترک نیستی."
        }
        if (questVirus1.contains("ادبانه")) {
            txtcCure1.text =
                "بچه ها دیگه وقتشه با \" $nameVirus1 \"  با ادب باشین، \" $nameVirus1 \" آزادی دیگه جواب بدی."
        }
        if (questVirus1.contains("دقیقا عزیزم")) {
            txtcCure1.text = "\" $nameVirus1 \"، درمانت رسید دیگه لازم نیست همرو تایید کنی. "
        }


        var touchCount = 0
        cure1.setOnClickListener {
            touchCount = touchCount + 1;
            if (touchCount == 2) {
                touchCount = 0;
                val intent = Intent(this, Question3Activity::class.java)
                intent.putExtra(CURE1, nameList)
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
        Toast.makeText(
            this,
            "یه بار دیگه دکمه برگشت رو بزن تا از برنامه خارج شی",
            Toast.LENGTH_SHORT
        ).show()

        Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
    }
}
