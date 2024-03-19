package com.zibfit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import com.zibfit.Virus2Activity.Companion.RANDOMNAME2
import com.zibfit.Virus2Activity.Companion.RANDOMQUEST2
import com.zibfit.Virus2Activity.Companion.VIRUS2
import kotlinx.android.synthetic.main.activity_question4.*
import java.util.*
import kotlin.collections.ArrayList

class Question4Activity : AppCompatActivity() {
    companion object {
        const val QUEST4 = "names"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question4)

        val nameList = intent.getStringArrayListExtra(VIRUS2)
        val nameVirus2 = intent.getStringExtra(RANDOMNAME2)
        val questVirus2 = intent.getStringExtra(RANDOMQUEST2)

        Log.d("VIRUS2","Esm ine : $nameVirus2 va quest ine :$questVirus2")
        val random = Random()
        val Low = 0
        var High = nameList.size
        val Result = random.nextInt(High - Low) + Low
        val randomName = nameList[Result]

        val questionList: ArrayList<String> = ArrayList()

        questionList.add("\" $randomName \"، به اندازه تعداد بازیکنا برو بالا.")
        questionList.add("\" $randomName \"، عین یه خوک دور اتاق رو بزن و صداشو در بیار  یا ۲ قلپ بنوش.")
        questionList.add("دستا بالا! آخرین بازیکنی که دستاش رفت بالا باید ۴ قلپ بخوره.")


        val randomValueQuest = random.nextInt(questionList.size)
        val randomQuest = questionList[randomValueQuest]
        txtQuest4.text = randomQuest

        var touchCount = 0
        question4.setOnClickListener{
            touchCount  = touchCount  + 1;
            if (touchCount  == 2){
                touchCount=0;
                val intent =  Intent(this,Love2Activity::class.java)
                intent.putExtra(QUEST4, nameList)
                intent.putExtra(Virus2Activity.RANDOMNAME2, nameVirus2)
                intent.putExtra(Virus2Activity.RANDOMQUEST2, questVirus2)
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
