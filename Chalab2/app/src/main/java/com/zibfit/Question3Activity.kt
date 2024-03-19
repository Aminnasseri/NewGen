package com.zibfit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.zibfit.Cure1Activity.Companion.CURE1
import kotlinx.android.synthetic.main.activity_question3.*
import java.util.*
import kotlin.collections.ArrayList

class Question3Activity : AppCompatActivity() {
    companion object {
        const val QUEST3 = "names"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question3)

        val nameList = intent.getStringArrayListExtra(CURE1)
        val random = Random()
        val Low = 0
        var High = nameList.size
        val Result = random.nextInt(High - Low) + Low
        val randomName = nameList[Result]

        val questionList: ArrayList<String> = ArrayList()

        questionList.add("\" $randomName \"، تو ۵ تا عکس آخرت توی گوشیو به همه نشون بده یا ۵ قلپ بخور. ")
        questionList.add("\" $randomName \"، اگر ملافه تختتو  زودتر از ۳ ماه عوض کردی اعلام کن همه ۳ قلب بخورن وگرنه خودت بزن. ")
        questionList.add("اولین نفری که یه سلفی بگیره و توی فضای مجازی بگذازه میتونه یکی رو انتخاب کنه که نوشیدنیش رو تا ته بخوره.عکس باید تا فردا بماند.")


        val randomValueQuest = random.nextInt(questionList.size)
        val randomQuest = questionList[randomValueQuest]
        txtQuest3.text = randomQuest

        var touchCount = 0
        question3.setOnClickListener{
            touchCount  = touchCount  + 1;
            if (touchCount  == 2){
                touchCount=0;
                val intent =  Intent(this,Game3Activity::class.java)
                intent.putExtra(QUEST3, nameList)
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
