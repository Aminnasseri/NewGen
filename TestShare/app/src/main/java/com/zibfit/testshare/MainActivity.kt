package com.zibfit.testshare

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException


class MainActivity : AppCompatActivity() {

    var imagePath: File? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        btn.setOnClickListener {

            Dexter.withContext(this)
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

    fun takeScreenshot(): Bitmap? {
        var rootView2 = getWindow().getDecorView().findViewById<View>(android.R.id.content)

        val rootView =
            window.decorView.findViewById<View>(android.R.id.content)
        rootView2.isDrawingCacheEnabled = true
        return rootView2.drawingCache
    }

    fun saveBitmap(bitmap: Bitmap) {
        imagePath = File(
            Environment.getExternalStorageDirectory().toString() + "/screenshot.png"
        )
        val fos: FileOutputStream
        try {
            fos = FileOutputStream(imagePath)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            fos.flush()
            fos.close()
        } catch (e: FileNotFoundException) {
            Log.e("GREC", e.message, e)
        } catch (e: IOException) {
            Log.e("GREC", e.message, e)
        }
    }

    private fun shareIt() {


        val uri: Uri = FileProvider.getUriForFile(
            this,
            this.getApplicationContext().getPackageName().toString() + ".provider",
            imagePath!!
        )
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "image/*"
        val shareBody = "IgG=Pos\n" +
                "IgA = Neg\n" +
                "IgM = Pos\n" +
                "Test result Code = 34343343421566"
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Test result Code = 34343343421566")
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
        sharingIntent.putExtra(Intent.EXTRA_STREAM, uri)
        startActivity(Intent.createChooser(sharingIntent, "Share via"))
    }
}