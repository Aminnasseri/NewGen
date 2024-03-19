package AminAmix.com.socialpersonality

import AminAmix.com.socialpersonality.WebService.APIClient
import AminAmix.com.socialpersonality.WebService.APIInterface
import AminAmix.com.socialpersonality.model.MbtiResult
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_mbti_result.*
import kotlinx.android.synthetic.main.activity_mbtitest.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MbtiResultActivity : AppCompatActivity() {
  //  lateinit var token: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mbti_result)
        window.statusBarColor = ContextCompat.getColor(this, R.color.black)
        val preferences: SharedPreferences =
            applicationContext.getSharedPreferences("SOCIAL_PERSONALITY", Context.MODE_PRIVATE)
      val  token = preferences.getString("TOKEN", null).toString() //second parameter default value.
      val tokenB ="Bearer $token"
      val resId = intent.getStringExtra("resId")
      if (resId != null) {
          getPersonalDes(tokenB,resId)
      }




    }

    fun getPersonalDes(token: String, id: String) {
        val apilnterface: APIInterface = APIClient().getClient()!!.create(APIInterface::class.java)

        var call: Call<MbtiResult> = apilnterface.personalityDes(token, id)
        call.enqueue(object : Callback<MbtiResult> {
            override fun onResponse(
                call: Call<MbtiResult>,
                response: Response<MbtiResult>
            ) {
                if (response.code() == 200) {

                    Log.w("RESGetID", "${response.body().toString()}")
                    val dataFromResponse = response.body()
                    txtMbtiType.text = dataFromResponse?.title
                    txtMbtiShortDes.text = dataFromResponse?.shortDesc
                    txtCarrRes.text = dataFromResponse?.career



                    if (dataFromResponse?.imageMale != null && !dataFromResponse.imageMale.isEmpty()) {
                        var imgMale = "http://185.97.117.195:1337" + dataFromResponse?.imageMale.toString()
                        Picasso.get().setLoggingEnabled(true)
                        Picasso.get().load(imgMale)
                            .into(imgMbtiResImage);
                        Log.w("IMG", "${dataFromResponse?.imageMale.toString()}")


                    } else {
                        profile_image.setImageDrawable(
                            ContextCompat.getDrawable(
                                this@MbtiResultActivity,
                                R.drawable.profile_women
                            )
                        )
                    }



                    runOnUiThread {
                        val layoutManager = LinearLayoutManager(
                            this@MbtiResultActivity,
                            LinearLayoutManager.HORIZONTAL,
                            false
                        )

                        val layoutManager2 = LinearLayoutManager(
                            this@MbtiResultActivity,
                            LinearLayoutManager.HORIZONTAL,
                            false
                        )
                        resStr.layoutManager = layoutManager
                        resWeakness.layoutManager = layoutManager2
                        resStr.adapter = dataFromResponse?.let { MbtiAdvantagesAdapter(it) }
                        resWeakness.adapter = dataFromResponse?.let { MbtiWeaknessAdapter(it) }
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
                        Log.i("TAGERRor",jObjError.getString("message"))

                    } catch (e: Exception) {
                        Toast.makeText(applicationContext, e.message, Toast.LENGTH_LONG).show()
                        Log.i("TAGERRor",e.message.toString())
                    }

                }
//                if (response.code() == 401) {
//                    try {
//                        val preferences: SharedPreferences =
//                            applicationContext.getSharedPreferences(
//                                "SOCIAL_PERSONALITY",
//                                Context.MODE_PRIVATE
//                            )
//                        preferences.edit().putString("TOKEN", "").apply()
//                        val jObjError = JSONObject(response.errorBody()!!.string())
//                        Toast.makeText(
//                            applicationContext,
//                            jObjError.getString("message"),
//                            Toast.LENGTH_LONG
//                        ).show()
//
//                    } catch (e: Exception) {
//                        Toast.makeText(applicationContext, e.message, Toast.LENGTH_LONG).show()
//                        Log.i("TAGERRor",e.message.toString())
//
//                        val intent = Intent(this@MbtiResultActivity, FirstPageActivity::class.java)
//                        startActivity(intent)
//                        finish()
//                    }
//
//                }
            }

            override fun onFailure(call: Call<MbtiResult>, t: Throwable) {

                Toast.makeText(
                    this@MbtiResultActivity,
                    " Register is not ok",
                    Toast.LENGTH_LONG
                )
                    .show()
            }

        })
    }

}