package com.zibfit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.zibfit.Game1Activity.Companion.GAME1
import kotlinx.android.synthetic.main.activity_question1.socialFirst
import kotlinx.android.synthetic.main.activity_truth_question1.*
import java.util.*
import kotlin.collections.ArrayList

class TruthQuestion1Activity : AppCompatActivity() {
    companion object {
        const val TRUTH1 = "names"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_truth_question1)

        val nameList = intent.getStringArrayListExtra(GAME1)
        val nameVirus1 = intent.getStringExtra(Virus1Activy.RANDOMNAME1)
        val questVirus1 = intent.getStringExtra(Virus1Activy.RANDOMQUEST1)
        val random = Random()
        val Low = 0
        var High = nameList.size
        val Result = random.nextInt(High - Low) + Low
        val randomName = nameList[Result]

        val questionList: ArrayList<String> = ArrayList()


        questionList.add("رای گیری کنین کی از همه بیشتر کارهاشو عقب میندازه اون ۵ قلپ بره بالا")
        questionList.add("\" $randomName \"، بازیکن کم هوش تر از خودت رو اعلام کن که ۴ قلپ بخوره ، اگه کسی نیست خودت ۴ قلپ برو بالا. ")
        questionList.add("هرکس گوشیش رو میزه باید ۲ قلپ بره بالا.")


        val randomValueQuest = random.nextInt(questionList.size)
        val randomQuest = questionList[randomValueQuest]
        txtTruthQuest1.text = randomQuest

        var touchCount = 0
        socialFirst.setOnClickListener{
            touchCount  = touchCount  + 1;
            if (touchCount  == 2){
                touchCount=0;
                val intent =  Intent(this,Game2Activity::class.java)
                intent.putExtra(TRUTH1, nameList)
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
