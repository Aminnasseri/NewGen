package com.zibfit.diax
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        changeColor(R.color.colorAccent);

            val background = object : Thread() {
                override fun run() = try {
                    Thread.sleep(1000)

                    startActivity(Intent(this@SplashActivity,MainActivity::class.java))
                } catch (e: Exception) {
                    println(e)
                }
            }
            background.start()






    }
    fun isConnectedToNetwork(): Boolean {
        val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        return connectivityManager?.activeNetworkInfo?.isConnectedOrConnecting() ?: false
    }

    //change status bar color for a page
    fun changeColor(resourseColor: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(
                applicationContext,
                resourseColor
            )
        }
        val bar: ActionBar? = supportActionBar
        bar?.setBackgroundDrawable(ColorDrawable(resources.getColor(resourseColor)))
    }

}

