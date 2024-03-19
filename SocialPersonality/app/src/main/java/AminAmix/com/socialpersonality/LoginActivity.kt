package AminAmix.com.socialpersonality

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    lateinit var emaiAddress:String
    lateinit var pass:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        //Night Mode Color change

        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> {

                textView2.setTextColor(getResources().getColor(R.color.white));

            }
            Configuration.UI_MODE_NIGHT_NO -> {
            }
        }


        btnLoginInLoginPage.setOnClickListener {
            emaiAddress = editTextTextEmailAddressLogin.text.toString()
            pass = editTextTextPasswordLogin.text.toString()
            //Toast.makeText(this,"$emaiAddress" + " and " + "$pass" ,Toast.LENGTH_LONG).show()
            if (emaiAddress == "aminnaaseri@gmail.com" && pass == "Golmohammadi"){
                val homeIntent = Intent(this, HomeActivity::class.java)
                startActivity(homeIntent)

            }else{
                Toast.makeText(this," Your Email Or password is Incorrect" , Toast.LENGTH_LONG).show()

            }

        }


        txtSingUp.setOnClickListener {
            val registerIntent = Intent(this, RegisterActivity::class.java)
            startActivity(registerIntent)
        }
    }
}