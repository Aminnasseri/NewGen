package smartherd.com.gplustest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
     val user = 1

        Handler().postDelayed({
            if (user != 1) {
                val dashboardIntent = Intent(this, FirstPageActivity::class.java)
                startActivity(dashboardIntent)
                finish()
            } else {
                val singInIntent = Intent(this, LoginActivity::class.java)
                startActivity(singInIntent)
                finish()
            }


        }, 2000)
    }
}