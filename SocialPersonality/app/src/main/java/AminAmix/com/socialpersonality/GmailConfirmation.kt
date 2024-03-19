package AminAmix.com.socialpersonality

import AminAmix.com.socialpersonality.WebService.APIClient
import AminAmix.com.socialpersonality.WebService.APIInterface
import AminAmix.com.socialpersonality.model.Users
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.android.synthetic.main.activity_home.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GmailConfirmation : AppCompatActivity() {
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    lateinit var token: String
    lateinit var id: String
    lateinit var fullName: String
    lateinit var emailStatus: String
    lateinit var personalityType: String
    lateinit var sex: String
    lateinit var emailAdress: String
    private var secureKey ="HRHaYYCfqr8XkdEZCgWRDh4KknXe82"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gmail_confirmation)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        val acct = GoogleSignIn.getLastSignedInAccount(this)
            var profileImage = ""
            if (acct != null) {
                val emaiAddress = acct.email.toString()
                val fullName = acct.displayName
                val sex = "male"
                var source = "google"

                val profileImage: String? = if (acct.photoUrl != null) acct.photoUrl.toString() else "null"


                register(secureKey,emaiAddress, fullName, profileImage!! , sex, source)
            } else {
                Toast.makeText(this, "nulllllll", Toast.LENGTH_SHORT).show()

            }



    }
    private fun register(
        secureKey:String,
        email: String,
        fullName: String,
        profileImage: String,
        sex: String,
        source: String
    ) {
        val apilnterface: APIInterface = APIClient().getClient()!!.create(APIInterface::class.java)

        var call: Call<Users> =
            apilnterface.registerUserOauth(secureKey,email, fullName, profileImage, sex, source)
        call.enqueue(object : Callback<Users> {
            override fun onResponse(call: Call<Users>, response: Response<Users>) {
                if (response.code() == 200) {

                    Log.w("RES", "${response.body().toString()}")
                    val dataFromResponse = response.body()
                    val token = dataFromResponse?.token.toString()

                    val preferences: SharedPreferences =
                        applicationContext.getSharedPreferences("SOCIAL_PERSONALITY", Context.MODE_PRIVATE)
                    preferences.edit().putString("TOKEN", token).apply()

                    val intent = Intent(this@GmailConfirmation, HomeActivity::class.java)

                    startActivity(intent)
                    finish()




                }
                if (response.code() == 400) {
                    try {
                        val jObjError = JSONObject(response.errorBody()!!.string())
                        Toast.makeText(
                            applicationContext,
                            jObjError.getString("message"),
                            Toast.LENGTH_LONG
                        ).show()
                        val erMsg = jObjError.getString("message")
                        Log.w("Error1", erMsg.toString())
                        val intent = Intent(this@GmailConfirmation, FirstPageActivity::class.java)

                        startActivity(intent)
                        finish()

                    } catch (e: Exception) {
                        Toast.makeText(applicationContext, e.message, Toast.LENGTH_LONG).show()
                        Log.w("Errore", e.message.toString())
                        val intent = Intent(this@GmailConfirmation, FirstPageActivity::class.java)

                        startActivity(intent)
                        finish()
                    }

                }
            }

            override fun onFailure(call: Call<Users>, t: Throwable) {

                Toast.makeText(this@GmailConfirmation, " Register is not ok", Toast.LENGTH_LONG)
                    .show()
            }

        })
    }
}