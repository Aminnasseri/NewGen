package com.zibfit.diax

import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.activity_location_radius.*
import kotlinx.android.synthetic.main.toolbar_open_kit.*

private lateinit var backToast: Toast

class LocationRadiusActivity : AppCompatActivity() {
    companion object{
        const val Location = "LOCATION"
        const val LAT = "LAT"
        const val LONG = "LONG"

    }
    var lat: Float = 0.0F
    var lon: Float = 0.0F
    lateinit var mFusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_radius)

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

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

        btnNextLocation.setBackgroundTintList(
            ContextCompat.getColorStateList(
                this,
                R.color.colorAccent
            )
        )

        btnNextLocation.setOnClickListener {


            Dexter.withActivity(this)
                .withPermissions(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                )
                .withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport) { // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {

                            mFusedLocationClient.lastLocation.addOnCompleteListener(this@LocationRadiusActivity) { task ->
                                var location: Location? = task.result
                                if (location == null) {

                                } else {
                                    lat = location.latitude.toFloat()
                                    lon = location.longitude.toFloat()
                                    val editor = getSharedPreferences(
                                        Location,
                                        Context.MODE_PRIVATE
                                    ).edit()
                                    editor.putString(LAT, lat.toString())
                                    editor.putString(LONG, lon.toString())
                                    editor.apply()


                                }
                            }



                            startActivity(
                                Intent(
                                    this@LocationRadiusActivity,
                                    TakeBloodActivity::class.java
                                )
                            )
                            finish()
                        }
                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied) {
                            Toast.makeText(
                                this@LocationRadiusActivity,
                                "You Must enable Location permission",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        permissions: List<PermissionRequest>,
                        token: PermissionToken
                    ) {
                        token.continuePermissionRequest()
                    }
                })
                .onSameThread()
                .check()


        }


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
