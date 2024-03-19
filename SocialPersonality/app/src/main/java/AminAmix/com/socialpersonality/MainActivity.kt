package AminAmix.com.socialpersonality

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import java.net.InetAddress
import java.net.UnknownHostException


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        lateinit var token: String

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.statusBarColor = ContextCompat.getColor(this, R.color.blue)

        val preferences: SharedPreferences =
            applicationContext.getSharedPreferences("SOCIAL_PERSONALITY", Context.MODE_PRIVATE)
        token = preferences.getString("TOKEN", "").toString() //second parameter default value.
        Log.w("TESTTOKEN" , token.toString())





        Handler().postDelayed({
            if(isNetworkAvailable(this)){
                if ( token.isNullOrEmpty() ) {
                    Log.w("TESTTOKEN2" , token.toString())

                    val mIntent = Intent(this, FirstPageActivity::class.java)
                    startActivity(mIntent)
                    finish()

                }else{
                    val mIntent = Intent(this, HomeActivity::class.java)
                    startActivity(mIntent)
                    finish()
                }


            }else{
                Toast.makeText(this,"Check Your internet Conection",Toast.LENGTH_LONG).show()
            }

        }, 3000)
    }

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!
            .isConnected
    }

    fun isInternetAvailable(): Boolean {
        try {
            val address: InetAddress = InetAddress.getByName("www.google.com")
            return !address.equals("")
        } catch (e: UnknownHostException) {
            // Log error
        }
        return false
    }
}