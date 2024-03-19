package AminAmix.com.socialpersonality

import AminAmix.com.socialpersonality.WebService.APIClient
import AminAmix.com.socialpersonality.WebService.APIInterface
import AminAmix.com.socialpersonality.model.Users
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_email_confirmation.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_personal_information.*
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PersonalInformationActivity : AppCompatActivity() {
    lateinit var tokenOrg: String
    lateinit var fullName: String
    val REQUEST_CODE = 100




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_information)
        progressbarPersonalInfo.visibility = View.INVISIBLE
        tokenOrg = intent.getStringExtra("personal_token").toString()

       var tokenBAr = "Bearer $tokenOrg"

        var sexVal =""


        radioGroupSex.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.rbMale) {
                Log.i("Radio", "0")
                sexVal = "male"

            }
            if (checkedId == R.id.rdFemale) {
                Log.i("Radio", "1")
                sexVal = "female"

            }
        }


        btnInfo.setOnClickListener {
            progressbarPersonalInfo.visibility = View.VISIBLE
            btnInfo.isEnabled =false
            fullName = editTextTextPersonName.text.toString()
            postInfo(tokenBAr,fullName,sexVal)
        }
        btnEditImage.setOnClickListener {  openGalleryForImage() }


    }

    fun postInfo(token: String, fullName: String,sex:String) {
        Handler().postDelayed(
            Runnable {
                btnInfo.isEnabled = true
            },
            2200 //Specific time in milliseconds
        )

        val apilnterface: APIInterface = APIClient().getClient()!!.create(APIInterface::class.java)

        var call: Call<Users> = apilnterface.postInfo(token, fullName,sex)
        call.enqueue(object : Callback<Users> {
            override fun onResponse(
                call: Call<Users>,
                response: Response<Users>
            ) {
                if (response.code() == 200) {
                    val preferences: SharedPreferences =
                        applicationContext.getSharedPreferences("SOCIAL_PERSONALITY", Context.MODE_PRIVATE)
                    preferences.edit().putString("TOKEN", tokenOrg).apply()
                    Log.i("TokenHome1",tokenOrg)
                    progressbarPersonalInfo.visibility = View.INVISIBLE

                    Log.w("RES", "${response.body().toString()}")

                    val dataFromResponse = response.body()

                    val intent = Intent(this@PersonalInformationActivity, HomeActivity::class.java)
                    startActivity(intent)
                    finish()


                }
                if (response.code() == 400) {
                    progressbarPersonalInfo.visibility = View.INVISIBLE

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

            override fun onFailure(call: Call<Users>, t: Throwable) {

                Toast.makeText(
                    this@PersonalInformationActivity,
                    " Register is not ok",
                    Toast.LENGTH_LONG
                )
                    .show()
            }

        })
    }
    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE){
            profile_image.setImageURI(data?.data) // handle chosen image
        }
    }

}