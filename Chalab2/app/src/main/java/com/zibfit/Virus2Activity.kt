package com.zibfit

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import com.zibfit.Game3Activity.Companion.GAME3
import kotlinx.android.synthetic.main.activity_virus2.*
import java.util.*
import kotlin.collections.ArrayList

class Virus2Activity : AppCompatActivity() {
    companion object {
        const val VIRUS2 = "names"
        const val RANDOMNAME2 = "randomName"
        const val RANDOMQUEST2 = "randomQuest"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_virus2)

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

        val nameList = intent.getStringArrayListExtra(GAME3)
        val random = Random()
        val Low = 0
        var High = nameList.size
        val Result = random.nextInt(High - Low) + Low
        val randomName = nameList[Result]

        val questionList: ArrayList<String> = ArrayList()

        questionList.add("\" $randomName \"،  تو الان یک سیاه چاله ای ! هرکس مجبور باشه بنوشه تو باید بجاش بری بالا.")
        questionList.add("\" $randomName \"، از این لحظه باید هر کلمه‌ای که میگی رو دو بار تکرار کنی. هر بار فراموش کنی باید ۳ قلپ ناقابل بنوشی عزیزم..")
        questionList.add("از این لحظه با اون دستی که مینویسین نمیتونین لیوان رو بردارین هر کس اشتباه کنه باید ۲ قلپ بزنه به بدن.")


        val randomValueQuest = random.nextInt(questionList.size)
        val randomQuest = questionList[randomValueQuest]
        txtVirus2.text = randomQuest

        var touchCount = 0
        virus2.setOnClickListener {
            touchCount = touchCount + 1;
            if (touchCount == 2) {
                touchCount = 0;
                val intent = Intent(this, Question4Activity::class.java)
                intent.putExtra(VIRUS2, nameList)
                intent.putExtra(RANDOMNAME2, randomName)
                intent.putExtra(RANDOMQUEST2, randomQuest)
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
        Toast.makeText(
            this,
            "یه بار دیگه دکمه برگشت رو بزن تا از برنامه خارج شی",
            Toast.LENGTH_SHORT
        ).show()

        Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
    }
}
