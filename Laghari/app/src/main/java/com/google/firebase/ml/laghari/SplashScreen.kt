package com.google.firebase.ml.laghari

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.*
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics

import com.google.firebase.ml.laghari.MainActivity.Companion.TAGMAIN
import com.google.firebase.ml.laghari.MainActivity.Companion.TOKENSH
import kotlinx.android.synthetic.main.activity_splash_screen.*
import org.json.JSONException
import org.json.JSONObject




class SplashScreen : AppCompatActivity() {
    companion object {
        const val NUMBERTOK = "numberTok"
        const val COUNTRY = "country"

    }

    var token: String? = null
    var country: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val background = object : Thread() {

            override fun run() = try {
                btnReplay.setOnClickListener {
                    val intent = Intent(this@SplashScreen, SplashScreen::class.java)
                    intent.putExtra(COUNTRY, country)
                    startActivity(intent)
                }



                IPreq()



                val sharedPreferences = getSharedPreferences(TAGMAIN, Context.MODE_PRIVATE)
                token = sharedPreferences.getString(TOKENSH, "")
                Thread.sleep(3000)
                if (token != "") {
                    jsonRequest()
                }else{

                    val intent = Intent(this@SplashScreen, MainActivity::class.java)
                    intent.putExtra(COUNTRY,country)
                    Log.d("AMINIP","country is :$country.toString()")
                    startActivity(intent)
                }


            } catch (e: Exception) {
                println(e)
            }
        }
        background.start()
    }

    fun jsonRequest() {


        val queue = Volley.newRequestQueue(this)
//for POST requests, only the following line should be changed to

//for POST requests, only the following line should be changed to
        val sr: StringRequest =
            object : StringRequest(
                Method.GET, "https://laghary.herokuapp.com/api/v1/user/profile",
                object : Response.Listener<String?> {
                    override fun onResponse(response: String?) {
                        val intent = Intent(this@SplashScreen,ResultActivity::class.java)


                            val jObject = JSONObject(response)
                            val id = jObject["id"].toString()
                            val name = jObject["name"].toString()
                            val age = jObject["age"].toString()
                            val gender = jObject["gender"].toString()
                            val currentWeight = jObject["currentWeight"].toString()
                            val height = jObject["height"].toString()
                            val bmr = jObject["bmr"].toString()
                            val bmi = jObject["bmi"].toString()
                            val goalWeight = jObject["goalWeight"].toString()
                            val email = jObject["email"].toString()



                        intent.putExtra(MainActivity.WEIGHT, currentWeight)
                        intent.putExtra(MainActivity.HEIGHT, height)
                        intent.putExtra(MainActivity.AGE, age)
                        intent.putExtra(MainActivity.EMAIL, email)
                        intent.putExtra(MainActivity.GOALWEIGHT, goalWeight)
                        intent.putExtra(MainActivity.BMR, bmr)
                        intent.putExtra(MainActivity.BMI, bmi)
                        intent.putExtra(MainActivity.NAME, name)
                        intent.putExtra(MainActivity.GENDER, gender)
                        intent.putExtra(COUNTRY, country)
                        startActivity(intent)
                    }

                },
                object : Response.ErrorListener {
                    override fun onErrorResponse(error: VolleyError?) {
                        if (error is TimeoutError || error is NoConnectionError) {
                            btnReplay.visibility = View.VISIBLE
                            textReplay.visibility = View.VISIBLE
                            progressBar.visibility = View.GONE

                            Toast.makeText(
                                this@SplashScreen,
                               "Connection error",
                                Toast.LENGTH_LONG
                            ).show()
                        } else if (error is AuthFailureError) {
                            btnReplay.visibility = View.VISIBLE
                            textReplay.visibility = View.VISIBLE
                            progressBar.visibility = View.GONE


                            Toast.makeText(
                                this@SplashScreen,
                                "AuthFailureError",
                                Toast.LENGTH_LONG
                            ).show()
                        } else if (error is ServerError) {
                            btnReplay.visibility = View.VISIBLE
                            textReplay.visibility = View.VISIBLE
                            progressBar.visibility = View.GONE


                            Toast.makeText(
                                this@SplashScreen,
                                "ServerError",
                                Toast.LENGTH_LONG
                            ).show()
                        } else if (error is NetworkError) {
                            btnReplay.visibility = View.VISIBLE
                            textReplay.visibility = View.VISIBLE
                            progressBar.visibility = View.GONE


                            Toast.makeText(
                                this@SplashScreen,
                                "NetworkError",
                                Toast.LENGTH_LONG
                            ).show()
                        } else if (error is ParseError) {
                            btnReplay.visibility = View.VISIBLE
                            textReplay.visibility = View.VISIBLE
                            progressBar.visibility = View.GONE


                            Toast.makeText(
                                this@SplashScreen,
                                "ParseError",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                }) {
                override fun getParams(): Map<String, String> {
                    val params: MutableMap<String, String> =
                        HashMap()
                    return params
                }

                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val params: MutableMap<String, String> =
                        HashMap()
                    params.put("authorization", "Bearer " + token)
                    return params
                }
            }
        queue.add(sr)

    }

    fun IPreq() {


        val queue = Volley.newRequestQueue(this)
//for POST requests, only the following line should be changed to

//for POST requests, only the following line should be changed to
        val sr: StringRequest =
            object : StringRequest(
                Method.GET, "https://ifconfig.co/json",
                object : Response.Listener<String?> {
                    override fun onResponse(response: String?) {
                        val jObject = JSONObject(response)
                        country = jObject["country"].toString()
                        Log.d("AMINIPs",country.toString())


                    }

                },
                object : Response.ErrorListener {
                    override fun onErrorResponse(error: VolleyError?) {
                        if (error is TimeoutError || error is NoConnectionError) {


                            Toast.makeText(
                                this@SplashScreen,
                                "Connection error",
                                Toast.LENGTH_LONG
                            ).show()
                        } else if (error is AuthFailureError) {



                            Toast.makeText(
                                this@SplashScreen,
                                "AuthFailureError",
                                Toast.LENGTH_LONG
                            ).show()
                        } else if (error is ServerError) {



                            Toast.makeText(
                                this@SplashScreen,
                                "ServerError",
                                Toast.LENGTH_LONG
                            ).show()
                        } else if (error is NetworkError) {



                            Toast.makeText(
                                this@SplashScreen,
                                "NetworkError",
                                Toast.LENGTH_LONG
                            ).show()
                        } else if (error is ParseError) {



                            Toast.makeText(
                                this@SplashScreen,
                                "ParseError",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                }) {
                override fun getParams(): Map<String, String> {
                    val params: MutableMap<String, String> =
                        HashMap()
                    return params
                }

                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val params: MutableMap<String, String> =
                        HashMap()
                    return params
                }
            }
        queue.add(sr)

    }
}

