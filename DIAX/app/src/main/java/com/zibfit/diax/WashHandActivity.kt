package com.zibfit.diax


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_wash_hand.*
import kotlinx.android.synthetic.main.toolbar_open_kit.*
import java.util.*
import java.util.concurrent.TimeUnit

private lateinit var backToast: Toast

class WashHandActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wash_hand)
        home_toolbar_open_kit.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    MainActivity::class.java
                )
            )
            overridePendingTransition(R.anim.left_in, R.anim.right_out)
            finish()
        }
        home_toolbar_open_kit.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    MainActivity::class.java
                )
            )
        }
        washHand.setAnimation(R.raw.washing)
        btnNextWash.setOnClickListener {
            startActivity(Intent(this, TakeBloodActivity::class.java))
        }


        val timer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                //    textView.setText("00: " + millisUntilFinished / 1000)
//format for minute  ----->  "Time Remaining %02d min: %02d sec"
                val text: String = java.lang.String.format(
                    Locale.getDefault(), "Active Button in: %02d sec",
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60
                )
                activeTimeLeft.setText(text)

            }

            override fun onFinish() {
                btnNextWash.isEnabled = true
                activeTimeLeft.visibility = View.GONE


            }
        }
        timer.start()

    }

    private val TIME_INTERVAL =
        2000 // # milliseconds, desired time passed between two back presses.

    private var mBackPressed: Long = 0

    override fun onBackPressed() {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            backToast.cancel()
            super.onBackPressed()
            return
        } else {
            backToast =
                Toast.makeText(baseContext, "Tap back button in order to exit", Toast.LENGTH_SHORT)
            backToast.show()
        }
        mBackPressed = System.currentTimeMillis()
    }

}


//Format for Minute and Second


//val text: String = java.lang.String.format(
//    Locale.getDefault(), "Time Remaining %02d min: %02d sec",
//    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60,
//    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60
