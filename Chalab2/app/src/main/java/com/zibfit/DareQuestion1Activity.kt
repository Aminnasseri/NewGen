package com.zibfit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.zibfit.Question1Activity.Companion.QUESTION1
import kotlinx.android.synthetic.main.activity_dare_question1.*
import kotlinx.android.synthetic.main.activity_question1.socialFirst
import java.util.*
import kotlin.collections.ArrayList

class DareQuestion1Activity : AppCompatActivity() {
    companion object {
        const val QUESTION2 = "names"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dare_question1)
        val nameList = intent.getStringArrayListExtra(QUESTION1)
        val random = Random()
        val Low = 0
        var High = nameList.size
        val Result = random.nextInt(High - Low) + Low
        val randomName = nameList[Result]

        val questionList: ArrayList<String> = ArrayList()

        questionList.add("\" $randomName \"، پاشو  ۳۰ ثانیه بندری برقص یا ۳ قلپ برو بالا. ")
        questionList.add("\" $randomName \"، کیو از همه کمتر میشناسی بگو ۲ قلپ بره بالا. ")
        questionList.add("\" $randomName \"، لباسی که الان پوشیدی هر کدومش رو اگر کمتر از ۱ ماهه خریدی ۳ قلپ برو بالا وگرنه یکی رو اعلام کن که ۳ قلپ بره بالا. ")
        val randomValueQuest = random.nextInt(questionList.size)
        val randomQuest = questionList[randomValueQuest]
        txtDareQuest1.text = randomQuest


        var touchCount = 0
        socialFirst.setOnClickListener{
            touchCount  = touchCount  + 1;
            if (touchCount  == 2){
                touchCount=0;
                val intent =  Intent(this,ExerciseQuestion1Activity::class.java)
                intent.putExtra(QUESTION2, nameList)
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
