package com.zibfit.vaxdiax

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.android.volley.*
import com.android.volley.toolbox.JsonObjectRequest
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.zxing.*
import com.google.zxing.common.HybridBinarizer
import com.google.zxing.qrcode.detector.FinderPattern
import kotlinx.android.synthetic.main.activity_image_server.*
import kotlinx.android.synthetic.main.toolbar_open_kit.*
import org.json.JSONObject
import wiadevelopers.com.advancevolley_kotlin.network.VolleyMultipartRequest
import wiadevelopers.com.volleykotling.AppSingleton
import java.io.File
import java.io.FileInputStream

private lateinit var backToast: Toast

class ImageServerActivity : AppCompatActivity() {

    companion object {
        const val SERVER = "SERVER"
        const val QRRES = "QRRES"
        const val TIMESERVER = "TIMESERVER"
        const val RESULT = "RESULT"
        const val CONFIDENCE = "CONFIDENCE"

    }

    private lateinit var bitmap: Bitmap

    //    val token =
//        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjVlOTZkZTNkODEyNmYwMDAyNGM2OTc3NSIsImlhdCI6MTU4NzIwNjMwMCwiZXhwIjoxNTg3MjQyMzAwfQ.6VYd39LG_bH5hffZx0c_RzW_jnNcYrZQsgIUCb4IVkU"
    //  var bitmap: Bitmap? = null
    var pathImage: String? = ""
    var qr: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_server)

        val sharedPreferencesQR =
            getSharedPreferences(QRReaderActivity.QRCODEACTIVITY, Context.MODE_PRIVATE)
        qr = sharedPreferencesQR.getString(QRReaderActivity.QR, "")

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

        retryCapture.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    ScanPlateActivity::class.java
                )
            )
        }

        btn_server.setOnClickListener {
            btn_server.isEnabled = false
            uploadImage()
            progressBarSend.visibility = View.VISIBLE

        }



        val path = intent.getStringExtra("path")
        getBitmap(path)
        val image = File(path)
        println("PAthaga :: $path")


//        //Get QR From Photo
//        val mbuilder = MaterialAlertDialogBuilder(this)
//
//        val `is`: InputStream = BufferedInputStream(FileInputStream(image))
//        val bitmap2 = BitmapFactory.decodeStream(`is`)
//        val decoded = scanQRImage(bitmap2)
//        Log.i("QrTest", "Decoded string=$decoded")
//
////        Qrcode Check
//
//        if (decoded != null && decoded == qr) {
//            //  Toast.makeText(this, "Okeey Ast Dada Qr: $decoded and qr == $qr", Toast.LENGTH_SHORT).show()
//
//        }
//        else if(decoded != null && decoded != qr){
//            mbuilder.setTitle("Qr Problem")
//            mbuilder.setMessage("QR Code error correction")
//                .setNegativeButton("Dismiss") { dialog, which ->
//                    startActivity(Intent(this, ScanPlateActivity::class.java))
//                    finish()
//                }
//            mbuilder.setCancelable(false)
//            mbuilder.show()
//
//        }
//        else {
//            mbuilder.setTitle("Error")
//            mbuilder.setMessage("QRCode not found.")
//                .setNegativeButton("Dismiss") { dialog, which ->
//                    startActivity(Intent(this, ScanPlateActivity::class.java))
//                    finish()
//                }
//            mbuilder.setCancelable(false)
//            mbuilder.show()
//
//        }


    }


    fun getBitmap(path: String?): Bitmap? {
        try {
            val f = File(path)
            val options = BitmapFactory.Options()
            options.inPreferredConfig = Bitmap.Config.ARGB_8888
            bitmap = BitmapFactory.decodeStream(FileInputStream(f), null, options)!!
            val rotatedBitmap = bitmap?.rotate(90F)

            imgTest.setImageBitmap(rotatedBitmap)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return bitmap
    }

    fun Bitmap.rotate(degrees: Float): Bitmap {
        val matrix = Matrix().apply { postRotate(degrees) }
        return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
    }

    private fun uploadImage() {

        val TokenSharedPreferences =
            getSharedPreferences(QRReaderActivity.GETINFOACT, Context.MODE_PRIVATE)
        val token = TokenSharedPreferences.getString(QRReaderActivity.TOKEN, "").toString()
        Log.i("LOG_Token", token.toString())


//in url baray get info hast
        val url = "https://prod-vax.rapidtestapi.me/api/v1/image"


        //anonymous
        val urlQr = "https://healthcheckapplication-test.herokuapp.com/api/v1/image"


        val listener = Response.Listener<NetworkResponse> { response ->


            progressBarSend.visibility = View.GONE
            val jsonObject = JSONObject(String(response.data))
//            Toast.makeText(applicationContext, jsonObject.getString("path"), Toast.LENGTH_LONG)
//                .show()
            pathImage = jsonObject.getString("path")

            aminooAmiroo()

        }

        val errorListener = Response.ErrorListener { error ->

            val mbuilder = MaterialAlertDialogBuilder(this)
            if (error is TimeoutError || error is NoConnectionError) {
                progressBarSend.visibility = View.GONE
                mbuilder.setTitle("Error")
                mbuilder.setMessage("You have Network problem,Check your Internet Connection")
                    .setNegativeButton("Dismiss") { dialog, which ->
                        startActivity(Intent(this, ScanPlateActivity::class.java))
                    }
                mbuilder.show()


            } else if (error.networkResponse.data != null) {


                val networkResponseData = error.networkResponse.data
                val networkResponseJson = JSONObject(String(networkResponseData))
                val errors = networkResponseJson.has("errors")
                if (errors) {
                    progressBarSend.visibility = View.GONE
                    val msg = networkResponseJson["message"].toString()
                    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
                } else {
                    progressBarSend.visibility = View.GONE
                    Toast.makeText(this, "Error: Service unavailable", Toast.LENGTH_LONG).show()
                }

                startActivity(Intent(this, ScanPlateActivity::class.java))
            }
        }


        val request = object : VolleyMultipartRequest(Method.PUT, url, listener, errorListener) {


            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val params: MutableMap<String, String> =
                    HashMap()
                params["Authorization"] = "Bearer $token"
                params["Accept"] = "application/json"
                params["apiKey"] = "4IjEKZyh48WjFX3KP3whbOczl9Ju6TPK"


                return params
            }

            override val byteData: Map<String, DataPart>?
                protected get() {
                    val params: MutableMap<String, DataPart> = HashMap()
                    val imagename = System.currentTimeMillis()
                    val converetdImage = getResizedBitmap(bitmap, 900)
                    params["image"] =
                        DataPart("$imagename.jpg", getFileDataFromDrawable(converetdImage!!))

                    return params
                }
        }

        AppSingleton.getInstance(applicationContext).addToRequestQueue(request)
    }

    fun getResizedBitmap(image: Bitmap, maxSize: Int): Bitmap? {
        var width = image.width
        var height = image.height
        val bitmapRatio = width.toFloat() / height.toFloat()
        if (bitmapRatio > 1) {
            width = maxSize
            height = (width / bitmapRatio).toInt()
        } else {
            height = maxSize
            width = (height * bitmapRatio).toInt()
        }
        return Bitmap.createScaledBitmap(image, width, height, true)
    }





    //Find Qr code in Image

    fun scanQRImage(bMap: Bitmap): String? {
        var contents: String? = null
        val intArray = IntArray(bMap.width * bMap.height)
        //copy pixel data from the Bitmap into the 'intArray' array
        bMap.getPixels(intArray, 0, bMap.width, 0, 0, bMap.width, bMap.height)
        val source: LuminanceSource =
            RGBLuminanceSource(bMap.width, bMap.height, intArray)
        val bitmap = BinaryBitmap(HybridBinarizer(source))
        val reader: Reader = MultiFormatReader()
        try {
            val result = reader.decode(bitmap)
            contents = result.getText()

            //postion of qr code
            val resultPoints = result.resultPoints
            println("resultPoints:")
            for (i in resultPoints.indices) {
                val resultPoint = resultPoints[i]
                print("  [$i]:")
                print(" x = " + resultPoint.x)
                print(", y = " + resultPoint.y)
                if (resultPoint is FinderPattern) print(
                    ", estimatedModuleSize = "
                            + (resultPoint as FinderPattern).estimatedModuleSize
                )
                println()
            }

        } catch (e: java.lang.Exception) {
            Log.e("QrTest", "Error decoding barcode", e)
        }
        return contents
    }


    fun aminooAmiroo() {

        val TokenSharedPreferences =
            getSharedPreferences(QRReaderActivity.GETINFOACT, Context.MODE_PRIVATE)
        val token = TokenSharedPreferences.getString(QRReaderActivity.TOKEN, "").toString()
        Log.i("LOG_Token", token.toString())

        Log.i("LOG_ERROR", "Shatak")

        val URL = "https://prod-vax.rapidtestapi.me/api/v1/check"
        val URLQR = "https://healthcheckapplication-test.herokuapp.com/api/v1/check"
        //lat and Long
//        val sharedPreferencesLocation =
//            getSharedPreferences(LocationRadiusActivity.Location, Context.MODE_PRIVATE)
//        val lat = sharedPreferencesLocation.getString(LocationRadiusActivity.LAT, "")
//        val long = sharedPreferencesLocation.getString(LocationRadiusActivity.LONG, "")
//        println("latoo: $lat")

//QR data
//        val sharedPreferencesQR =
//            getSharedPreferences(QRReaderActivity.QRCODEACTIVITY, Context.MODE_PRIVATE)
//        qr = sharedPreferencesQR.getString(QRReaderActivity.QR, "")

        val jsonObject = JSONObject()
        jsonObject.put("imagePath", pathImage)
        jsonObject.put("qrCode", qr)
//        jsonObject.put("latitude", lat)
//        jsonObject.put("longitude", long)
        Log.i("QrIS", qr.toString())
        Log.i("DEV_LOG", jsonObject.toString())

        val listener = Response.Listener<JSONObject> { response ->
            val mbuilder = MaterialAlertDialogBuilder(this)
            Log.i("LOG_RESPONSE", response.toString())


            val errors = response.has("errors")
            Log.i("LOG_RESPONSE", errors.toString())
            if (errors == true) {

                mbuilder.setTitle("Error")
                mbuilder.setMessage(response.toString())
                    .setNegativeButton("Dismiss") { dialog, which ->
                        startActivity(Intent(this, ScanPlateActivity::class.java))
                        finish()
                    }
                mbuilder.setCancelable(false)
                mbuilder.show()

            } else {

                var qrcode = response.getString("qrCode")
                var timeServer = response.getString("timestamp")
                var result = response.getString("result")
                var confidence = response.getString("confidence")
                var testNumber = response.getString("testNumber")
                var igm = response.getString("igm")
                var iga = response.getString("iga")
                var igg = response.getString("igg")

                val intent =
                    Intent(this, UploadingActivity::class.java)


                intent.putExtra("qr", qrcode)
                intent.putExtra("time", timeServer)
                intent.putExtra("result", result)
                intent.putExtra("confidence", confidence)
                intent.putExtra("test", testNumber)
                intent.putExtra("igm", igm)
                intent.putExtra("iga", iga)
                intent.putExtra("igg", igg)


                startActivity(intent)
                finish()
            }


        }

        val errorListener = Response.ErrorListener { error ->
            val mbuilder = MaterialAlertDialogBuilder(this)
            if (error is TimeoutError || error is NoConnectionError) {
                progressBarSend.visibility = View.GONE
                mbuilder.setTitle("Error")
                mbuilder.setMessage("You have Network problem,Check your Internet Connection")
                    .setNegativeButton("Dismiss") { dialog, which ->
                        startActivity(Intent(this, ScanPlateActivity::class.java))
                    }
                mbuilder.show()

            } else if (error.networkResponse.data != null) {


                val networkResponseData = error.networkResponse.data
                val networkResponseJson = JSONObject(String(networkResponseData))
                val errors = networkResponseJson.has("errors")
                if (errors) {
                    progressBarSend.visibility = View.GONE
                    val msg = networkResponseJson["message"].toString()
                    mbuilder.setTitle("Error")
                    mbuilder.setMessage(msg)
                    mbuilder.setCancelable(false)
                        .setPositiveButton("ok") { dialog, which ->
                            startActivity(Intent(this, ScanPlateActivity::class.java))
                        }
                    mbuilder.show()

                } else {
                    progressBarSend.visibility = View.GONE
                    Toast.makeText(this, "Error: Service unavailable", Toast.LENGTH_LONG).show()

                }


            }
        }

        val request = object :
            JsonObjectRequest(Request.Method.POST, URL, jsonObject, listener, errorListener) {

            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val params: MutableMap<String, String> =
                    HashMap()
                params["Authorization"] = "Bearer $token"
                params["Accept"] = "application/json"
                params["apiKey"] = "4IjEKZyh48WjFX3KP3whbOczl9Ju6TPK"
                Log.i("DEV_LOG", "HEADERS")


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




