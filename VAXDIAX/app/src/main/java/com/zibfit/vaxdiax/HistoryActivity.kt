package com.zibfit.vaxdiax

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.android.volley.*
import com.android.volley.toolbox.JsonObjectRequest
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_test_history.*
import kotlinx.android.synthetic.main.toolbar_open_kit.*
import org.json.JSONObject
import wiadevelopers.com.volleykotling.AppSingleton

private lateinit var backToast: Toast

private var testNumberInput: String? = null
private var igm: String? = null
private var iga: String? = null
private var igg: String? = null

class HistoryActivity : AppCompatActivity() {
    var testNumber: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_history)

        testNumber = findViewById(R.id.test_number)
        testNumber?.addTextChangedListener(loginTextWatcher)

        btnNextHistory.setOnClickListener {
            progressBarHistory.visibility = View.VISIBLE
            btnNextHistory.isEnabled = false

//            if(testNumberInput == "35735"){
//                startActivity(
//                    Intent(
//                        this,
//                        HistoryResultActivity::class.java
//                    )
//                )
//            }else{
//                val mBuilder = MaterialAlertDialogBuilder(this)
//                mBuilder.setTitle("Error")
//                mBuilder.setMessage("Please enter a correct code")
//                    .setNegativeButton("Dismiss") { dialog, which ->
//                                                // Do something for button click
//                    }
//                mBuilder.show()
//            }
            jsonRequest()


        }
        home_toolbar_open_kit.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    HomeActivity::class.java
                )
            )
            overridePendingTransition(R.anim.left_in, R.anim.right_out)
            finish()
        }
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
            val testWithoutSpace: String = testNumber?.getText().toString().replace(" ", "")
            testNumberInput = testWithoutSpace
            testNumber?.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16F)

            btnNextHistory?.setEnabled(!testNumberInput!!.isEmpty())
            Log.i("LOG_test", testNumberInput.toString())

        }

        var count = 0
        override fun afterTextChanged(s: Editable) {
            val inputlength: Int = testNumber?.getText().toString().length
            if (count <= inputlength && (inputlength == 4 ||
                        inputlength == 9 || inputlength == 14 || inputlength == 19)
            ) {
                testNumber!!.setText(testNumber?.getText().toString() + " ");
                val pos: Int = testNumber!!.getText().length
                testNumber?.setSelection(pos)
            } else if (count >= inputlength && (inputlength == 4 ||
                        inputlength == 9 || inputlength == 14 || inputlength == 19)
            ) {
                testNumber?.setText(
                    testNumber?.getText().toString()
                        .substring(
                            0, testNumber?.getText()
                                .toString().length - 1
                        )
                );
                val pos: Int = testNumber!!.getText().length
                testNumber?.setSelection(pos)
            }
            count = testNumber?.getText().toString().length

        }
    }

    fun jsonRequest() {


        val url = "https://prod-vax.rapidtestapi.me/api/v1/check/item"
        val jsonObject = JSONObject()
        jsonObject.put("testNumber", testNumberInput.toString())


        val listener = Response.Listener<JSONObject> { response ->


            var timestamp = response.getString("timestamp")
            Log.i("LOG_time", timestamp)

            var testNumber = response.getString("testNumber")
            var result = response.getString("result")
            var confidence = response.getString("confidence")
            if(response.has("igm")){
                igm = response.getString("igm")
            }else
            {
                igm ="3"
            }

            if(response.has("iga")){
                iga = response.getString("iga")
            }else
            {
                iga ="3"
            }

            if(response.has("igg")){
                igg= response.getString("igg")
            }else
            {
                igg ="3"
            }

//            var iga = response.getString("iga")
//            var igg = response.getString("igg")
            progressBarHistory.visibility = View.GONE
            val intent =
                Intent(this, HistoryResultActivity::class.java)



            intent.putExtra("time", timestamp)
            intent.putExtra("result", result)
            intent.putExtra("confidence", confidence)
            intent.putExtra("test", testNumber)
            intent.putExtra("igm", igm)
            intent.putExtra("iga", iga)
            intent.putExtra("igg", igg)


            startActivity(intent)
            finish()

        }

        val errorListener = Response.ErrorListener { error ->
            progressBarHistory.visibility = View.GONE

            val mbuilder = MaterialAlertDialogBuilder(this)

            if (error is TimeoutError || error is NoConnectionError) {
                Toast.makeText(
                    this,
                    "You have Network problem,Check your Internet Connection",
                    Toast.LENGTH_LONG
                ).show()

            } else if (error.networkResponse.data != null) {

                val networkResponseData = error.networkResponse.data
                val networkResponseJson = JSONObject(String(networkResponseData))
                val msg = networkResponseJson["message"].toString()
                val errors = networkResponseJson.has("errors")
                if (errors) {
                    Log.i("LOG_J", networkResponseJson.toString())
                    mbuilder.setTitle("Error")
                    mbuilder.setMessage(msg)
                        .setNegativeButton("Dismiss") { dialog, which ->
                            // Do something for button click
                        }
                    mbuilder.show()

                    progressBarHistory.visibility = View.GONE

                } else {
                    progressBarHistory.visibility = View.GONE

                    mbuilder.setTitle("Error")
                    mbuilder.setMessage("Error: Service unavailable")
                        .setNegativeButton("Dismiss") { dialog, which ->
                            // Do something for button click
                        }
                    mbuilder.show()

                }
            } else {
                Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show()
            }


        }
        val request = object :
            JsonObjectRequest(Request.Method.POST, url, jsonObject, listener, errorListener) {

            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val params: MutableMap<String, String> =
                    HashMap()
                params["apiKey"] = "4IjEKZyh48WjFX3KP3whbOczl9Ju6TPK"


                return params

            }
        }

        AppSingleton.getInstance(applicationContext).addToRequestQueue(request)


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
