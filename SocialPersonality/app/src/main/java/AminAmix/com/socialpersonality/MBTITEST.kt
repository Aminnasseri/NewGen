package AminAmix.com.socialpersonality

import AminAmix.com.MbtiQuestionAdapter
import AminAmix.com.socialpersonality.WebService.APIClient
import AminAmix.com.socialpersonality.WebService.APIInterface
import AminAmix.com.socialpersonality.model.QuestionList
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_mbtitest.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MBTITEST : AppCompatActivity() {
    lateinit var token: String
    var size = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mbtitest)
        window.statusBarColor = ContextCompat.getColor(this, R.color.black)


        val preferences: SharedPreferences =
            applicationContext.getSharedPreferences("SOCIAL_PERSONALITY", Context.MODE_PRIVATE)
        token = preferences.getString("TOKEN", null).toString() //second parameter default value.
        var tokenBAr = "Bearer $token"
        getQuestions(tokenBAr)


    }

    fun getQuestions(token: String) {
        val apilnterface: APIInterface = APIClient().getClient()!!.create(APIInterface::class.java)

        var call: Call<QuestionList> = apilnterface.getQuestions(token)
        call.enqueue(object : Callback<QuestionList> {
            override fun onResponse(
                call: Call<QuestionList>,
                response: Response<QuestionList>
            ) {
                if (response.code() == 200) {
//                    Toast.makeText(
//                        this@MBTITEST,
//                        "Testatol GolMohammadi",
//                        Toast.LENGTH_LONG
//                    )
//                        .show()
//                    Log.w("RESGetID", "${response.body().toString()}")
                    val dataFromResponse = response.body()
                    val questionList = dataFromResponse?.questions
                     size = questionList?.size!!
                    val title = questionList?.get(0)?.title

                    print(questionList)
                    Log.w("QuestionListDaDa", questionList.toString())
                    Log.w("QuestionListDaDa", size.toString())
                    Log.w("QuestionListDaDa", title.toString())

                    runOnUiThread {
                        val layoutManager = LinearLayoutManager(
                            this@MBTITEST,
                            LinearLayoutManager.HORIZONTAL,
                            false
                        )
                        recycler_questions.layoutManager = layoutManager
                        recycler_questions.setHasFixedSize(true)
                        recycler_questions.adapter?.setHasStableIds(true)
                        Collections.shuffle(dataFromResponse?.questions)
                        recycler_questions.adapter =
                            dataFromResponse?.let {
                                MbtiQuestionAdapter(it) { number ->
                                    updateProgressBar(
                                        number
                                    )
                                }
                            }
//                        txtNumbetMax.text = questionList?.size.toString()
                        var mbtiAdapter = dataFromResponse?.let {
                            MbtiQuestionAdapter(it) { number ->
                                updateProgressBar(number)
                            }
                        }


//                        btnShowRes.setOnClickListener {
//                         //   dataFromResponse?.let { MbtiQuestionAdapter(it) }?.activateButtons(true)
//                            val intent = Intent(this@MBTITEST, ShowTestResultActivity::class.java)
//                            startActivity(intent)
//                            finish()
//                        }


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
                        val intent = Intent(this@MBTITEST, FirstPageActivity::class.java)
                        startActivity(intent)
                        finish()

                    }

                }

            }

            override fun onFailure(call: Call<QuestionList>, t: Throwable) {

                Toast.makeText(
                    this@MBTITEST,
                    " Register is not ok",
                    Toast.LENGTH_LONG
                )
                    .show()
            }

        })
    }

    private fun updateProgressBar(testNumber: Int) {
        var percentage = (testNumber.toDouble() / size) * 100
        progressBarMbti.max = size
        progressBarMbti.setProgress(testNumber)
        Log.i("testnumber", testNumber.toString())
        txtTestNumber.setText(percentage.toInt().toString() + 0x0025.toChar())

    }


}

