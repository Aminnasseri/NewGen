package com.zibfit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.zibfit.Virus3Activity.Companion.RANDOMNAME3
import com.zibfit.Virus3Activity.Companion.RANDOMQUEST3
import com.zibfit.Virus3Activity.Companion.VIRUS3
import kotlinx.android.synthetic.main.activity_question5.*
import java.util.*
import kotlin.collections.ArrayList

class Question5Activity : AppCompatActivity() {
    companion object {
        const val QUEST5 = "names"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question5)

        val nameList = intent.getStringArrayListExtra(VIRUS3)
        val nameVirus3 = intent.getStringExtra(RANDOMNAME3)
        val questVirus3 = intent.getStringExtra(RANDOMQUEST3)
        val random = Random()
        val Low = 0
        var High = nameList.size
        val Result = random.nextInt(High - Low) + Low
        val randomName = nameList[Result]

        val questionList: ArrayList<String> = ArrayList()
        questionList.add("هر بازیکنی که تا حالا سر میزی توی رستوران نشسته که  کسی بوده که به گارسون گفته باشه غذا افتضاح بوده ۴ قلپ بخوره و اگر اون شخص خودت بود ۱ قلپ بهش اضافه کن.")
        questionList.add("\" $randomName \"، اعلام کن اون بازیکنی که از تو بیشتر خورده باید ۵ قلپ دیگه بخوره اگر نیست خودت برو بالا. ")
        questionList.add("اگر توی فاصله بیش از ۷۰ کیلومتری اینجا به دنیا اومدی ۲ قلپ بزن.")

        val randomValueQuest = random.nextInt(questionList.size)
        val randomQuest = questionList[randomValueQuest]
        txtQuest5.text = randomQuest

        var touchCount = 0
        question5.setOnClickListener{
            touchCount  = touchCount  + 1;
            if (touchCount  == 2){
                touchCount=0;
                val intent =  Intent(this,Game6Activity::class.java)
                intent.putExtra(QUEST5, nameList)
                intent.putExtra(RANDOMNAME3, nameVirus3)
                intent.putExtra(RANDOMQUEST3, questVirus3)
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
