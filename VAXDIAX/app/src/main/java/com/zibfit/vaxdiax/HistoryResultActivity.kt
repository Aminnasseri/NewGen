package com.zibfit.vaxdiax

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.text.format.DateFormat
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.activity_history_result.*
import kotlinx.android.synthetic.main.toolbar_open_kit.*
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

private lateinit var backToast: Toast

class HistoryResultActivity : AppCompatActivity() {

    var imagePath: File? = null
    var testNumber: String? = null
    var time: String? = null
    var resShare: String? = null
    var igmFinal: String? = null
    var igaFinal: String? = null
    var iggFinal: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_result)
        home_toolbar_open_kit.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            overridePendingTransition(R.anim.left_in, R.anim.right_out)
            finish()
        }
        val timeServer = intent.getStringExtra("time")
        val result = intent.getStringExtra("result").toInt()
        var getTestNumber = intent.getStringExtra("test")
        val confidence = intent.getStringExtra("confidence")
        val igm = intent.getStringExtra("igm")
        val iga = intent.getStringExtra("iga")
        val igg = intent.getStringExtra("igg")


        // IGX Group
        val igmInt = igm.toInt()
        val iggInt = igg.toInt()
        val igaInt = iga.toInt()

        when (igmInt) {
            1 -> {
                igmFinal = "Pos."
                txtIgmH.text = igmFinal
            }
            0 -> {
                igmFinal = "Neg."
                txtIgmH.text = igmFinal
            }
            3 -> {
                igmFinal = "-"
                txtIgmH.text = igmFinal
            }
            else -> { // Note the block

            }
        }

        when (iggInt) {
            1 -> {
                iggFinal = "Pos."
                txtIggH.text = iggFinal
            }
            0 -> {
                iggFinal = "Neg."
                txtIggH.text = iggFinal
            }
            3 -> {
                iggFinal = "-"
                txtIggH.text = iggFinal
            }
            else -> { // Note the block

            }
        }

        when (igaInt) {
            1 -> {
                igaFinal = "Pos."
                txtIgAH.text = igaFinal
            }
            0 -> {
                igaFinal = "Neg."
                txtIgAH.text = igaFinal
            }
            3 -> {
                igaFinal = "-"
                txtIgAH.text = igaFinal
            }
            else -> { // Note the block

            }
        }


//        if (igmInt == 1) {
//            igmFinal = "Pos."
//            txtIgmH.text = igmFinal
//        } else {
//            igmFinal = "Neg."
//            txtIgmH.text = igmFinal
//        }
//
//        if (iggInt == 1) {
//            iggFinal = "Pos."
//            txtIggH.text = iggFinal
//        } else {
//            iggFinal = "Neg."
//            txtIggH.text = iggFinal
//        }
//
//        if (igaInt == 1) {
//            igaFinal = "Pos."
//            txtIgAH.text = igaFinal
//        } else {
//            igaFinal = "Neg."
//            txtIgAH.text = igaFinal
//        }
        //Toast.makeText(this, "igm = $igm  igg = $igg  iga = $iga", Toast.LENGTH_SHORT).show()
        //---------------------------------

        time = getDate(timeServer.toLong())
        txtDateAndTimeResH.setText(time.toString())

        //seperator

        testNumber = getTestNumber.chunked(4).joinToString(separator = " ")
        qrResH.setText("$testNumber")

//        if (result == 1) {
//            imgResultFinalH.setImageDrawable(getDrawable(R.drawable.positve))
//            txtResH.setText("This test has sensitivity of ${confidence.toString()} % Please contact your local doctor.")
//        } else {
//            imgResultFinalH.setImageDrawable(getDrawable(R.drawable.negative))
//            txtResH.setText("This test has sensitivity of ${confidence.toString()} %")
//        }


        if (result == 1) {
            // imgResultFinalH.setImageDrawable(getDrawable(R.drawable.pos_inrec))
            // txtResH.setText("This test has sensitivity of 99.8%")
            drTxtRecommendH.visibility = View.VISIBLE

            btnRes.setText("Effective Vaccination")
            btnRes.setBackgroundColor(ContextCompat.getColor(this, R.color.neg_col));
            resShare = "Effective Vaccination"


            //txtRes.setText("This test has sensitivity of ${confidence.toString()} %  Please contact your local doctor.")
        } else if (result == 0) {
            //  imgResultFinalH.setImageDrawable(getDrawable(R.drawable.negative))
            txtResH.setText("This test has sensitivity of 99.8%")
            btnRes.setText("Weak Vaccination")
            btnRes.setBackgroundColor(ContextCompat.getColor(this, R.color.pos_in_rec_col));
            drTxtRecommendH.visibility = View.GONE
            resShare = "Weak Vaccination"

            //  txtRes.setText("This test has sensitivity of ${confidence.toString()} %")
        } else if (result == 2) {
            //  imgResultFinalH.setImageDrawable(getDrawable(R.drawable.recovered))
            txtResH.setText("This test has sensitivity of 99.8%")
            btnRes.setText("Recovered")
            btnRes.setBackgroundColor(ContextCompat.getColor(this, R.color.rec_col));
            resShare = "RECOVERED"

            //  txtRes.setText("This test has sensitivity of ${confidence.toString()} %")
        }

        //Share Button

        btnShareH.setOnClickListener {

            Dexter.withActivity(this)
                .withPermissions(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.INTERNET
                ).withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport) {

                        val bitmap = takeScreenshot()
                        saveBitmap(bitmap!!)
                        shareIt()

                    }

                    override fun onPermissionRationaleShouldBeShown(
                        permissions: List<PermissionRequest?>?,
                        token: PermissionToken?
                    ) { /* ... */
                    }
                }).check()


        }


    }


    fun getDate(timestamp: Long): String {
        val calendar = Calendar.getInstance(Locale.ENGLISH)
        calendar.timeInMillis = timestamp * 1000L
        val date = DateFormat.format("MMM dd, yyyy 'at' h:mm a", calendar).toString()
        return date
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


    //Share and Save Functions
    fun takeScreenshot(): Bitmap? {
        var rootView2 = getWindow().getDecorView().findViewById<View>(android.R.id.content)

        val rootView =
            window.decorView.findViewById<View>(android.R.id.content)
        rootView2.isDrawingCacheEnabled = true
        return rootView2.drawingCache
    }

    fun saveBitmap(bitmap: Bitmap) {
        imagePath = File(
            Environment.getExternalStorageDirectory().toString() + "/VAXDIAX.png"
        )
        val fos: FileOutputStream
        try {
            fos = FileOutputStream(imagePath)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
            fos.flush()
            fos.close()
        } catch (e: FileNotFoundException) {
            Log.e("GREC", e.message, e)
        } catch (e: IOException) {
            Log.e("GREC", e.message, e)
        }
    }


    private fun shareIt() {

        val codeQrH = intent.getStringExtra("result")
        val uri: Uri = FileProvider.getUriForFile(
            this,
            this.getApplicationContext().getPackageName().toString() + ".provider",
            imagePath!!
        )
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "image/*"
        val shareBody = "VAXDIAX COVID-19 Antibody Rapid Test\n\n" +
                "Date:\n" +
                "$time\n\n" +
                "Test Number:\n" +
                "$testNumber\n\n" +
                "Test Result:\n" +
                "$resShare\n\n" +
                "IgM = $igmFinal\n" +
                "IgG = $iggFinal\n" +
                "IgA = $igaFinal\n"

        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "VAXDIAX COVID-19 Test Result")
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
        sharingIntent.putExtra(Intent.EXTRA_STREAM, uri)
        startActivity(Intent.createChooser(sharingIntent, "Share via"))
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }
}






