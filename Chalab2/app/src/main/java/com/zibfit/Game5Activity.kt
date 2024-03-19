package com.zibfit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.zibfit.Cure2Activity.Companion.CURE2
import kotlinx.android.synthetic.main.activity_game5.*
import java.util.*
import kotlin.collections.ArrayList

class Game5Activity : AppCompatActivity() {
    companion object {
        const val GAME5 = "names"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game5)

        val nameList = intent.getStringArrayListExtra(CURE2)
        val random = Random()
        val Low = 0
        var High = nameList.size
        val Result = random.nextInt(High - Low) + Low
        val randomName = nameList[Result]

        val questionList: ArrayList<String> = ArrayList()
        questionList.add("همگی بگن که چه کاری رو تاحالا انجام دادن. اونایی که تا حالا اون کارو انجام ندادن  ۱ قلپ برن بالا . \" $randomName \" تو شروع کن.")
        questionList.add("برای بیرون رفتن از کلاس چه بهانه ای میشه آورد؟ اولین کسی که نتونه بگه باید ۴ قلپ بزنه . \" $randomName \"")
        questionList.add("\" $randomName \"، یه تیکه از یه فیلم رو بازی کن، اولین کسی که بتونه درست حدس بزنه میتونه یکی رو انتخاب کنه که ۳ قلپ نوش جان کنه. ")

        val randomValueQuest = random.nextInt(questionList.size)
        val randomQuest = questionList[randomValueQuest]
        txtGame5.text = randomQuest

        var touchCount = 0
        game5.setOnClickListener{
            touchCount  = touchCount  + 1;
            if (touchCount  == 2){
                touchCount=0;
                val intent =  Intent(this,Virus3Activity::class.java)
                intent.putExtra(GAME5, nameList)
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
