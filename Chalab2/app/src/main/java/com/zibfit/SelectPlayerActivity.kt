package com.zibfit

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.android.synthetic.main.activity_select_player.*
import smartdevelop.ir.eram.showcaseviewlib.GuideView
import smartdevelop.ir.eram.showcaseviewlib.config.DismissType
import smartdevelop.ir.eram.showcaseviewlib.config.Gravity


class SelectPlayerActivity : AppCompatActivity() {
    private var mFirebaseAnalytics: FirebaseAnalytics? = null

    companion object {
        const val ARRAYLISTNAMES = "arrayListNames"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_player)
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);





        val PREFS_NAME = "MyPrefsFile"

        val settings = getSharedPreferences(PREFS_NAME, 0)

        if (settings.getBoolean("my_first_time", true)) {
            //the app is being launched for first time, do something
            Log.d("Comments", "First time")

            // first time task
            //Type Faces
            var typeFace: Typeface? = ResourcesCompat.getFont(this.applicationContext, R.font.kalameh_regular)
            var typeFace2: Typeface? = ResourcesCompat.getFont(this.applicationContext, R.font.kalameh_bold)

            //ShowCase
            GuideView.Builder(this)
                .setContentText("اول نام بازیکن را وارد کرده\n و با فشار این دکمه بازیکن ها را اضافه کرده \nو بعد از اضافه کردن همه بازیکن ها دکمه فلش را بزنید")
                .setGravity(Gravity.auto) //optional
                .setDismissType(DismissType.anywhere) //optional - default DismissType.targetView
                .setTargetView(btnAdd)
                .setContentTypeFace(typeFace2)//optional
                .setContentTextSize(16) //optional
                .setTitleTextSize(16) //optional
                .build()
                .show()

            // record the fact that the app has been started at least once
            settings.edit().putBoolean("my_first_time", false).commit()
        }





        btnAdd.setOnClickListener {
            if (edtPlayerNames.text.toString() != "") {
                val chip = Chip(this)
                val drawable = ChipDrawable.createFromAttributes(
                    this,
                    null,
                    0,
                    R.style.Widget_MaterialComponents_Chip_Entry
                )

                chip.setChipDrawable(drawable)
                chip.isCheckable = false
                chip.isClickable = false
                chip.setChipIconResource(R.drawable.hacker)
                chip.iconStartPadding = 3f
                chip.setPadding(60, 10, 60, 10)
                chip.setText(edtPlayerNames.text.toString())
                chip.setOnCloseIconClickListener {
                    chipGroup.removeView(chip)
                }
                chipGroup.addView(chip)
                edtPlayerNames.setText("")
            } else {
                Toast.makeText(this, "اسم بازیکن رو وارد کن", Toast.LENGTH_SHORT).show()
            }
        }

        button2.setOnClickListener {
//            val amin = chipGroup.getChildAt(0)
//            Toast.makeText(this,amin.toString(),Toast.LENGTH_LONG).show()

            val emails: ArrayList<String> = ArrayList()

            for (i in 0 until chipGroup.childCount) {
                val email = (chipGroup.getChildAt(i) as Chip).text.toString()
                emails.add(email)
                //Toast.makeText(this,emails.toString(),Toast.LENGTH_LONG).show()
                if (!emails.isEmpty()){
                    val intent = Intent(this, GuideActivity::class.java)
                    intent.putExtra(ARRAYLISTNAMES, emails)
                    startActivity(intent)
                 //   button2.isEnabled = false
                }else{
                    Toast.makeText(this, "حداقل باید اسم یک بازیکن رو وارد کنی", Toast.LENGTH_SHORT).show()

                }

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


