package com.zibfit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.zibfit.TruthQuestion1Activity.Companion.TRUTH1
import kotlinx.android.synthetic.main.activity_game2.*
import java.util.*
import kotlin.collections.ArrayList

class Game2Activity : AppCompatActivity() {

    companion object {
        const val GAME2 = "names"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game2)

        val nameList = intent.getStringArrayListExtra(TRUTH1)
        val nameVirus1 = intent.getStringExtra(Virus1Activy.RANDOMNAME1)
        val questVirus1 = intent.getStringExtra(Virus1Activy.RANDOMQUEST1)
        val random = Random()
        val Low = 0
        var High = nameList.size
        val Result = random.nextInt(High - Low) + Low
        val randomName = nameList[Result]

        val questionList: ArrayList<String> = ArrayList()

        questionList.add("چیزایی که تو اتاق بچه ها پیدا میشه به نوبت نفری یه دونه بگین کسی که من من کرد باید ۲ قلپ بره بالا .\" $randomName \" تو شروع کن.")
        questionList.add("\" $randomName \"، تو بگو (یکی بود یکی نبود …) و ۳ کلمه بهش اضافه کن،  همه باید داستان رو از اول کلمه به کلمه تعریف و ۳ تا کلمه بهش اضافه کنن اولین نفری که نتونه درست بگه باید ۳ قلپ بره بالا. ")
        questionList.add("همگی رای بدن، تا سن ۸۰ سالگی زندگی کنی اما فقیر . تا ۴۰ سالگی بیشتر عمر نکنی اما پولدار باشی، گروهی که رای کمتر داده باید ۳ قلپ بره بالا.")


        val randomValueQuest = random.nextInt(questionList.size)
        val randomQuest = questionList[randomValueQuest]
        txtGame2.text = randomQuest

        var touchCount = 0
        game2.setOnClickListener{
            touchCount  = touchCount  + 1;
            if (touchCount  == 2){
                touchCount=0;
                val intent =  Intent(this,Question2Activity::class.java)
                intent.putExtra(Virus1Activy.RANDOMNAME1, nameVirus1)
                intent.putExtra(Virus1Activy.RANDOMQUEST1, questVirus1)
                intent.putExtra(GAME2, nameList)
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