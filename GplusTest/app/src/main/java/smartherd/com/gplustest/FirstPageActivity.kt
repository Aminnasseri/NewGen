package smartherd.com.gplustest

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.material.internal.ContextUtils.getActivity
import kotlinx.android.synthetic.main.activity_first_page.*
import android.graphics.BitmapFactory

import android.graphics.Bitmap
import android.widget.Toast
import java.net.URL
import androidx.annotation.NonNull
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

import com.google.android.gms.tasks.OnCompleteListener
import android.accounts.Account
import android.app.Person
import android.content.Intent


class FirstPageActivity : AppCompatActivity() {

    private lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_page)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        btnsSignOut.setOnClickListener {
            signOut()
        }

        btnStart.setOnClickListener {
            startActivity(Intent(this,MainActivity2::class.java))
        }


        val acct = GoogleSignIn.getLastSignedInAccount(this)
        if (acct != null) {
            val personName = acct.displayName
            val personGivenName = acct.givenName
            val personFamilyName = acct.familyName
            val personEmail = acct.email
            val personId = acct.id
            val personToken = acct.idToken

            if (acct.photoUrl != null) {
                val  personPhoto :Uri = acct.photoUrl
                txtPhone.text = personPhoto!!.toString()
                Glide.with(this).load(personPhoto).into(profile_image);
            }





            Log.w(
                "MyDataBaby",
                "$personName" + " " + "$personGivenName" + " " + "$personFamilyName" + " " + "$personEmail" + " " + "$personId"
                        + " " + "${personToken.toString()}")

            txtId.text = personId
            txtName.text = personGivenName +" " + personFamilyName
            txtEmail.text = personEmail

            txtToken.text = personToken








        }
//
//        val firstName = intent.getStringExtra("first_name").toString()
//        val lastName = intent.getStringExtra("last_name").toString()
//        val email = intent.getStringExtra("email").toString()
//        val id = intent.getStringExtra("id").toString()
//        val imageURi = intent.getStringExtra("url").toString()


    }
    private fun signOut() {
        mGoogleSignInClient.signOut()
            .addOnCompleteListener(this, OnCompleteListener<Void?> {
                // ...
                Toast.makeText(this,"You Singing Out Successfully",Toast.LENGTH_LONG).show()
                finish()

            })
    }
}