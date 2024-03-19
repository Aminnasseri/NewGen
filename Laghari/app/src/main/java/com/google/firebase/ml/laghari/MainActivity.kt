package com.google.firebase.ml.laghari

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.android.volley.*
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import kotlin.collections.HashMap


class MainActivity : AppCompatActivity() {

    companion object {
        const val TAGMAIN = "MainActivity"
        const val WEIGHT = "WEIGHT"
        const val HEIGHT = "HEIGHT"
        const val AGE = "AGE"
        const val EMAIL = "EMAIL"
        const val BMR = "BMR"
        const val BMI = "BMI"
        const val GOALWEIGHT = "GOALWEIGHT"
        const val PROFILEPIC = "PROFILE"
        const val NAME = "NAME"
        const val ID = "ID"
        const val TOKEN = "TOKEN"
        const val TOKENSH = "TOKENSH"
        const val GENDER = "gender"


    }

    var genderR: String? = null
    var bmrResult: String? = null
    var idealWeight: String? = null
    var profileGender: Int? = -1
    var getEmail: String? = null
    var getAge: String? = null
    var getWeight: String? = null
    var getHeight: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //language Farsi
        val font1 = ResourcesCompat.getFont(this, R.font.iranyekanwebregular)
        val font2 = ResourcesCompat.getFont(this, R.font.iranyekanwebbold)
        val fontNumRegular = ResourcesCompat.getFont(this, R.font.iranyekanwebregularnum)
        val fontNumBold = ResourcesCompat.getFont(this, R.font.iranyekanwebboldnum)

        val country = intent.getStringExtra(SplashScreen.COUNTRY)
        Log.d("TAGMAIN","$country.toString()")

        if (country=="Iran"){
            val locale = Locale("fa")
            Locale.setDefault(locale)
            val config = Configuration()
            config.locale = locale
            baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)

            edtFullName.hint = "پست الکترونیکی"
            edtFullName.run { edtFullName.setTypeface(font1) }

            edtDateOfBirth.hint = "سن"
            edtFullName.run { edtDateOfBirth.setTypeface(font1) }

            edtEmailAddress.hint = "وزن/کیلوگرم"
            edtFullName.run { edtEmailAddress.setTypeface(font1) }

            edtHeight.hint = "قد/سانتیمتر"
            edtFullName.run { edtHeight .setTypeface(font1) }

            btnStart.text = "شروع کن"
            edtFullName.run { btnStart .setTypeface(font2) }


        }


        val genderS = arrayOf("male", "female")
        val arrayAdapter =
            ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, genderS)
        spinner.adapter = arrayAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                genderR = genderS[position]
            }

        }


        btnStart.setOnClickListener {
            btnStart.isEnabled = false

            getEmail = email.text.toString()
            getAge = date_of_birth.text.toString()
            getWeight = weight.text.toString()
            //     val getGender = gender.text.toString()
            getHeight = height.text.toString()

            if (getEmail!!.isEmpty()) {
                email.setError("Email is empty")
                btnStart.isEnabled = true
            } else if (getAge!!.isEmpty()) {
                date_of_birth.setError("Age is empty")
                btnStart.isEnabled = true

            } else if (getWeight!!.isEmpty()) {
                weight.setError("Weight is empty")
                btnStart.isEnabled = true

            }
//            else if (getGender.isEmpty()) {
//                gender.setError("Gender is empty")}
            else if (getHeight!!.isEmpty()) {
                height.setError("Height is empty")
                btnStart.isEnabled = true

            } else {
                jsonRequest()
                progressBarM.visibility = View.VISIBLE


            }
        }

    }

    fun jsonRequest() {
        val deviceID = Settings.Secure.getString(
            this.getContentResolver(),
            Settings.Secure.ANDROID_ID
        )

        val queue = Volley.newRequestQueue(this)
//for POST requests, only the following line should be changed to

//for POST requests, only the following line should be changed to
        val sr: StringRequest =
            object : StringRequest(
                Method.POST, "https://laghary.herokuapp.com/api/v1/user/register",
                object : Response.Listener<String?> {
                    override fun onResponse(response: String?) {
                        val intent = Intent(this@MainActivity, ResultActivity::class.java)

                        progressBarM.visibility = View.GONE
                        //Toast.makeText(this@MainActivity,response.toString(),Toast.LENGTH_LONG).show()
                        try {
                            val jObject = JSONObject(response)
                            val user = JSONObject(jObject["user"].toString())
                            val token = jObject["token"].toString()
                            val bmi = user["bmi"].toString()
                            val bmr = user["bmr"].toString()
                            val goalWeight = user["goalWeight"].toString()
                            val name = user["name"].toString()
                            val weight = user["currentWeight"].toString()
                            val height = user["height"].toString()
                            val age = user["age"].toString()
                            val email = user["email"].toString()
                            val gender = user["gender"].toString()

                            //SharedPrefrences
                            val editor = getSharedPreferences(TAGMAIN, Context.MODE_PRIVATE).edit()
                            editor.putString(TOKENSH, token)
                            editor.apply()



                            intent.putExtra(MainActivity.WEIGHT, weight)
                            intent.putExtra(MainActivity.HEIGHT, height)
                            intent.putExtra(MainActivity.AGE, age)
                            intent.putExtra(MainActivity.EMAIL, email)
                            intent.putExtra(MainActivity.GOALWEIGHT, goalWeight)
                            intent.putExtra(MainActivity.BMR, bmr)
                            intent.putExtra(MainActivity.BMI, bmi)
                            intent.putExtra(MainActivity.NAME, name)
                            intent.putExtra(MainActivity.GENDER, gender)
                           // intent.putExtra(MainActivity.TOKEN, token)
                            Log.d(TAGMAIN,"Result : bmi:$bmi , bmr:$bmr , name : $name , gender:$gender , token:$token")

                            startActivity(intent)

                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }


//
                    }

                },
                object : Response.ErrorListener {
                    override fun onErrorResponse(error: VolleyError?) {

                        progressBarM.visibility = View.GONE
                        btnStart.isEnabled = true
                        if (error is TimeoutError || error is NoConnectionError) {

                            Toast.makeText(
                                this@MainActivity,
                                "Connection error",
                                Toast.LENGTH_LONG
                            ).show()
                        } else if (error is AuthFailureError) {
                            Toast.makeText(
                                this@MainActivity,
                                "AuthFailureError",
                                Toast.LENGTH_LONG
                            ).show()
                        } else if (error is ServerError) {
                            Toast.makeText(
                                this@MainActivity,
                                "ServerError",

                                Toast.LENGTH_LONG
                            ).show()
                        } else if (error is NetworkError) {
                            Toast.makeText(
                                this@MainActivity,
                                "NetworkError",
                                Toast.LENGTH_LONG
                            ).show()
                        } else if (error is ParseError) {
                            Toast.makeText(
                                this@MainActivity,
                                "ParseError",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }) {
                override fun getParams(): Map<String, String> {
                    val params: MutableMap<String, String> =
                        HashMap()
                    params["email"] = "$getEmail"
                    params["age"] = "$getAge"
                    params["gender"] = "$genderR"
                    params["height"] = "$getHeight"
                    params["weight"] = "$getWeight"
                    params["deviceID"] = "$deviceID"
                    return params
                }

                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val params: MutableMap<String, String> =
                        HashMap()
                    params["Content-Type"] = "application/x-www-form-urlencoded"
                    return params
                }
            }
        queue.add(sr)

    }
    override fun onRestart() {
        super.onRestart()
        finish()
        startActivity(intent)
    }


}

