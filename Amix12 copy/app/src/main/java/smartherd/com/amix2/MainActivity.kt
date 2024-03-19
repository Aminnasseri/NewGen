package smartherd.com.amix2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
//    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        mAuth = FirebaseAuth.getInstance()
//        val user = mAuth.currentUser
//
//        Handler().postDelayed({
//            if (user != null) {
//                val dashboardIntent = Intent(this, MbtiFirstActivity::class.java)
//                startActivity(dashboardIntent)
//                finish()
//            } else {
//                val singInIntent = Intent(this, SingInActivity::class.java)
//                startActivity(singInIntent)
//                finish()
//            }
//
//
//        }, 2000)
    }
}