package AminAmix.com.socialpersonality

import AminAmix.com.socialpersonality.WebService.APIClient
import AminAmix.com.socialpersonality.WebService.APIInterface
import AminAmix.com.socialpersonality.model.ResponseRegister
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RegisterActivity : AppCompatActivity() {
    lateinit var name: String
    lateinit var pass: String
    lateinit var emaiAddress: String
    private var secureKey = "HRHaYYCfqr8XkdEZCgWRDh4KknXe82"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)


        txtSingIn.setOnClickListener {
            val SingInIntent = Intent(this, LoginActivity::class.java)
            startActivity(SingInIntent)
        }
//
//        btnSingUpRegister.setEnabled(true)
//        progressRegister.visibility = View.INVISIBLE

        btnSingUpRegister.setOnClickListener {
            btnSingUpRegister.isEnabled = false
            progressRegister.visibility = View.VISIBLE

            emaiAddress = editTextTextEmailAddress.text.toString()
            // Check if email id is valid or not
            if (!isEmailValid(emaiAddress)) {
                Toast.makeText(
                    this@RegisterActivity,
                    "Pleas Enter The valid Email",
                    Toast.LENGTH_LONG
                )
                    .show()
            } else {
                btnSingUpRegister.isEnabled = false
                progressRegister.visibility = View.VISIBLE
                register(secureKey, emaiAddress)
            }


        }
    }


    fun register(secureKey: String, email: String) {

        Handler().postDelayed(
                Runnable {
                    btnSingUpRegister.isEnabled = true
                         },
                2200 //Specific time in milliseconds
            )

        val apilnterface: APIInterface = APIClient().getClient()!!.create(APIInterface::class.java)

        var call: Call<ResponseRegister> = apilnterface.registerUser(secureKey, email)
        call.enqueue(object : Callback<ResponseRegister> {
            override fun onResponse(
                call: Call<ResponseRegister>,
                response: Response<ResponseRegister>
            ) {
                if (response.code() == 200) {

                    progressRegister.visibility = View.INVISIBLE


                    Log.w("RES", "${response.body().toString()}")

                    val dataFromResponse = response.body()
                    val token = dataFromResponse?.token.toString()
                    val registerMode = dataFromResponse?.type.toString()
                    Log.w("REGMode", registerMode)
                    Log.w("tokenee", token)

                    val intent =
                        Intent(this@RegisterActivity, EmailConfirmationActivity::class.java)
                    intent.putExtra("user_email", emaiAddress)
                    intent.putExtra("registerMode", registerMode)
                    intent.putExtra("token", token)
                    startActivity(intent)

                }

                if (response.code() == 400) {

                    progressRegister.visibility = View.INVISIBLE

                    try {
                        val jObjError = JSONObject(response.errorBody()!!.string())
                        Toast.makeText(
                            applicationContext,
                            jObjError.getString("message"),
                            Toast.LENGTH_LONG
                        ).show()
                    } catch (e: Exception) {
                        Toast.makeText(applicationContext, e.message, Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onFailure(call: Call<ResponseRegister>, t: Throwable) {

                Toast.makeText(this@RegisterActivity, " Register is not ok", Toast.LENGTH_LONG)
                    .show()
            }

        })
    }

    fun isEmailValid(email: CharSequence?): Boolean {
        btnSingUpRegister.isEnabled = true
        progressRegister.visibility = View.INVISIBLE

        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}