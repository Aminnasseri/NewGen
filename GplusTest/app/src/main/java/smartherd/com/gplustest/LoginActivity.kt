package smartherd.com.gplustest

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import androidx.core.app.ActivityCompat.startActivityForResult

import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.facebook.CallbackManager
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.google.android.gms.common.api.ApiException
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*
import com.facebook.FacebookException

import com.facebook.login.LoginResult

import com.facebook.FacebookCallback

import com.facebook.login.LoginManager
import org.json.JSONObject

import com.facebook.GraphResponse

import com.facebook.GraphRequest

import com.facebook.AccessToken
import com.facebook.GraphRequest.GraphJSONObjectCallback
import org.json.JSONException


class LoginActivity : AppCompatActivity() {
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var callbackManager: CallbackManager

    private val EMAIL = "email"

    companion object {
        const val RC_SIGN_IN = 120
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        //Facebook login
        btnFB.setReadPermissions(Arrays.asList(EMAIL));




        callbackManager = CallbackManager.Factory.create()
        
        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult?> {
                override fun onSuccess(loginResult: LoginResult?) {
                    val request = GraphRequest.newMeRequest(
                        loginResult!!.accessToken
                    ) { `object`, response -> //Read the data you need from the GraphResponse here like this:
                        try {
                            val firstName = response.jsonObject.getString("first_name")
                            val lastName = response.jsonObject.getString("last_name")
                            val email = response.jsonObject.getString("email")
                            val id = response.jsonObject.getString("id")
                            val picture =
                                response.jsonObject.getJSONObject("picture").getJSONObject("data")
                                    .getString("url")

                            Log.w(
                                "Aniee",
                                "Email: $email" + " " + "Name: $firstName" + " " + "LastName: $lastName" + " " + "ID: $id" + " " + "Pic: $picture" + " "
                            )

                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                        val intent = Intent(applicationContext, MainActivity::class.java)
                        startActivity(intent)
                    }

                    val parameters = Bundle()
                    parameters.putString(
                        "fields",
                        "id,name,email,first_name,last_name,picture.width(150).height(150)"
                    )
                    request.parameters = parameters
                    request.executeAsync()




                    Toast.makeText(this@LoginActivity, "Facbook OK", Toast.LENGTH_LONG).show()
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

            val loginIntent = Intent(this, FirstPageActivity::class.java)
            startActivity(loginIntent)

            // Signed in successfully, show authenticated UI.
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("TAG", "signInResult:failed code=" + e.statusCode)
        }
    }

}