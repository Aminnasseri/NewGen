package AminAmix.com.socialpersonality

import AminAmix.com.socialpersonality.WebService.APIClient
import AminAmix.com.socialpersonality.WebService.APIInterface
import AminAmix.com.socialpersonality.model.ResponseRegister2
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_email_confirmation.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_personal_information.*
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import java.util.concurrent.TimeUnit


class EmailConfirmationActivity : AppCompatActivity() {
    lateinit var number: String
    lateinit var tokenOrg: String
    lateinit var finalToken: String
    lateinit var registerMode: String
    lateinit var ss: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email_confirmation)
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)
        progressBarConfirmNumber.visibility = View.INVISIBLE



        //Timer
        val timer = object : CountDownTimer(120000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                //    textView.setText("00: " + millisUntilFinished / 1000)
//format for minute  ----->  "Time Remaining %02d min: %02d sec"
                val text: String = java.lang.String.format(
                    Locale.getDefault(), " %02d : %02d",
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60,
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60
                )
                txtResnd.setText(text)

            }

            @SuppressLint("SetTextI18n")
            override fun onFinish() {
            txtResnd.setText("Resnd")
                txtResnd.setOnClickListener{
                    txtResnd.setText("Waiting...")
               resendCode(finalToken,ss)
                    Log.i("Finaltoken",finalToken)

                }

            }
        }
        timer.start()





        //Retrieve token wherever necessary
        //Retrieve token wherever necessary


         ss = intent.getStringExtra("user_email").toString()
         registerMode = intent.getStringExtra("registerMode").toString()
         tokenOrg = intent.getStringExtra("token").toString()



//        token = intent.getStringExtra("token").toString()
        finalToken = "Bearer $tokenOrg"
        Log.w("Amiroo",finalToken)

        txtEmailAdress.setText(ss)

        btnConfirm.setOnClickListener {
            progressBarConfirmNumber.visibility = View.VISIBLE
            btnConfirm.isEnabled =false

            number = editTextNumber.text.toString()
            confirmUser(finalToken, number)
        }

    }

    fun confirmUser(token: String, amount: String) {
        Handler().postDelayed(
            Runnable {
                btnConfirm.isEnabled = true
            },
            2200 //Specific time in milliseconds
        )

        val apilnterface: APIInterface = APIClient().getClient()!!.create(APIInterface::class.java)

        var call: Call<ResponseRegister2> = apilnterface.confirmUser(token, amount)
        call.enqueue(object : Callback<ResponseRegister2> {
            override fun onResponse(
                call: Call<ResponseRegister2>,
                response: Response<ResponseRegister2>
            ) {
                if (response.code() == 200) {
                    progressBarConfirmNumber.visibility = View.INVISIBLE


                    Log.w("RES", "${response.body().toString()}")

                    if (registerMode == "login" ){
                        val preferences: SharedPreferences =
                            applicationContext.getSharedPreferences("SOCIAL_PERSONALITY", Context.MODE_PRIVATE)
                        preferences.edit().putString("TOKEN", tokenOrg).apply()
                        Log.i("TokenHome1",tokenOrg)

                        val intent = Intent(this@EmailConfirmationActivity, HomeActivity::class.java)
                        startActivity(intent)
                        finish()
                    }else{
                        val intent = Intent(this@EmailConfirmationActivity, PersonalInformationActivity::class.java)
                        intent.putExtra("personal_token", tokenOrg)
                        startActivity(intent)
                        finish()
                    }



                    val dataFromResponse = response.body()
                    val token = dataFromResponse?.token.toString()

//                    val intent = Intent(this@EmailConfirmationActivity, EmailConfirmationActivity::class.java)
//                    startActivity(intent)

                }
                if (response.code() == 400) {
                    progressBarConfirmNumber.visibility = View.INVISIBLE


                    try {
                        val jObjError = JSONObject(response.errorBody()!!.string())
                        Toast.makeText(
                            applicationContext,
                            jObjError.getString("message"),
                            Toast.LENGTH_LONG
                        ).show()
                    } catch (e: Exception) {
                        Toast.makeText(applicationContext, e.message, Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onFailure(call: Call<ResponseRegister2>, t: Throwable) {
                btnConfirm.isEnabled
                Toast.makeText(
                    this@EmailConfirmationActivity,
                    " Register is not ok",
                    Toast.LENGTH_LONG
                )
                    .show()
            }

        })
    }

    fun resendCode(token: String,emailAddress:String) {
        val apilnterface: APIInterface = APIClient().getClient()!!.create(APIInterface::class.java)

        var call: Call<ResponseRegister2> = apilnterface.reSend(token,emailAddress)
        call.enqueue(object : Callback<ResponseRegister2> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<ResponseRegister2>,
                response: Response<ResponseRegister2>
            ) {
                if (response.code() == 200) {
                    txtResnd.setText("Resend")

                    Log.w("RES", "${response.body().toString()}")
                    Toast.makeText(
                        this@EmailConfirmationActivity,
                        "We've sent the code back to you",
                        Toast.LENGTH_LONG
                    )
                        .show()


                }
                if (response.code() == 400) {
                    txtResnd.setText("Resend")

                    try {
                        val jObjError = JSONObject(response.errorBody()!!.string())
                        Toast.makeText(
                            applicationContext,
                            jObjError.getString("message"),
                            Toast.LENGTH_LONG
                        ).show()
                    } catch (e: Exception) {
                        Toast.makeText(applicationContext, e.message, Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onFailure(call: Call<ResponseRegister2>, t: Throwable) {

                Toast.makeText(
                    this@EmailConfirmationActivity,
                    " Register is not ok",
                    Toast.LENGTH_LONG
                )
                    .show()
            }

        })
    }

    fun confirm(amount: String) {
        val apilnterface: APIInterface = APIClient().getClient()!!.create(APIInterface::class.java)


    }





}