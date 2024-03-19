package com.zibfit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.zibfit.Game2Activity.Companion.GAME2
import kotlinx.android.synthetic.main.activity_question2.*
import java.util.*
import kotlin.collections.ArrayList

class Question2Activity : AppCompatActivity() {

    companion object {
        const val QUEST2 = "names"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question2)
        val nameList = intent.getStringArrayListExtra(GAME2)
        val nameVirus1 = intent.getStringExtra(Virus1Activy.RANDOMNAME1)
        val questVirus1 = intent.getStringExtra(Virus1Activy.RANDOMQUEST1)
        val random = Random()
        val Low = 0
        var High = nameList.size
        val Result = random.nextInt(High - Low) + Low
        val randomName = nameList[Result]

        val questionList: ArrayList<String> = ArrayList()

        questionList.add("\" $randomName \"، میتونی به تعداد دایی هات افرادی رو انتخاب کنی که ۲ قلپ برن بالا. ")
        questionList.add("\" $randomName \"، اگر  تا حالا به کسی سنت رو دروغ گفتی  اسم یکی رو بگو که ۲ قلپ بره بالا وگرنه خودت ۲ قلپ بزن. ")
        questionList.add("\" $randomName \"، یکی باهوش تر از خودت انتخاب کن که ۲ قلپ بره بالا… اگر کسی از تو باهوش تر نیست خودت ۲ قلپ بزن. ")


        val randomValueQuest = random.nextInt(questionList.size)
        val randomQuest = questionList[randomValueQuest]
        txtQuest2.text = randomQuest

        var touchCount = 0
        question2.setOnClickListener{
            touchCount  = touchCount  + 1;
            if (touchCount  == 2){
                touchCount=0;
                val intent =  Intent(this,Cure1Activity::class.java)
                intent.putExtra(QUEST2, nameList)
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