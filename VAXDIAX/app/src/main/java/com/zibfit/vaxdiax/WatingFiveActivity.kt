package com.zibfit.vaxdiax

import android.content.Context
import android.content.Intent
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_wating_five.*
import kotlinx.android.synthetic.main.toolbar_open_kit.*
import java.util.*
import java.util.concurrent.TimeUnit

private lateinit var backToast: Toast

class WatingFiveActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wating_five)
        waitingFiveLottie.setAnimation(R.raw.anim2_loading)

        home_toolbar_open_kit.setOnClickListener {
            startActivity(Intent(this, SplashActivity::class.java))
            overridePendingTransition(R.anim.left_in, R.anim.right_out)
            finish()
        }


        btnNexWatingFive.setOnClickListener {
            startActivity(Intent(this, ScanPlateActivity::class.java))
            finish()
        }
        textView21.setOnClickListener {
            btnNexWatingFive.isEnabled = true
            activeTimeLeftFive.visibility = View.GONE
            doneImage.visibility = View.VISIBLE
            txtUndertik.visibility = View.VISIBLE
            imgShowTik.visibility = View.VISIBLE
            shakeItBaby(this@WatingFiveActivity)

        }

        //300000 Changeeeeeeeee

        val timer = object : CountDownTimer(600000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                //    textView.setText("00: " + millisUntilFinished / 1000)
//format for minute  ----->  "Time Remaining %02d min: %02d sec"
                val text: String = java.lang.String.format(
                    Locale.getDefault(), " %02d : %02d",
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60,
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60
                )
                activeTimeLeftFive.setText(text)

            }

            override fun onFinish() {
                btnNexWatingFive.isEnabled = true
                activeTimeLeftFive.visibility = View.GONE
                doneImage.visibility = View.VISIBLE
                txtUndertik.visibility = View.VISIBLE
                imgShowTik.visibility = View.VISIBLE
                shakeItBaby(this@WatingFiveActivity)

            }
        }
        timer.start()

    }

    private fun shakeItBaby(context: Context) {
        if (Build.VERSION.SDK_INT >= 26) {
            (context.getSystemService(VIBRATOR_SERVICE) as Vibrator).vibrate(
                VibrationEffect.createOneShot(
                    500,
                    VibrationEffect.EFFECT_TICK
                )
            )
        } else {
            (context.getSystemService(VIBRATOR_SERVICE) as Vibrator).vibrate(500)
        }
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


