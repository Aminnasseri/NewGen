package smartherd.com.amix2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_mbti_first.*

class MbtiFirstActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mbti_first)



        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser

        txtId.text = currentUser?.uid
        txtName.text = currentUser?.displayName
        txtEmail.text = currentUser?.email
        txtPhone.text = currentUser?.phoneNumber



        Glide.with(this).load(currentUser?.photoUrl).into(profile_image);
        Log.w("Photo", currentUser?.phoneNumber.toString())

    }
}