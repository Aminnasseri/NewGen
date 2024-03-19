package com.zibfit.diax

import android.content.Intent
import android.os.Bundle
import android.text.format.DateFormat
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_history_result.*
import kotlinx.android.synthetic.main.activity_result.*
import kotlinx.android.synthetic.main.toolbar_open_kit.*
import java.util.*
private lateinit var backToast: Toast

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        home_toolbar_open_kit.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            overridePendingTransition(R.anim.left_in, R.anim.right_out)
            finish()        }

//        val ServerSharedPreferences =
//            getSharedPreferences(ImageServerActivity.SERVER, Context.MODE_PRIVATE)
//        val qr = ServerSharedPreferences.getString(ImageServerActivity.QRRES, "CN-CAA011943")
//        val timeStamp = ServerSharedPreferences.getInt(ImageServerActivity.TIMESERVER,-1)
//        val confidence = ServerSharedPreferences.getInt(ImageServerActivity.CONFIDENCE, 80)
//        val result = ServerSharedPreferences.getInt(ImageServerActivity.RESULT, 1)

        val qrcode = intent.getStringExtra("qr")
        val timeServer = intent.getStringExtra("time")
        val result = intent.getStringExtra("result")?.toInt()
        val confidence = intent.getStringExtra("confidence")
        val testNumber = intent.getStringExtra("test")


        val currentDateTimeString =
            java.text.DateFormat.getDateTimeInstance().format(Date())

// textView is the TextView view that should display it
        // textView is the TextView view that should display it
    //    txtDateAndTimeRes.setText(currentDateTimeString)

        val time = getDate(timeServer!!.toLong())
        txtDateAndTimeRes.setText(time)

        qrRes.setText("$testNumber")




        if (result == 1) {
            // imgResultFinalH.setImageDrawable(getDrawable(R.drawable.pos_inrec))
            txtRes.setText("This test has sensitivity of 99.8%")
            btnRes2.setText("Positive in Recovery")
            btnRes2.setBackgroundColor( ContextCompat.getColor(this, R.color.pos_in_rec_col));
            imgPassHealth.setImageDrawable(getDrawable(R.drawable.reject_logo))

            //txtRes.setText("This test has sensitivity of ${confidence.toString()} %  Please contact your local doctor.")
        } else if (result == 0) {
            //  imgResultFinalH.setImageDrawable(getDrawable(R.drawable.negative))
            txtRes.setText("This test has sensitivity of 99.8%")
            btnRes2.setText("Negative")
            btnRes2.setBackgroundColor( ContextCompat.getColor(this, R.color.neg_col));
            imgPassHealth.setImageDrawable(getDrawable(R.drawable.approved_logo))

            //  txtRes.setText("This test has sensitivity of ${confidence.toString()} %")
        }else if (result == 2) {
            //  imgResultFinalH.setImageDrawable(getDrawable(R.drawable.recovered))
            txtRes.setText("This test has sensitivity of 99.8%")
            btnRes2.setText("Recovery")
            btnRes2.setBackgroundColor( ContextCompat.getColor(this, R.color.rec_col));
            imgPassHealth.setImageDrawable(getDrawable(R.drawable.approved_logo))

            //  txtRes.setText("This test has sensitivity of ${confidence.toString()} %")
        }


    }

    fun getDate(timestamp: Long): String {
        val calendar = Calendar.getInstance(Locale.ENGLISH)
        calendar.timeInMillis = timestamp * 1000L
        val date = DateFormat.format("EEE, d MMM yyyy HH:mm:ss", calendar).toString()
        return date
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
