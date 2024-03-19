package AminAmix.com.socialpersonality

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_mbti_intro.*

class MbtiIntro : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mbti_intro)
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)


        btnStartIntro.setOnClickListener {
            val intent = Intent(this, MBTITEST::class.java)
            startActivity(intent)
            finish()
        }

    }
}