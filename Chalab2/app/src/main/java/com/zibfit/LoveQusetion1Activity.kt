package com.zibfit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import com.zibfit.Virus1Activy.Companion.VIRUS1
import kotlinx.android.synthetic.main.activity_love_qusetion1.*
import kotlinx.android.synthetic.main.activity_question1.socialFirst
import java.util.*
import kotlin.collections.ArrayList

class LoveQusetion1Activity : AppCompatActivity() {
    companion object {
        const val LOVEQUEST1 = "names"
        const val RANDOMNAME1 = "randomName"
        const val RANDOMQUEST1 = "randomQuest"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_love_qusetion1)
        val nameList = intent.getStringArrayListExtra(VIRUS1)
        val nameVirus1 = intent.getStringExtra(RANDOMNAME1)
        val questVirus1 = intent.getStringExtra(RANDOMQUEST1)
        Log.d("CURE1","in esm $nameVirus1 .....  in soal $questVirus1 ")
        val random = Random()
        val Low = 0
        var High = nameList.size
        val Result = random.nextInt(High - Low) + Low
        val randomName = nameList[Result]

        val questionList: ArrayList<String> = ArrayList()

        questionList.add("\" $randomName \"، جذاب ترین بازیکن رو انتخاب کن،بگو ۲ قلپ بره بالا. ")
        questionList.add("\" $randomName \"، اگر تاحالا رفتی سر قرار اینترنتی یکی رو اعلام کن ۲ قلپ بره بالا، اگر نه خودت ۲ قلپ بزن. ")
        questionList.add("اونی که برای مدت طولانی تر سینگله اعلام کنه بقیه ۳ قلپ برن بالا.")

        val randomValueQuest = random.nextInt(questionList.size)
        val randomQuest = questionList[randomValueQuest]
        txtLoveQuest1.text = randomQuest

        var touchCount = 0
        socialFirst.setOnClickListener{
            touchCount  = touchCount  + 1;
            if (touchCount  == 2){
                touchCount=0;
                val intent =  Intent(this,Game1Activity::class.java)
                intent.putExtra(LOVEQUEST1, nameList)
                intent.putExtra(Virus1Activy.RANDOMNAME1, nameVirus1)
                intent.putExtra(Virus1Activy.RANDOMQUEST1, questVirus1)
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
