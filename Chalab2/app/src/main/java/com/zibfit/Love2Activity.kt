package com.zibfit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import com.zibfit.Question4Activity.Companion.QUEST4
import kotlinx.android.synthetic.main.activity_love2.*
import java.util.*
import kotlin.collections.ArrayList

class Love2Activity : AppCompatActivity() {

    companion object {
        const val LOVE2 = "names"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_love2)
        val nameList = intent.getStringArrayListExtra(QUEST4)
        val nameVirus2 = intent.getStringExtra(Virus2Activity.RANDOMNAME2)
        val questVirus2 = intent.getStringExtra(Virus2Activity.RANDOMQUEST2)
        Log.d("VIRUS2","Esm ine : $nameVirus2 va quest ine :$questVirus2")

        val random = Random()
        val Low = 0
        var High = nameList.size
        val Result = random.nextInt(High - Low) + Low
        val randomName = nameList[Result]

        val questionList: ArrayList<String> = ArrayList()

        questionList.add("\" $randomName \"، به اندازه تعداد بازیکنا برو بالا.")
        questionList.add("\" $randomName \"، اگه تا حالا خیانت کردی ۳ قلپ برو بالا،اگر نه اونی که فک میکنی خیانت کار تره رو اعلام کن ۴ قلپ بره بالا.")
        questionList.add("\" $randomName \"، اولین تجربه عشقیتو تعریف کن یا ۵ قلپ رو خودت زحمتش رو بکش.")


        val randomValueQuest = random.nextInt(questionList.size)
        val randomQuest = questionList[randomValueQuest]
        txtLove2.text = randomQuest

        var touchCount = 0
        love2.setOnClickListener{
            touchCount  = touchCount  + 1;
            if (touchCount  == 2){
                touchCount=0;
                val intent =  Intent(this,Game4Activity::class.java)
                intent.putExtra(LOVE2, nameList)
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
