package com.zibfit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.zibfit.Question3Activity.Companion.QUEST3
import kotlinx.android.synthetic.main.activity_game3.*
import java.util.*
import kotlin.collections.ArrayList

class Game3Activity : AppCompatActivity() {
    companion object {
        const val GAME3 = "names"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game3)

        val nameList = intent.getStringArrayListExtra(QUEST3)
        val random = Random()
        val Low = 0
        var High = nameList.size
        val Result = random.nextInt(High - Low) + Low
        val randomName = nameList[Result]

        val questionList: ArrayList<String> = ArrayList()

        questionList.add("\" $randomName \"، یه کلمه بگو ، اولین کسی که بتونه باهاش  یه آهنگ بخونه میتونه یکی رو انتخاب کنه که ۲ قلپ بره بالا.")
        questionList.add(" همگی باید ۲ قلپ برین بالا و آخرین پیامی که فرستادین رو بلند بخونین \" $randomName \". تو شروع کن.")
        questionList.add("همگی رای بدین ، ترجیح میدی بری بهشت یا یه بار دیگه  دنیا بیای اما یه زندگی مرفح تر و باحال تر . گروه بازنده باید ۲ قلپ بخوره.")


        val randomValueQuest = random.nextInt(questionList.size)
        val randomQuest = questionList[randomValueQuest]
        txtGame3.text = randomQuest

        var touchCount = 0
        game3.setOnClickListener{
            touchCount  = touchCount  + 1;
            if (touchCount  == 2){
                touchCount=0;
                val intent =  Intent(this,Virus2Activity::class.java)
                intent.putExtra(GAME3, nameList)
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
