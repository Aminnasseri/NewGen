package com.zibfit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.zibfit.Game6Activity.Companion.GAME6
import kotlinx.android.synthetic.main.activity_love3.*
import java.util.*
import kotlin.collections.ArrayList

class Love3Activity : AppCompatActivity() {
    companion object {
        const val LOVE3 = "names"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_love3)

        val nameList = intent.getStringArrayListExtra(GAME6)
        val nameVirus3 = intent.getStringExtra(Virus3Activity.RANDOMNAME3)
        val questVirus3 = intent.getStringExtra(Virus3Activity.RANDOMQUEST3)
        val random = Random()
        val Low = 0
        var High = nameList.size
        val Result = random.nextInt(High - Low) + Low
        val randomName = nameList[Result]

        val questionList: ArrayList<String> = ArrayList()
        questionList.add("\" $randomName \"، یک دروغ که توی رابطت گفتی رو تعریف کن،یا ۶ قلپ رو خودت انجام بده. ")
        questionList.add("\" $randomName \"، اصلی ترین چیزی که توی جنس مخالف برای تو جذابه چیه؟ یا جواب بده یا ۵ قلپ بخور. ")
        questionList.add("\" $randomName \"، اگر مخاطب خاص تو، کیس ازدواج هست؟ ۲ قلپ بخور اگه نه ۴ قلپ. ")


        val randomValueQuest = random.nextInt(questionList.size)
        val randomQuest = questionList[randomValueQuest]
        txtLove3.text = randomQuest

        var touchCount = 0
        love3.setOnClickListener{
            touchCount  = touchCount  + 1;
            if (touchCount  == 2){
                touchCount=0;
                val intent =  Intent(this,Game7Activity::class.java)
                intent.putExtra(LOVE3, nameList)
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