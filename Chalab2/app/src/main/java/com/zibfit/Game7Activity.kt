package com.zibfit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.zibfit.Love3Activity.Companion.LOVE3
import kotlinx.android.synthetic.main.activity_game7.*
import java.util.*
import kotlin.collections.ArrayList

class Game7Activity : AppCompatActivity() {
    companion object {
        const val GAME7 = "names"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game7)

        val nameList = intent.getStringArrayListExtra(LOVE3)
        val nameVirus3 = intent.getStringExtra(Virus3Activity.RANDOMNAME3)
        val questVirus3 = intent.getStringExtra(Virus3Activity.RANDOMQUEST3)
        val random = Random()
        val Low = 0
        var High = nameList.size
        val Result = random.nextInt(High - Low) + Low
        val randomName = nameList[Result]

        val questionList: ArrayList<String> = ArrayList()
        questionList.add("\" $randomName \"، تو ۳۰ ثانیه ربات دنس بزن یا ۴ قلپ بخور. ")
        questionList.add("رای گیری، سن بالا یا پایین؟ گروه بازنده ۳ قلپ بره بالا ماشالاااا…")
        questionList.add("فک کنین توی یه جزیره نا شناخته گیر افتادین،دوست داری هیچ کس نباشه یا با ۴ نفری که ازشون متنفری باهات باشن؟ رای گیری کنین گروه بازنده ۲ قلپ.")


        val randomValueQuest = random.nextInt(questionList.size)
        val randomQuest = questionList[randomValueQuest]
        txtGame7.text = randomQuest

        var touchCount = 0
        game7.setOnClickListener{
            touchCount  = touchCount  + 1;
            if (touchCount  == 2){
                touchCount=0;
                val intent =  Intent(this,Cure3Activity::class.java)
                intent.putExtra(GAME7, nameList)
                intent.putExtra(Virus3Activity.RANDOMNAME3, nameVirus3)
                intent.putExtra(Virus3Activity.RANDOMQUEST3, questVirus3)
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