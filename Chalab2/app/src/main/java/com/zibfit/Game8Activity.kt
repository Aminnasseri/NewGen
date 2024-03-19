package com.zibfit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.zibfit.Cure3Activity.Companion.CURE3
import kotlinx.android.synthetic.main.activity_game8.*
import java.util.*
import kotlin.collections.ArrayList

class Game8Activity : AppCompatActivity() {
    companion object {
        const val GAME8 = "names"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game8)

        val nameList = intent.getStringArrayListExtra(CURE3)
        val random = Random()
        val Low = 0
        var High = nameList.size
        val Result = random.nextInt(High - Low) + Low
        val randomName = nameList[Result]

        val questionList: ArrayList<String> = ArrayList()
        questionList.add("اسم کشورهای (آمریکای جنوبی) رو بگین اولین کسی که نتونه ۳ قلپ . \" $randomName \" تو استارتشو بزن.")
        questionList.add("کارهایی که با (ج) شروع میشه رو بگین هر کس نتونه بگه باید ۳ قلپ بزن. \" $randomName \" تو استارتشو بزن.")
        questionList.add("مرگ های پایانی یک فیلم رو تعریف کنین،اولین نفری که نتونه باید۳ قلپ بنوشه. \" $randomName \" تو شروع کن ")


        val randomValueQuest = random.nextInt(questionList.size)
        val randomQuest = questionList[randomValueQuest]
        txtGame8.text = randomQuest

        var touchCount = 0
        game8.setOnClickListener{
            touchCount  = touchCount  + 1;
            if (touchCount  == 2){
                touchCount=0;
                val intent =  Intent(this,FinishGameActivity::class.java)
                intent.putExtra(GAME8, nameList)
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