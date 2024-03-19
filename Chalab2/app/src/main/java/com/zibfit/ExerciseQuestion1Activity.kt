package com.zibfit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_exercise_question1.*
import kotlinx.android.synthetic.main.activity_question1.socialFirst
import java.util.*
import kotlin.collections.ArrayList

class ExerciseQuestion1Activity : AppCompatActivity() {
    companion object {
        const val EXERCISE1 = "names"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise_question1)
        val nameList = intent.getStringArrayListExtra(Question1Activity.QUESTION1)
        val random = Random()
        val Low = 0
        var High = nameList.size
        val Result = random.nextInt(High - Low) + Low
        val randomName = nameList[Result]

        val questionList: ArrayList<String> = ArrayList()

        questionList.add("\" $randomName \"، ادای یه خزنده رو واسه ۵ ثانیه در بیار یا ۵ قلپ بخور. ")
        questionList.add("\" $randomName \"، تو ۳۰ ثانیه باید ترکی برقصی یا ۴ قلپ نوش جان کنی. ")
        questionList.add("ورزشکارا ۲ قلپ! سریع!!")
        val randomValueQuest = random.nextInt(questionList.size)
        val randomQuest = questionList[randomValueQuest]
        txtExerciseQuest1.text = randomQuest


        var touchCount = 0
        socialFirst.setOnClickListener{
            touchCount  = touchCount  + 1;
            if (touchCount  == 2){
                touchCount=0;
                val intent =  Intent(this,Virus1Activy::class.java)
                intent.putExtra(EXERCISE1, nameList)
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
