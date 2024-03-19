package com.google.firebase.ml.laghari

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.google.firebase.ml.laghari.MainActivity.Companion.AGE
import com.google.firebase.ml.laghari.MainActivity.Companion.BMI
import com.google.firebase.ml.laghari.MainActivity.Companion.BMR
import com.google.firebase.ml.laghari.MainActivity.Companion.EMAIL
import com.google.firebase.ml.laghari.MainActivity.Companion.GENDER
import com.google.firebase.ml.laghari.MainActivity.Companion.GOALWEIGHT
import com.google.firebase.ml.laghari.MainActivity.Companion.HEIGHT
import com.google.firebase.ml.laghari.MainActivity.Companion.NAME
import com.google.firebase.ml.laghari.MainActivity.Companion.WEIGHT
import com.google.firebase.ml.laghari.SplashScreen.Companion.COUNTRY
import kotlinx.android.synthetic.main.activity_result.*
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.*

class ResultActivity : AppCompatActivity() {
    var rBmi :Double? = null
    var rBmr :Double? = null
    var rGoal :Double? = null
    var token :String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
//        val localeFromDevice: String = this.getResources().getConfiguration().locale.getCountry()
//        if (localeFromDevice=="IR"){
//            val locale = Locale("fa")
//            Locale.setDefault(locale)
//            val config = Configuration()
//            config.locale = locale
//            baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
//        }
      //  Log.d("Amin","Country: $locale")

        val weight = intent.getStringExtra(WEIGHT)
        val height = intent.getStringExtra(HEIGHT)
        val email = intent.getStringExtra(EMAIL)
        val country = intent.getStringExtra(COUNTRY)
        val age = intent.getStringExtra(AGE)
        val bmi = intent.getStringExtra(BMI).toDouble()
        val bmr = intent.getStringExtra(BMR).toDouble()
        val goalWeight = intent.getStringExtra(GOALWEIGHT).toDouble()
        val gender = intent.getStringExtra(GENDER)
       //  token = intent.getStringExtra(TOKEN)
        val name = intent.getStringExtra(NAME)

        val font1 = ResourcesCompat.getFont(this, R.font.iranyekanwebregular)
        val font2 = ResourcesCompat.getFont(this, R.font.iranyekanwebbold)
        val fontNumRegular = ResourcesCompat.getFont(this, R.font.iranyekanwebregularnum)
        val fontNumBold = ResourcesCompat.getFont(this, R.font.iranyekanwebboldnum)
            //Round 1 decimal
        val dec = DecimalFormat("####.#")
        dec.roundingMode = RoundingMode.CEILING
        val nbmi = dec.format(bmi)
        val nbmr = dec.format(bmr)
        val nGoal = dec.format(goalWeight)

        if (country=="Iran"){

            val locale = Locale("fa")
            Locale.setDefault(locale)
            val config = Configuration()
            config.locale = locale
            baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
        }
        val localLang= Resources.getSystem().getConfiguration().locale.getLanguage()
        val currentLang: String = Locale.getDefault().getLanguage()

        if (currentLang == "fa" ){
            titleName.text = "نام"
            titleGoalWeight.text = "هدف"
            title_Goal_Weight.text = "وزن هدف"
            titleAge.text = "سن"
            titleWeight.text = "وزن"
            titleCurrent.text = "وزن فعلی"
            titleHeight.text = "قد"



            txtBmi.setTypeface(fontNumBold)
            txtAge.setTypeface(fontNumRegular)
            txtHeight.setTypeface(fontNumRegular)
            txtWeight.setTypeface(fontNumRegular)
            txtCurrent.setTypeface(fontNumRegular)
            txtGoalButtom.setTypeface(fontNumRegular)
            txtGoal.setTypeface(fontNumBold)
            txtBmr.setTypeface(fontNumBold)

            titleName.setTypeface(font1)
            titleGoalWeight.setTypeface(font1)
            title_Goal_Weight.setTypeface(font1)
            titleAge.setTypeface(font1)
            titleWeight.setTypeface(font1)
            titleHeight.setTypeface(font1)
            titleCurrent.setTypeface(font1)




        }

        Log.d("langoo","lang = $localLang , and app is : $currentLang")




        txtName.text = name
        txtAge.text = age
        txtWeight.text = weight
        txtBmi.text = nbmi.toString()
        txtGoal.text = nGoal.toString()
        txtHeight.text = height
        txtBmr.text = nbmr.toString()
        txtGoalButtom.text = nGoal.toString()
        txtCurrent.text = weight

        if (gender == "female"){
            profilePic.setImageResource(R.drawable.person_2)
        }else{
            profilePic.setImageResource(R.drawable.person)

        }

    }

    fun setlocate(lang: String) {
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)

        val editor = getSharedPreferences("Settings", Context.MODE_PRIVATE).edit()
        editor.putString("My_Lang", lang)
        editor.apply()


    }

    fun loadlocate() {
        val sharedPreferences = getSharedPreferences("Settings", Activity.MODE_PRIVATE)
        val language = sharedPreferences.getString("My_Lang", "")
        if (language != null) {
            setlocate(language)
        }
    }


}
