package com.zibfit

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.zibfit.ExerciseQuestion1Activity.Companion.EXERCISE1
import kotlinx.android.synthetic.main.activity_question1.socialFirst
import kotlinx.android.synthetic.main.activity_virus1_activy.*
import java.util.*
import kotlin.collections.ArrayList


class Virus1Activy : AppCompatActivity() {
    companion object {
        const val VIRUS1 = "names"
        const val RANDOMNAME1 = "randomName"
        const val RANDOMQUEST1 = "randomQuest"

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_virus1_activy)

        val mp = MediaPlayer.create(this, R.raw.virus);
        mp.start()

        //Vibration
        val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
// Vibrate for 500 milliseconds
// Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            //deprecated in API 26
            v.vibrate(200)
        }


        val nameList = intent.getStringArrayListExtra(EXERCISE1)
        val random = Random()
        val Low = 0
        var High = nameList.size
        val Result = random.nextInt(High - Low) + Low
        val randomName = nameList[Result]

        val questionList: ArrayList<String> = ArrayList()

        questionList.add("\" $randomName \"، با لهجه ترکی بازی رو ادامه بده تا جلوتر درمانش بیاد. ")
        questionList.add("\" $randomName \"، هر وقت هر کس جملش رو تموم کرد تو باید بگی (دقیقا عزیزم) . هر بار یادت بره باید ۳ قلپ بخوری. تا وقتی درمان بیاد باید بگی. ")
        questionList.add("بیاین بی ادب باشیم! همه باید با  \" $randomName \" بی ادبانه حرف بزنن و او هم اجازه نداره جواب بده. تا وقتی درمان نیومده ادامه بدین. ")

        val randomValueQuest = random.nextInt(questionList.size)
        val randomQuest = questionList[randomValueQuest]
        txtVirus1.text = randomQuest


        var touchCount = 0
        socialFirst.setOnClickListener{
            touchCount  = touchCount  + 1;
            if (touchCount  == 2){
                touchCount=0;
                val intent =  Intent(this,LoveQusetion1Activity::class.java)
                intent.putExtra(VIRUS1, nameList)
                intent.putExtra(RANDOMNAME1, randomName)
                intent.putExtra(RANDOMQUEST1, randomQuest)

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
