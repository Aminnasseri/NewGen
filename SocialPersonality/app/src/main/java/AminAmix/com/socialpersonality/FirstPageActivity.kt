package AminAmix.com.socialpersonality

import AminAmix.com.socialpersonality.WebService.APIClient
import AminAmix.com.socialpersonality.WebService.APIInterface
import AminAmix.com.socialpersonality.model.Users
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.activity_first_page.*
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class FirstPageActivity : AppCompatActivity() {

    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var callbackManager: CallbackManager
    private val EMAIL = "email"
    private var secureKey ="HRHaYYCfqr8XkdEZCgWRDh4KknXe82"



    companion object {
        const val RC_SIGN_IN = 120
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_page)
        window.statusBarColor = ContextCompat.getColor(this, R.color.black)



        textView3.setOnClickListener {
            val intent1 = Intent(this, TestActivity::class.java)
            startActivity(intent1)
        }

        //initialize Facebook SDK
        login_button.setReadPermissions(Arrays.asList(EMAIL));




        callbackManager = CallbackManager.Factory.create()

        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult?> {
                override fun onSuccess(loginResult: LoginResult?) {

                    val request = GraphRequest.newMeRequest(
                        loginResult!!.accessToken
                    ) { `object`, response -> //Read the data you need from the GraphResponse here like this:
                        try {
                            val firstName = response.jsonObject.getString("first_name").toString()
                            val lastName = response.jsonObject.getString("last_name").toString()
                            val fullName = firstName + " " + lastName .toString()
                          val  emailAdress = response.jsonObject.getString("email").toString()
                           val id = response.jsonObject.getString("id").toString()
                           val userPic =
                                response.jsonObject.getJSONObject("picture").getJSONObject("data")
                                    .getString("url").toString()
                            val source = "facebook"
                            registerFacebook(secureKey,emailAdress,fullName,userPic,"male",source)


                            Log.w(
                                "Aniee",
                                "Email: $emailAdress" + " " + "Name: $fullName" + " " + "UserPic: $userPic" + " " + "ID: $id" + " " + "Source: $source" + " "
                            )


                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }

                    }

                    val parameters = Bundle()
                    parameters.putString(
                        "fields",
                        "id,name,email,first_name,last_name,picture.width(150).height(150)"
                    )
                    request.parameters = parameters
                    request.executeAsync()

                    Log.w("FACEBOOKRES", loginResult?.accessToken!!.userId)

                }

                override fun onCancel() {
                    // App code
                }

                override fun onError(exception: FacebookException) {
                    // App code
                }
            })
        val signInButton = findViewById<SignInButton>(R.id.btnSign)
        signInButton.setSize(SignInButton.SIZE_STANDARD)

        signInButton.setOnClickListener {
            signIn()
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        btnLogin.setOnClickListener {
            val loginIntent = Intent(this, LoginActivity::class.java)
            startActivity(loginIntent)

        }

        btnRegister.setOnClickListener {
            val registerIntent = Intent(this, RegisterActivity::class.java)
            startActivity(registerIntent)

        }


    }


    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)

        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

            val loginIntent = Intent(this, GmailConfirmation::class.java)
            startActivity(loginIntent)
            finish()

            // Signed in successfully, show authenticated UI.
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("TAG", "signInResult:failed code=" + e.statusCode)
        }

    }

    private fun registerFacebook(
        secureKey:String,
        email: String,
        fullName: String,
        profileImage: String,
        sex: String,
        source: String
    ) {
        val apilnterface: APIInterface = APIClient().getClient()!!.create(APIInterface::class.java)

        var call: Call<Users> =
            apilnterface.registerUserOauth(secureKey,email,fullName,profileImage,sex,source)
        call.enqueue(object : Callback<Users> {
            override fun onResponse(call: Call<Users>, response: Response<Users>) {
                if (response.code() == 200) {

                    Log.w("RES", "${response.body().toString()}")
                    val dataFromResponse = response.body()
                    val token = dataFromResponse?.token.toString()

                    val preferences: SharedPreferences =
                        applicationContext.getSharedPreferences(
                            "SOCIAL_PERSONALITY",
                            Context.MODE_PRIVATE
                        )
                    preferences.edit().clear().apply()
                    preferences.edit().putString("TOKEN", token).apply()

                    val intent = Intent(this@FirstPageActivity, HomeActivity::class.java)

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
                        val intent = Intent(this@FirstPageActivity, FirstPageActivity::class.java)

                        startActivity(intent)
                        finish()

                    } catch (e: Exception) {
                        Toast.makeText(applicationContext, e.message, Toast.LENGTH_LONG).show()
                        Log.w("Errore", e.message.toString())
                        val intent = Intent(this@FirstPageActivity, FirstPageActivity::class.java)

                        startActivity(intent)
                        finish()
                    }

                }

            }

            override fun onFailure(call: Call<Users>, t: Throwable) {

                Toast.makeText(this@FirstPageActivity, " Register is not ok", Toast.LENGTH_LONG)
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

        Handler(Looper.getMainLooper()).postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
    }


}