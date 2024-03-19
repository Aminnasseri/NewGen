package com.zibfit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import com.zibfit.Love2Activity.Companion.LOVE2
import kotlinx.android.synthetic.main.activity_game4.*
import java.util.*
import kotlin.collections.ArrayList

class Game4Activity : AppCompatActivity() {
    companion object {
        const val GAME4 = "names"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game4)

        val nameList = intent.getStringArrayListExtra(LOVE2)
        val nameVirus2 = intent.getStringExtra(Virus2Activity.RANDOMNAME2)
        val questVirus2 = intent.getStringExtra(Virus2Activity.RANDOMQUEST2)
        Log.d("VIRUS2","Esm ine : $nameVirus2 va quest ine :$questVirus2")

        val random = Random()
        val Low = 0
        var High = nameList.size
        val Result = random.nextInt(High - Low) + Low
        val randomName = nameList[Result]

        val questionList: ArrayList<String> = ArrayList()

        questionList.add("اسم آهنگی که از دونستنش شرم داریم ، اولین کسی که نتونه بگه ۳ قلپ بنوشه. \" $randomName \" تو شروع کن.")
        questionList.add("\" $randomName \"، یه چیزیو که دوست داری بگو، همه بازیکنایی که اونو دوست ندارن باید ۳ قلپ بخورن .اگر کسی نخورد خودت باید ۴ قلپ بری بالا.")
        questionList.add("کدومو انتخاب میکنی، تو زندگیت، دکمه برگشت به عقب داشته باشی یا دکمه پاز ؟ همه رای بدن ، بازنده ها ۲ قلپ برن بالا.")


        val randomValueQuest = random.nextInt(questionList.size)
        val randomQuest = questionList[randomValueQuest]
        txtGame4.text = randomQuest

        var touchCount = 0
        game4.setOnClickListener{
            touchCount  = touchCount  + 1;
            if (touchCount  == 2){
                touchCount=0;
                val intent =  Intent(this,Cure2Activity::class.java)
                intent.putExtra(GAME4, nameList)
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
