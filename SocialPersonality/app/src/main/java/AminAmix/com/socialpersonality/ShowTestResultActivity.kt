package AminAmix.com.socialpersonality

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_show_test_result.*

class ShowTestResultActivity : AppCompatActivity() {
    var i = ""
    var e = ""
    var n = ""
    var t = ""
    var j = ""
    var p = ""
    var f = ""
    var s = ""
    var type = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_test_result)
        window.statusBarColor = ContextCompat.getColor(this, R.color.blue_main)

        btnHomeRes.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        i = intent.getStringExtra("i").toString()
        e = intent.getStringExtra("e").toString()
        n = intent.getStringExtra("n").toString()
        t = intent.getStringExtra("t").toString()
        j = intent.getStringExtra("j").toString()
        p = intent.getStringExtra("p").toString()
        f = intent.getStringExtra("f").toString()
        s = intent.getStringExtra("s").toString()
        type = intent.getStringExtra("type").toString()
        txtResE.text = type





        txtE.text = e + " %"
        txtF.text = f + " %"
        txtN.text = n + " %"
        txtTT.text = t + " %"
        txtJ.text = j + " %"
        txtP.text = p + " %"
        txtF.text = f + " %"
        txtS.text = s + " %"
        txtI.text = i + " %"


    }

    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()

        Handler(Looper.getMainLooper()).postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
    }
}