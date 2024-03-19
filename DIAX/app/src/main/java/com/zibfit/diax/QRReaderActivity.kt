package com.zibfit.diax
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.*
import com.android.volley.toolbox.JsonObjectRequest
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.activity_q_r_reader.*
import kotlinx.android.synthetic.main.toolbar_open_kit.*
import org.json.JSONObject
import wiadevelopers.com.volleykotling.AppSingleton

private lateinit var backToast: Toast


class QRReaderActivity : AppCompatActivity() {

    companion object {
        const val QRCODEACTIVITY = "QRCODE"
        const val QR = "QR"


    }

    var codeScanner: CodeScanner? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_q_r_reader)

        val scannerView = findViewById<CodeScannerView>(R.id.scanner_view)


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

        //hidden key for use without Qr code

        txtStep2.setOnClickListener {
            startActivity(Intent(this,TakeBloodActivity::class.java))
        }


        btnQrNext.setOnClickListener {

            ///TODO This is for GET INFO Version
//            startActivity(Intent(this, GetInformationActivity::class.java))
//            finish()

            ///TODO This is for Ananymos Version


//
        }


        btnTryAgain.setOnClickListener {
            errorImageQr.visibility = View.INVISIBLE
            txtQrError.visibility = View.INVISIBLE
            view_wrong_code.visibility = View.INVISIBLE
            btnTryAgain.visibility = View.INVISIBLE
        }

        view_wrong_code.setOnClickListener {
            errorImageQr.visibility = View.INVISIBLE
            txtQrError.visibility = View.INVISIBLE
            view_wrong_code.visibility = View.INVISIBLE
            btnTryAgain.visibility = View.INVISIBLE
            txtQrResultQrPage.visibility = View.INVISIBLE

        }


        Dexter.withActivity(this)
            .withPermission(android.Manifest.permission.CAMERA)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse?) {

                    codeScanner = CodeScanner(this@QRReaderActivity, scannerView)
                    // Parameters (default values)
                    codeScanner?.camera =
                        CodeScanner.CAMERA_BACK // or CAMERA_FRONT or specific camera id
                    codeScanner?.formats = CodeScanner.ALL_FORMATS // list of type BarcodeFormat,
                    // ex. listOf(BarcodeFormat.QR_CODE)
                    codeScanner?.autoFocusMode = AutoFocusMode.SAFE // or CONTINUOUS
                    codeScanner?.scanMode = ScanMode.SINGLE // or CONTINUOUS or PREVIEW
                    codeScanner?.isAutoFocusEnabled = true // Whether to enable auto focus or not
                    codeScanner?.isFlashEnabled = false // Whether to enable flash or not


                    val vibrator =
                        applicationContext?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                    // Callbacks
                    codeScanner?.decodeCallback = DecodeCallback {

                        val pattern = Regex("[A-Z]{2}[-][A-Z]{3}.{6}")
                        val separated = pattern.matches(it.text)
                        if (separated) {


                            runOnUiThread {
                                view_wrong_code.visibility = View.VISIBLE
                                errorImageQr.setImageResource(R.drawable.succes_qr)
                                errorImageQr.visibility = View.VISIBLE
                                txtQrError.setText("QR Code registered")
                                txtQrError.setTextColor(Color.WHITE)
                                txtQrError.visibility = View.VISIBLE
                               // txtQrResultQrPage.visibility = View.VISIBLE
                                txtQrResultQrPage.setText(it.text)


                                if (Build.VERSION.SDK_INT >= 26) {
                                    vibrator.vibrate(
                                        VibrationEffect.createOneShot(
                                            50,
                                            VibrationEffect.DEFAULT_AMPLITUDE
                                        )
                                    )
                                } else {

                                    vibrator.vibrate(50)
                                }
                                progressBarQr.visibility = View.VISIBLE
                                jsonRequest()
                               // btnQrNext.isEnabled = true
//                                Toast.makeText(
//                                    this@QRReaderActivity,
//                                    "Scan result: ${it.text}",
//                                    Toast.LENGTH_LONG
//                                )
//                                    .show()

                                val editor = getSharedPreferences(
                                    QRCODEACTIVITY,
                                    Context.MODE_PRIVATE
                                ).edit()
                                editor.putString(QR, it.toString())
                                editor.apply()
                            }
                        } else {


                            runOnUiThread {

                                view_wrong_code.visibility = View.VISIBLE
                                errorImageQr.setImageResource(R.drawable.error_qr)
                                errorImageQr.visibility = View.VISIBLE
                                txtQrError.setText("QR Code is not valid")
                                txtQrError.visibility = View.VISIBLE

                                btnTryAgain.visibility = View.VISIBLE


                                if (Build.VERSION.SDK_INT >= 26) {
                                    vibrator.vibrate(
                                        VibrationEffect.createOneShot(
                                            400,
                                            VibrationEffect.DEFAULT_AMPLITUDE
                                        )
                                    )
                                } else {
                                    vibrator.vibrate(300)
                                }
                            }
                        }


                    }
                    codeScanner?.errorCallback = ErrorCallback {
                        // or ErrorCallback.SUPPRESS
                        runOnUiThread {
                            Toast.makeText(
                                this@QRReaderActivity, "Camera initialization error: ${it.message}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                    scannerView.setOnClickListener {
                        codeScanner?.startPreview()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken?
                ) {
                    Toast.makeText(
                        this@QRReaderActivity,
                        "You Must enable this permission from setting /app manager/rapidTest",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                    Toast.makeText(
                        this@QRReaderActivity,
                        "You Must enable this permission",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }).check()
    }

    override fun onResume() {
        super.onResume()
        codeScanner?.startPreview()
    }

    override fun onPause() {
        codeScanner?.releaseResources()
        super.onPause()
    }

    fun jsonRequest() {
        val deviceID = Settings.Secure.getString(
            contentResolver,
            Settings.Secure.ANDROID_ID
        )

        //QR data
        val sharedPreferencesQR =
            getSharedPreferences(QRReaderActivity.QRCODEACTIVITY, Context.MODE_PRIVATE)
        val qr = sharedPreferencesQR.getString(QRReaderActivity.QR, "")

        val url = "https://prod-env.rapidtestapi.me/api/v1/user/aregister"
        val jsonObject = JSONObject()
        jsonObject.put("deviceId", deviceID)
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
                GetInformationActivity.GETINFOACT,
                Context.MODE_PRIVATE
            ).edit()
            editor.putString(GetInformationActivity.TOKEN, token)
            editor.apply()

            val TokenSharedPreferences =
                getSharedPreferences(GetInformationActivity.GETINFOACT, Context.MODE_PRIVATE)
            val amin = TokenSharedPreferences.getString(GetInformationActivity.TOKEN, "").toString()
            Log.i("LOG_EDITOR_TOKEN", amin)

            progressBarQr.visibility = View.GONE
            startActivity(Intent(this, TakeBloodActivity::class.java))
            finish()

        }

        val errorListener = Response.ErrorListener { error ->
            progressBarQr.visibility = View.GONE

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
                    ///Todo in ghesmat bayad test she
                    mbuilder.setCancelable(false)
                        .setNegativeButton("Dismiss") { dialog, which ->
                            // Do something for button click
                            startActivity(Intent(this, QRReaderActivity::class.java))
                            finish()
                        }
                    mbuilder.show()

                    progressBarQr.visibility = View.GONE

                } else {
                    progressBarQr.visibility = View.GONE

                    mbuilder.setTitle("Error")
                    mbuilder.setMessage("Error: Service unavailable")
                    mbuilder.setCancelable(false)

                        .setNegativeButton("Dismiss") { dialog, which ->
                            // Do something for button click
                            startActivity(Intent(this, QRReaderActivity::class.java))
                            finish()

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
                params["apiKey"] = "K6KjwvAal87nUGM6fkmPd9w6Z6OGYYt6"


                return params

            }
        }



        AppSingleton.getInstance(applicationContext).addToRequestQueue(request)


    }

    //Back Press

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