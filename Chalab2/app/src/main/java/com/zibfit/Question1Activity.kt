package com.zibfit

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.zibfit.GuideActivity.Companion.GUIDENAMES
import kotlinx.android.synthetic.main.activity_question1.*
import java.util.*
import kotlin.collections.ArrayList


class Question1Activity : AppCompatActivity() {
    companion object {
        const val QUESTION1 = "names"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question1)

        val currentapiVersion = Build.VERSION.SDK_INT


        if (currentapiVersion >= 24) {
            val timer = object : CountDownTimer(3000, 1000) {

                override fun onTick(millisUntilFinished: Long) {
                    val number3digits: Long = Math.round(millisUntilFinished / 1000.0)
                    Log.d("Amiroo", number3digits.toString())
                    Log.d("Amiroo", "mil : $millisUntilFinished.toString()")

                    txtTimer.setText("" + (number3digits));
                    Log.d("Amiroo", "Timer : $millisUntilFinished")

                }

                override fun onFinish() {
                    txtTimer.visibility = View.GONE

                    txtQuest1.visibility = View.VISIBLE


                }
            }
            timer.start()
        } else {
            progressbar.visibility = View.VISIBLE

            val mProgressBar: ProgressBar
            val mCountDownTimer: CountDownTimer
            var i = 0

            mProgressBar = findViewById(R.id.progressbar) as ProgressBar
            mProgressBar.progress = i
            mCountDownTimer = object : CountDownTimer(3000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    Log.v("Log_tag", "Tick of Progress$i$millisUntilFinished")
                    i++
                    mProgressBar.progress = i as Int * 100 / (3000 / 1000)
                    txtTimer.setTextSize(28F)
                    txtTimer.setText("درحال آماده سازی")

                }

                override fun onFinish() {
                    //Do what you want
                    i++
                    mProgressBar.progress = 100
                    txtTimer.visibility = View.GONE
                    progressbar.visibility = View.GONE

                    txtQuest1.visibility = View.VISIBLE
                }
            }
            mCountDownTimer.start()

        }


        val nameList = intent.getStringArrayListExtra(GUIDENAMES)
        val random = Random()
        val Low = 0
        var High = nameList.size
        val Result = random.nextInt(High - Low) + Low
        val randomName = nameList[Result]


        val questionList: ArrayList<String> = ArrayList()

        questionList.add("\" $randomName \"، اگر تاحالا گم شدی اعلام کن و یکی رو انتخاب کن ۳ قلپ بره بالا. ")
        questionList.add("\" $randomName \"، اگر تا الان تو یه شهر دیگه زندگی کردی اعلام کن بقیه ۲ قلپ برن بالا. ")
        questionList.add("رای بدین،کی بیشتر کلاسای دانشگاه و مدرسه رو پیچونده،۳ قلپ بره بالا تا دیگه نپیجونه.")
        val randomValueQuest = random.nextInt(questionList.size)
        val randomQuest = questionList[randomValueQuest]
        txtQuest1.text = randomQuest

//        questionList.remove(randomQuest)
//        Log.d("listAmin",questionList.toString())


        Log.d("Amin", "List is : $nameList")
        Log.d("Amin", "random is :$randomName")

        var touchCount = 0
        socialFirst.setOnClickListener {
            touchCount = touchCount + 1;
            if (touchCount == 2) {
                touchCount = 0;
                val intent = Intent(this, DareQuestion1Activity::class.java)
                intent.putExtra(QUESTION1, nameList)
                startActivity(intent);
            } else {
                imgTouch.visibility = View.VISIBLE
                txtQuest1.visibility = View.INVISIBLE
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
        Toast.makeText(
            this,
            "یه بار دیگه دکمه برگشت رو بزن تا از برنامه خارج شی",
            Toast.LENGTH_SHORT
        ).show()

        Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
    }

}
