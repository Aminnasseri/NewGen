package com.zibfit.diax

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.google.android.material.datepicker.MaterialDatePicker
import com.zibfit.diax.R
import kotlinx.android.synthetic.main.activity_get_information.*
import kotlinx.android.synthetic.main.activity_image_server.*
import kotlinx.android.synthetic.main.toolbar_open_kit.*
import org.json.JSONObject
import wiadevelopers.com.volleykotling.AppSingleton


private var usernameInput: String? = null
private var qrCode: String? = null
private var bOT: String? = null

private var email: String? = null
private var companyCode: String? = null
private var success: String? = null
private lateinit var backToast: Toast


class GetInformationActivity : AppCompatActivity() {


    companion object {
        const val GETINFOACT = "GETINFOACTIVITY"
        const val TOKEN = "TOKEN"


    }

    private var editTextUsername: EditText? = null
    private var editTextBOT: EditText? = null
    private var editEmail: EditText? = null
    private var editCompany: EditText? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_information)
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



        btnNextInfo.setOnClickListener {
            btnNextInfo.isEnabled = false
            progressBarInfo.visibility = View.VISIBLE
            jsonRequest()


        }

        ///TODO DELETE THIS CODE
        textView9.setOnClickListener {
            startActivity(Intent(this, LocationRadiusActivity::class.java))
            finish()
        }
        val builder = MaterialDatePicker.Builder.datePicker()
        val materialDatePicker = builder.build()

//Set Date picker
        edtDateOfBirth.setEndIconOnClickListener {
            materialDatePicker.show(supportFragmentManager, "DATE-PICKER")
        }

        materialDatePicker.addOnPositiveButtonClickListener {
            date_of_birth.setText(materialDatePicker.headerText)
        }

        //set Edit Text
        editTextUsername = findViewById(R.id.full_name)
        editTextBOT = findViewById<EditText>(R.id.date_of_birth)
        editEmail = findViewById<EditText>(R.id.email_address)
        editCompany = findViewById<EditText>(R.id.company_code)


        editTextUsername?.addTextChangedListener(loginTextWatcher)
        editTextBOT?.addTextChangedListener(loginTextWatcher)
        editEmail?.addTextChangedListener(loginTextWatcher)
        editCompany?.addTextChangedListener(loginTextWatcher)


    }

    private val loginTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(
            s: CharSequence,
            start: Int,
            count: Int,
            after: Int
        ) {
        }

        override fun onTextChanged(
            s: CharSequence,
            start: Int,
            before: Int,
            count: Int
        ) {
            // qrCode = intent.getStringExtra("qrCode")
            usernameInput = editTextUsername?.getText().toString().trim()
            bOT = editTextBOT?.getText().toString().trim()
            email = editEmail?.getText().toString().trim()
            companyCode = editCompany?.getText().toString().trim()

            btnNextInfo?.setEnabled(!usernameInput!!.isEmpty() && !bOT!!.isEmpty() && !email!!.isEmpty())
        }

        override fun afterTextChanged(s: Editable) {}
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
    // {"fullName":"Amin nass","birthDate":"1994/09/17","email":"goli@reza.com"}

    fun jsonRequest() {

        //QR data
        val sharedPreferencesQR =
            getSharedPreferences(QRReaderActivity.QRCODEACTIVITY, Context.MODE_PRIVATE)
        val qr = sharedPreferencesQR.getString(QRReaderActivity.QR, "")

        val url = "https://healthcheckapplication.herokuapp.com/api/v1/user/register"
        val jsonObject = JSONObject()
        jsonObject.put("fullName", usernameInput)
        jsonObject.put("birthDate", bOT)
        jsonObject.put("email", email)
        jsonObject.put("companyCode", companyCode)
        jsonObject.put("qr", qr)
        Log.i("LOG_JSON", jsonObject.toString())


        val listener = Response.Listener<JSONObject> { response ->
            var token = response.getString("token")
            val verified = response.getString("verified")

            println("Shervin Token: $token")
            println("Shervin NewUser: $verified")
            Log.i("TOKEN", token)
            //Send Token
            val editor = getSharedPreferences(
                GETINFOACT,
                Context.MODE_PRIVATE
            ).edit()
            editor.putString(TOKEN, token)
            editor.apply()

            val TokenSharedPreferences =
                getSharedPreferences(GetInformationActivity.GETINFOACT, Context.MODE_PRIVATE)
            val amin = TokenSharedPreferences.getString(GetInformationActivity.TOKEN, "").toString()
            Log.i("LOG_EDITOR_TOKEN", amin)

            progressBarInfo.visibility = View.GONE
            startActivity(Intent(this, LocationRadiusActivity::class.java))
            finish()

        }

        val errorListener = Response.ErrorListener { error ->
            progressBarInfo.visibility = View.GONE
            Log.i("LOG_AMIRR", error.networkResponse.statusCode.toString())
            Log.i("LOG_AMIRR", error.networkResponse.toString())
            Log.i("LOG_AMIRR", error.networkResponse.data.toString())

            val networkResponseData = error.networkResponse.data
            val networkResponseJson = JSONObject(String(networkResponseData))
            val errors = networkResponseJson.has("errors")
            if (errors) {
                progressBarSend.visibility = View.GONE
                val msg = networkResponseJson["message"].toString()
                Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
                finish()
            } else {
                progressBarSend.visibility = View.GONE
                Toast.makeText(this, "Error: Service unavailable", Toast.LENGTH_LONG).show()
                finish()
            }
            Toast.makeText(this, "Error : $error", Toast.LENGTH_LONG).show()


        }
        val request =
            JsonObjectRequest(Request.Method.POST, url, jsonObject, listener, errorListener)
        AppSingleton.getInstance(applicationContext).addToRequestQueue(request)


    }


}


