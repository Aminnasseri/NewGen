package com.zibfit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.zibfit.Question5Activity.Companion.QUEST5
import kotlinx.android.synthetic.main.activity_game6.*
import java.util.*
import kotlin.collections.ArrayList

class Game6Activity : AppCompatActivity() {
    companion object {
        const val GAME6 = "names"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game6)
        val nameVirus3 = intent.getStringExtra(Virus3Activity.RANDOMNAME3)
        val questVirus3 = intent.getStringExtra(Virus3Activity.RANDOMQUEST3)

        val nameList = intent.getStringArrayListExtra(QUEST5)
        val random = Random()
        val Low = 0
        var High = nameList.size
        val Result = random.nextInt(High - Low) + Low
        val randomName = nameList[Result]

        val questionList: ArrayList<String> = ArrayList()
        questionList.add("رای گیری کنین، طرفدار رئالی یا بارسا تیم کمتر ۵ قلپ بره بالا.")
        questionList.add("رای گیری کنین، طرفدار  پرسپولیسی یا استقلال تیم کمتر ۵ قلپ بره بالا.")
        questionList.add("کسی که بازی چال آبو به شما معرفی کرده به بقیه اعلام کنه ۴ قلپ نوش جان کنن.")

        val randomValueQuest = random.nextInt(questionList.size)
        val randomQuest = questionList[randomValueQuest]
        txtGame6.text = randomQuest

        var touchCount = 0
        game6.setOnClickListener{
            touchCount  = touchCount  + 1;
            if (touchCount  == 2){
                touchCount=0;
                val intent =  Intent(this,Love3Activity::class.java)
                intent.putExtra(GAME6, nameList)
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
