package AminAmix.com.socialpersonality

import AminAmix.com.socialpersonality.WebService.APIClient
import AminAmix.com.socialpersonality.WebService.APIInterface
import AminAmix.com.socialpersonality.model.User2
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class HomeActivity : AppCompatActivity() {

    private lateinit var mGoogleSignInClient: GoogleSignInClient

    lateinit var token: String
    lateinit var id: String
    lateinit var fullName: String
    lateinit var emailStatus: String
    lateinit var personalityType: String
    lateinit var sex: String
    lateinit var emailAdress: String
    lateinit var resultId: String
    lateinit var resultIdTitle: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)
        val preferences: SharedPreferences =
            applicationContext.getSharedPreferences("SOCIAL_PERSONALITY", Context.MODE_PRIVATE)
        token = preferences.getString("TOKEN", null).toString() //second parameter default value.

        cardViewHome.setOnClickListener {
            val intent = Intent(this, MbtiResultActivity::class.java)
            intent.putExtra("resId",resultId)
            startActivity(intent)
        }




        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        val acct = GoogleSignIn.getLastSignedInAccount(this)





        if (token != null) {
            token = "Bearer $token"
            getUser(token)
            Log.i("TokenHome", token)

        }




        btnStart.setOnClickListener {
            val intent = Intent(this, MbtiIntro::class.java)
            startActivity(intent)
        }



        btnsSignOut.setOnClickListener {
            signOut()
        }
    }

    private fun signOut() {
        val preferences: SharedPreferences =
            applicationContext.getSharedPreferences("SOCIAL_PERSONALITY", Context.MODE_PRIVATE)
        // preferences.edit().clear()
        preferences.edit().putString("TOKEN", "").apply()


        mGoogleSignInClient.signOut()
            .addOnCompleteListener(this, OnCompleteListener<Void?> {
                // ...
                val intent = Intent(this, FirstPageActivity::class.java)
                startActivity(intent)
                //  Toast.makeText(this, "You Singing Out Successfully", Toast.LENGTH_LONG).show()
                finish()

            })
    }


    fun getUser(token: String) {
        val apilnterface: APIInterface = APIClient().getClient()!!.create(APIInterface::class.java)

        var call: Call<User2> = apilnterface.getUser(token)
        call.enqueue(object : Callback<User2> {
            override fun onResponse(
                call: Call<User2>,
                response: Response<User2>
            ) {
                if (response.code() == 200) {


                    val dataFromResponse = response.body()

                    emailStatus = dataFromResponse?.emailStatus.toString()
                    emailAdress = dataFromResponse?.emailAddress.toString()
                    id = dataFromResponse?.id.toString()
                    fullName = dataFromResponse?.fullName.toString()
                   // personalityType = dataFromResponse?.personalityType.toString()
                    sex = dataFromResponse?.sex.toString()
                    txtName.text = fullName
                    txtPhone.text = emailStatus
                    txtEmail.text = emailAdress
                    Log.d("RESGetID", "${response.body().toString()}")
                    if (dataFromResponse?.pType != null) {
                        resultIdTitle = dataFromResponse.pType.title
                        resultId = dataFromResponse.pType.id.toString()
                        txtType.text = resultIdTitle
                        Log.d("resID", resultId.toString())

                        txtToken.text = getDateTime(dataFromResponse.lastSeenAt.toString())
                        txtPhone.text =dataFromResponse.pType.shortDesc


                        //  getPersonalDes(token, resultId)

                    }
                    if (dataFromResponse?.profileImage != null && !dataFromResponse.profileImage.isEmpty()) {
                        Picasso.get().load(dataFromResponse?.profileImage.toString())
                            .into(profile_image);

                    } else {
                        profile_image.setImageDrawable(
                            ContextCompat.getDrawable(
                                this@HomeActivity,
                                R.drawable.profile_women
                            )
                        )
                    }


                }
                if (response.code() == 400) {
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
                if (response.code() == 401) {
                    try {
                        val preferences: SharedPreferences =
                            applicationContext.getSharedPreferences(
                                "SOCIAL_PERSONALITY",
                                Context.MODE_PRIVATE
                            )
                        preferences.edit().putString("TOKEN", "").apply()
                        val jObjError = JSONObject(response.errorBody()!!.string())
                        Toast.makeText(
                            applicationContext,
                            jObjError.getString("message"),
                            Toast.LENGTH_LONG
                        ).show()

                    } catch (e: Exception) {
                        Toast.makeText(applicationContext, e.message, Toast.LENGTH_LONG).show()
                        val intent = Intent(this@HomeActivity, FirstPageActivity::class.java)
                        startActivity(intent)
                        finish()
                    }

                }
            }

            override fun onFailure(call: Call<User2>, t: Throwable) {

                Toast.makeText(
                    this@HomeActivity,
                    " Register is not ok",
                    Toast.LENGTH_LONG
                )
                    .show()
            }

        })
    }


    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()

        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            doubleBackToExitPressedOnce = false
        }, 2000)
    }
    @SuppressLint("SimpleDateFormat")
    private fun getDateTime(s: String): String? {
        try {
            val sdf = SimpleDateFormat("MM/dd/yyyy")
            val netDate = Date(s.toLong() * 1000)
            return sdf.format(netDate)
        } catch (e: Exception) {
            return e.toString()
        }
    }

}