package com.zibfit

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import com.zibfit.Game5Activity.Companion.GAME5
import kotlinx.android.synthetic.main.activity_virus3.*
import java.util.*
import kotlin.collections.ArrayList

class Virus3Activity : AppCompatActivity() {
    companion object {
        const val VIRUS3 = "names"
        const val RANDOMNAME3= "randomName"
        const val RANDOMQUEST3 = "randomQuest"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_virus3)

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

        val nameList = intent.getStringArrayListExtra(GAME5)

        val random = Random()
        val Low = 0
        var High = nameList.size
        val Result = random.nextInt(High - Low) + Low
        val randomName = nameList[Result]

        val questionList: ArrayList<String> = ArrayList()
        questionList.add("خلاق باشین ،هر کس یه لقب باید به خودش بده (مثلا شاه ماهی،پدر دنس ایران..) بقیه باید اونو با او لقب صدا کنن هر کس اشتباه صدا کنه باید ۲ قلپ بره بالا.")
        questionList.add("\" $randomName \"، تو نقش یک قهرمان رو  داری، هر وقت یک بازیکن اسم کسی رو اعلام کنه که بنوشه، اینجاست که قهرمان وارد میشه و میتونه اعلام کننده رو مجبور به نوشیدن کنه . ")
        questionList.add("\" $randomName \"، تو عروسک گردانی، دستتو بکن تو یه جوراب و هر وقت خواستی حرف بزنی باید صداتو تغییر بدی و با اون حرف بزنی. هر وقت یادت بره باید ۱ قلپ نوش جان کنی. ")

        val randomValueQuest = random.nextInt(questionList.size)
        val randomQuest = questionList[randomValueQuest]
        txtVirus2.text = randomQuest

        var touchCount = 0
        virus3.setOnClickListener{
            touchCount  = touchCount  + 1;
            if (touchCount  == 2){
                touchCount=0;
                val intent =  Intent(this,Question5Activity::class.java)
                intent.putExtra(VIRUS3, nameList)
                intent.putExtra(RANDOMNAME3, randomName)
                intent.putExtra(RANDOMQUEST3, randomQuest)
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
