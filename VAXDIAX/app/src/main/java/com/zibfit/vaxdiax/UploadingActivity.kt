package com.zibfit.vaxdiax

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_uploading.*
private lateinit var backToast: Toast
var enableFlag = 0
class UploadingActivity : AppCompatActivity() {

    var secondaryHandler: Handler? = Handler()
    var firstProgressStatus = 0
    var secondaryProgressStatus = 0
    var thirdProgressStatus = 0
    var finalProgress = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_uploading)
        startSuccessProgress()
        startDataProgress()
        startEncProgress()
        startUploadProgress()
        btnResult.setOnClickListener {
            val qrcode = intent.getStringExtra("qr")
            val timeServer = intent.getStringExtra("time")
            val result = intent.getStringExtra("result")
            val confidence = intent.getStringExtra("confidence")
            val test = intent.getStringExtra("test")
            val igm = intent.getStringExtra("igm")
            val iga = intent.getStringExtra("iga")
            val igg = intent.getStringExtra("igg")

            val intent =
                Intent(this, HistoryResultActivity::class.java)


            intent.putExtra("qr", qrcode)
            intent.putExtra("time", timeServer)
            intent.putExtra("result", result)
            intent.putExtra("confidence", confidence)
            intent.putExtra("test", test)
            intent.putExtra("igm", igm)
            intent.putExtra("iga", iga)
            intent.putExtra("igg", igg)


            startActivity(intent)
            finish()
        }


    }

    fun startSuccessProgress() {
        Thread(Runnable {
            while (firstProgressStatus < 100) {
                firstProgressStatus += 1

                try {
                    Thread.sleep(50)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

                secondaryHandler?.post {
                    progressBarSuccess.setSecondaryProgress(firstProgressStatus)
                    txtSuccessPer.setText("$firstProgressStatus%")

                    if (firstProgressStatus == 100) {
                        txtSuccessPer.setText("complete")
                        txtSuccessPer.setTextColor(Color.parseColor("#AF126F"))


                    }
                }
            }
        }).start()
    }


    fun startDataProgress() {
        Thread(Runnable {
            while (secondaryProgressStatus < 100) {
                secondaryProgressStatus += 1

                try {
                    Thread.sleep(60)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

                secondaryHandler?.post {
                    progressBarDataAnony.setSecondaryProgress(secondaryProgressStatus)
                    txtSuccessData.setText("$secondaryProgressStatus%")

                    if (secondaryProgressStatus == 100) {
                        txtSuccessData.setText("complete")
                        txtSuccessData.setTextColor(Color.parseColor("#AF126F"))


                    }
                }
            }
        }).start()
    }


    fun startEncProgress() {
        Thread(Runnable {
            while (thirdProgressStatus < 100) {
                thirdProgressStatus += 1

                try {
                    Thread.sleep(70)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

                secondaryHandler?.post {
                    progressBarEnc.setSecondaryProgress(thirdProgressStatus)
                    txtSuccessEnc.setText("$thirdProgressStatus%")

                    if (thirdProgressStatus == 100) {
                        txtSuccessEnc.setText("complete")
                        txtSuccessEnc.setTextColor(Color.parseColor("#AF126F"))


                    }
                }
            }
        }).start()
    }


    fun startUploadProgress() {
        Thread(Runnable {
            while (finalProgress < 100) {
                finalProgress += 1

                try {
                    Thread.sleep(100)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

                secondaryHandler?.post {
                    progressBarUpload.setSecondaryProgress(finalProgress)
                    txtSuccessUpload.setText("$finalProgress%")

                    if (finalProgress == 100) {
                        txtSuccessUpload.setText("Success")
                        txtSuccessUpload.setTextColor(Color.parseColor("#AF126F"))

                        btnResult.isEnabled = true

                    }
                }
            }
        }).start()
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
