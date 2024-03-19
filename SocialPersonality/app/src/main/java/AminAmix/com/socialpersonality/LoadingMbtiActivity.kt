package AminAmix.com.socialpersonality

import AminAmix.com.socialpersonality.WebService.APIClient
import AminAmix.com.socialpersonality.WebService.APIInterface
import AminAmix.com.socialpersonality.model.TestResultResponse
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.airbnb.lottie.LottieAnimationView
import kotlinx.android.synthetic.main.activity_email_confirmation.*
import kotlinx.android.synthetic.main.activity_loading_mbti.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoadingMbtiActivity : AppCompatActivity() {
//    lateinit var i:String
//    lateinit var e:String
//    lateinit var n:String
//    lateinit var t:String
//    lateinit var j:String
//    lateinit var p:String
//    lateinit var f:String
//    lateinit var s:String
    lateinit var data:String
    lateinit var tokenOrg:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading_mbti)
        window.statusBarColor = ContextCompat.getColor(this, R.color.blue)
        val preferences: SharedPreferences =
            applicationContext.getSharedPreferences("SOCIAL_PERSONALITY", Context.MODE_PRIVATE)
        val token = preferences.getString("TOKEN", null).toString() //second parameter default value.
        tokenOrg = "Bearer $token"
        val animationView: LottieAnimationView = findViewById(R.id.img)
        animationView.setAnimation(R.raw.loading_1)
        animationView.loop(true)
        animationView.playAnimation()

        data = intent.getStringExtra("data").toString()

        Log.i("TESTRESRToken" ,tokenOrg)
        Log.i("TESTRESRESData" ,data)





                Handler().postDelayed({



                    getuser(tokenOrg,data)



        }, 5000)
    }
    fun getuser(token: String, data: String) {

        val apilnterface: APIInterface = APIClient().getClient()!!.create(APIInterface::class.java)

        var call: Call<TestResultResponse> = apilnterface.getResult(token,data)
        call.enqueue(object : Callback<TestResultResponse> {
            override fun onResponse(
                call: Call<TestResultResponse>,
                response: Response<TestResultResponse>
            ) {
                if (response.code() == 200) {


                    Log.w("RES", "${response.body().toString()}")
                    val type = response.body()?.type.toString()
                   val e = response.body()?.parameters?.e.toString()
                   val i = response.body()?.parameters?.i.toString()
                   val s = response.body()?.parameters?.s.toString()
                   val n = response.body()?.parameters?.n.toString()
                   val f = response.body()?.parameters?.f.toString()
                   val t = response.body()?.parameters?.t.toString()
                   val p = response.body()?.parameters?.p.toString()
                   val j = response.body()?.parameters?.j.toString()


                    val mIntent = Intent(this@LoadingMbtiActivity, ShowTestResultActivity::class.java)
                    mIntent.putExtra("e",e)
                    mIntent.putExtra("i",i)
                    mIntent.putExtra("s",s)
                    mIntent.putExtra("n",n)
                    mIntent.putExtra("f",f)
                    mIntent.putExtra("t",t)
                    mIntent.putExtra("p",p)
                    mIntent.putExtra("j",j)
                    mIntent.putExtra("j",j)
                    mIntent.putExtra("type",type)
                    startActivity(mIntent)
                    finish()
                    Log.i("startActivity" ,startActivity(mIntent).toString())



//                        val intent = Intent(this@LoadingMbtiActivity, PersonalInformationActivity::class.java)
//                        intent.putExtra("personal_token", tokenOrg)
//                        startActivity(intent)
//                        finish()




                    val dataFromResponse = response.body()



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
            }

            override fun onFailure(call: Call<TestResultResponse>, t: Throwable) {
                Toast.makeText(
                    this@LoadingMbtiActivity,
                    " Register is not ok",
                    Toast.LENGTH_LONG
                )
                    .show()
            }

        })
    }
    }
