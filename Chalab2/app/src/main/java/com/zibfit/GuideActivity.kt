package com.zibfit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_guide.*

class GuideActivity : AppCompatActivity() {
    companion object {
        const val GUIDENAMES = "names"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide)
        val nameList = intent.getStringArrayListExtra(SelectPlayerActivity.ARRAYLISTNAMES)
        val arrayCount = nameList.size.toString()
        txtPlayerCount.text = "تعداد بازیکنان : $arrayCount"

        btnStartGame.setOnClickListener {
            val intent = Intent(this, Question1Activity::class.java)
            intent.putExtra(GUIDENAMES, nameList)
            startActivity(intent)
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
