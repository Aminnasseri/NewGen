package com.zibfit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import com.zibfit.LoveQusetion1Activity.Companion.LOVEQUEST1
import kotlinx.android.synthetic.main.activity_game1.*
import kotlinx.android.synthetic.main.activity_question1.socialFirst
import java.util.*
import kotlin.collections.ArrayList

class Game1Activity : AppCompatActivity() {
    companion object {
        const val GAME1 = "names"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game1)

        val nameList = intent.getStringArrayListExtra(LOVEQUEST1)
        val nameVirus1 = intent.getStringExtra(Virus1Activy.RANDOMNAME1)
        val questVirus1 = intent.getStringExtra(Virus1Activy.RANDOMQUEST1)
        Log.d("CURE1","in esm $nameVirus1 .....  in soal $questVirus1 ")
        val random = Random()
        val Low = 0
        var High = nameList.size
        val Result = random.nextInt(High - Low) + Low
        val randomName = nameList[Result]

        val questionList: ArrayList<String> = ArrayList()

        questionList.add("چه فکری \" $randomName \" رو میخندونه ،ایده هارو بگین هر کی غلط بگه باید ۲ قلپ بخوره .همه نفرات بجز خودش باید یکبار بگن")
        questionList.add("\" $randomName \"، تو ۳  تا تیکه از آهنگ های مختلف سیروان بخون،اگر تونستی اعلام کن همه باید ۳ قلپ بنوشند،اگر نتونستی خودت باید بری بالا. ")
        questionList.add("\" $randomName \"، اسم کوچک و سن همه بازیکنارو بگو اسم یا سن هرکسی رو غلط بگی اون میتونه اعلام کنه کیا ۲ قلپ برن بالا. ")


        val randomValueQuest = random.nextInt(questionList.size)
        val randomQuest = questionList[randomValueQuest]
        txtGame1.text = randomQuest

        var touchCount = 0
        socialFirst.setOnClickListener{
            touchCount  = touchCount  + 1;
            if (touchCount  == 2){
                touchCount=0;
                val intent =  Intent(this,TruthQuestion1Activity::class.java)
                intent.putExtra(GAME1, nameList)
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
