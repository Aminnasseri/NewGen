package AminAmix.com

import AminAmix.com.socialpersonality.LoadingMbtiActivity
import AminAmix.com.socialpersonality.model.QuestionList
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.question_row.view.*
import org.json.JSONObject



private var lastCheckedRB: RadioButton? = null

class MbtiQuestionAdapter(val questionList: QuestionList,val onRadioButtonClick : (Int) -> Unit) :
    RecyclerView.Adapter<QuestionViewHolder>() {
    var maps = arrayListOf<HashMap<String, String>>()
    var i = 0
    var e = 0
    var n = 0
    var t = 0
    var j = 0
    var p = 0
    var f = 0
    var s = 0
    var typeQuestion = ""
    var testnumbers = 0



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(
            AminAmix.com.socialpersonality.R.layout.question_row,
            parent,
            false
        )
        return QuestionViewHolder(cellForRow)
    }


    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {

        //val question = questions.get(position)
        if (questionList.questions[position].tip != "") {
            holder.view.txtTitletipsQuestionRow.visibility = View.VISIBLE
            //      holder.view.imgtips.visibility = View.VISIBLE
            holder.view.txtTipsQuestion.visibility = View.VISIBLE
            holder.view.txtTipsQuestion.text = questionList.questions[position].tip

        } else {
            holder.view.imageView15.visibility = View.INVISIBLE
            holder.view.shapeBlue.visibility = View.VISIBLE
            holder.view.shapeGreen.visibility = View.VISIBLE
            holder.view.shapeCircle.visibility = View.VISIBLE


        }

        holder.view.txtAmountQuestion.text = questionList.questions.size.toString()
        holder.view.txtCurrentQuestion.text = (position + 1).toString()
        val question = questionList.questions.get(position).title
        Log.i("Position", position.toString())
        val answers = questionList.questions.get(position).answers

        var valueOfRadio = ""

        if (questionList.questions.get(position).type == "range") {
            holder.view.cardAB.visibility = View.INVISIBLE
            for (answer in answers) {
                val value = answer.value
                typeQuestion = answer.title

                Log.i("Position2", value.toString())
                if (answer.title == "Agree") {
                    holder.view.txtQuestionDisAgree.text = answer.title

                } else {
                    holder.view.txtQuestionAgree.text = answer.title

                }


            }
            holder.view.radioGroup.setOnCheckedChangeListener { group, checkedId ->

                if (checkedId == AminAmix.com.socialpersonality.R.id.radioButton_middle) {
                    Log.i("Radio", "0")
                    valueOfRadio = "0"



                }
                if (checkedId == AminAmix.com.socialpersonality.R.id.radioButton_agree1) {
                    Log.i("Radio", "1")
                    valueOfRadio = "1"

                }
                if (checkedId == AminAmix.com.socialpersonality.R.id.radioButton_agree2) {
                    Log.i("Radio", "2")
                    valueOfRadio = "2"

                }
                if (checkedId == AminAmix.com.socialpersonality.R.id.radioButton_disagree1) {
                    Log.i("Radio", "-1")
                    valueOfRadio = "-1"

                }
                if (checkedId == AminAmix.com.socialpersonality.R.id.radioButton_disagree_2) {
                    Log.i("Radio", "-2")
                    valueOfRadio = "-2"

                }

                val checked = group.findViewById(checkedId) as RadioButton
                val list: ArrayList<String> = ArrayList()
                list.add(checked.getText().toString())

                Log.i("Chekkk", list.toString())
                Log.i(
                    "checkedId", position.toString()
                )

                var idIf = ""
                var typeIf = ""
                var testValue = 0


                var map = HashMap<String, String>();


                var radioInt = valueOfRadio.toInt()
                var selectedAnswers = questionList.questions[position].answers


                if (radioInt > 0) {
                    for (answer in selectedAnswers) {
                        if (answer.title == "Agree") {
                            typeIf = answer.type
                            testValue = answer.value
                            holder.view.txtQuestionDisAgree.setTextColor(Color.parseColor("#5E5E5E"));
                            holder.view.txtQuestionAgree.setTextColor(Color.parseColor("#B2B2B2"));
                            holder.view.txtCurrentQuestion.setTextColor(Color.parseColor("#0B6095"));
                            holder.view.txtAmountQuestion.setTextColor(Color.parseColor("#0B6095"));
                            holder.view.txtCurrentQuestion2.setTextColor(Color.parseColor("#0B6095"));
                            holder.view.txtTitleQuestions.setTextColor(Color.parseColor("#0B6095"));
                            holder.view.questionsTik.visibility = View.VISIBLE


                        }
                    }

                } else if (radioInt < 0) {
                    for (answer in selectedAnswers) {
                        if (answer.title == "Disagree") {
                            typeIf = answer.type
                            testValue = answer.value
                            holder.view.txtQuestionAgree.setTextColor(Color.parseColor("#5E5E5E"));
                            holder.view.txtQuestionDisAgree.setTextColor(Color.parseColor("#B2B2B2"));
                            holder.view.txtCurrentQuestion.setTextColor(Color.parseColor("#0B6095"));
                            holder.view.txtAmountQuestion.setTextColor(Color.parseColor("#0B6095"));
                            holder.view.txtCurrentQuestion2.setTextColor(Color.parseColor("#0B6095"));
                            holder.view.txtTitleQuestions.setTextColor(Color.parseColor("#0B6095"));
                            holder.view.questionsTik.visibility = View.VISIBLE


                        }
                    }

                } else {
                    holder.view.txtQuestionAgree.setTextColor(Color.parseColor("#B2B2B2"));
                    holder.view.txtQuestionDisAgree.setTextColor(Color.parseColor("#B2B2B2"));
                    holder.view.txtCurrentQuestion.setTextColor(Color.parseColor("#0B6095"));
                    holder.view.txtAmountQuestion.setTextColor(Color.parseColor("#0B6095"));
                    holder.view.txtCurrentQuestion2.setTextColor(Color.parseColor("#0B6095"));
                    holder.view.txtTitleQuestions.setTextColor(Color.parseColor("#0B6095"));
                    holder.view.questionsTik.visibility = View.VISIBLE


                }

                var absolutResultRadio = Math.abs(valueOfRadio.toInt())


                //  map["Q" + "_" + idIf.toString() + "_" + typeIf.toString()] = valueOfRadio
                map["Q" + (position + 1).toString()] =
                    typeIf.toString() + "_" + absolutResultRadio * testValue

                var con = maps.any { it.containsKey("Q" + (position + 1).toString()) }
                if (maps.any { con == true }) {// filter
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        maps.removeIf { it.containsKey("Q" + (position + 1)) }
                        testnumbers = testnumbers - 1
                        onRadioButtonClick(testnumbers)


                    }
                    testnumbers = testnumbers + 1
                    onRadioButtonClick(testnumbers)


                    maps.add(map)
                    Log.i("maps", "in dare ejra mishe")


                } else {
                    maps.add(map)
                    testnumbers = testnumbers + 1
                    onRadioButtonClick(testnumbers)


                }

            }
            Log.i ( "testnumber2" , testnumbers.toString())


        } else {
            holder.view.cardRange.visibility = View.INVISIBLE
            holder.view.radioButtonFirstAAndB.text = answers[0].title
            holder.view.radioButtonSecondAAndB.text = answers[1].title

            holder.view.radioGroup2.setOnCheckedChangeListener { group, checkedId ->


                if (checkedId == AminAmix.com.socialpersonality.R.id.radioButtonFirstAAndB) {
                    Log.i("Radio", "1")
                    valueOfRadio = "1"

                }

                if (checkedId == AminAmix.com.socialpersonality.R.id.radioButtonSecondAAndB) {
                    Log.i("Radio", "-1")
                    valueOfRadio = "-1"

                }


                val checked = group.findViewById(checkedId) as RadioButton
                val list: ArrayList<String> = ArrayList()
                list.add(checked.getText().toString())

                Log.i("Chekkk", list.toString())
                Log.i(
                    "checkedId", position.toString()
                )

                var idIf = ""
                var typeIf = ""
                var testValue = 0


                var map = HashMap<String, String>();


                var radioInt = valueOfRadio.toInt()
                var selectedAnswers = questionList.questions[position].answers


                if (radioInt > 0) {


                    typeIf = selectedAnswers[0].type
                    testValue = selectedAnswers[0].value

                    holder.view.txtCurrentQuestion.setTextColor(Color.parseColor("#0B6095"));
                    holder.view.txtAmountQuestion.setTextColor(Color.parseColor("#0B6095"));
                    holder.view.txtCurrentQuestion2.setTextColor(Color.parseColor("#0B6095"));
                    holder.view.txtTitleQuestions.setTextColor(Color.parseColor("#0B6095"));
                    holder.view.questionsTik.visibility = View.VISIBLE


                } else if (radioInt < 0) {

                    typeIf = selectedAnswers[1].type
                    testValue = selectedAnswers[1].value
                    holder.view.txtCurrentQuestion.setTextColor(Color.parseColor("#0B6095"));
                    holder.view.txtAmountQuestion.setTextColor(Color.parseColor("#0B6095"));
                    holder.view.txtCurrentQuestion2.setTextColor(Color.parseColor("#0B6095"));
                    holder.view.txtTitleQuestions.setTextColor(Color.parseColor("#0B6095"));
                    holder.view.questionsTik.visibility = View.VISIBLE


                }

                var absolutResultRadio = Math.abs(valueOfRadio.toInt())


                //  map["Q" + "_" + idIf.toString() + "_" + typeIf.toString()] = valueOfRadio
                map["Q" + (position + 1).toString()] =
                    typeIf.toString() + "_" + absolutResultRadio * testValue

                var con = maps.any { it.containsKey("Q" + (position + 1).toString()) }
                if (maps.any { con == true }) {// filter
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        maps.removeIf { it.containsKey("Q" + (position + 1)) }
                        testnumbers = testnumbers - 1
                        onRadioButtonClick(testnumbers)


                    }
                    testnumbers = testnumbers + 1
                    onRadioButtonClick(testnumbers)

                    maps.add(map)
                    Log.i("maps", "in dare ejra mishe")


                } else {
                    testnumbers = testnumbers + 1
                    onRadioButtonClick(testnumbers)
                    maps.add(map)
                }

            }


        }




        Log.i("mapSize", maps.toString())
        Log.i("mapSize", maps.size.toString())
        if (maps.size.toInt() == questionList.questions.size
        ) {

            Log.i("maps", "Halaaaaaaaaaaa in dare ejra mishe")
            Log.i("Mapss", "SomeText: " + Gson().toJson(maps))
            val dataOfTestToJson = Gson().toJson(maps).toString()
            val eRegex = Regex("E_(\\d+)")
            val iRegex = Regex("I_(\\d+)")
            val sRegex = Regex("S_(\\d+)")
            val nRegex = Regex("N_(\\d+)")
            val fRegex = Regex("F_(\\d+)")
            val tRegex = Regex("T_(\\d+)")
            val pRegex = Regex("P_(\\d+)")
            val jRegex = Regex("J_(\\d+)")
            e = eRegex.findAll(dataOfTestToJson).map { it.groupValues[1].toInt() }.sum()
            i = iRegex.findAll(dataOfTestToJson).map { it.groupValues[1].toInt() }.sum()
            s = sRegex.findAll(dataOfTestToJson).map { it.groupValues[1].toInt() }.sum()
            n = nRegex.findAll(dataOfTestToJson).map { it.groupValues[1].toInt() }.sum()
            f = fRegex.findAll(dataOfTestToJson).map { it.groupValues[1].toInt() }.sum()
            t = tRegex.findAll(dataOfTestToJson).map { it.groupValues[1].toInt() }.sum()
            p = pRegex.findAll(dataOfTestToJson).map { it.groupValues[1].toInt() }.sum()
            j = jRegex.findAll(dataOfTestToJson).map { it.groupValues[1].toInt() }.sum()

            val data = JSONObject()
            data.put("E", e)
            data.put("I", i)
            data.put("S", s)
            data.put("N", n)
            data.put("T", t)
            data.put("F", f)
            data.put("J", j)
            data.put("P", p)
            Log.i(
                "dataSexy",
                data.toString()
            )


            Log.i(
                "mapsMinoofam",
                "I = $i" + " " + "E = $e" + " " + "S= $s" + " " + "N = $n" + " " + "F = $f" + " " + "T = $t" + " " + "P = $p" + " " + "J = $j"
            )

            val context = holder.view.context
            val intent = Intent(context, LoadingMbtiActivity::class.java)
            intent.putExtra("e", e.toString())
            intent.putExtra("i", i.toString())
            intent.putExtra("s", s.toString())
            intent.putExtra("n", n.toString())
            intent.putExtra("f", f.toString())
            intent.putExtra("t", t.toString())
            intent.putExtra("p", p.toString())
            intent.putExtra("j", j.toString())
            intent.putExtra("data", data.toString())
            context.startActivity(intent)
            (context as Activity).finish()
        }

        holder.view.txtQuestionInRow.text = question

    }


    override fun getItemCount(): Int {
        return questionList.questions.size

    }

    override fun getItemViewType(position: Int): Int {
        return position
    }


}


class QuestionViewHolder(val view: View) : RecyclerView.ViewHolder(view) {


}