package com.zibfit.edittexttest

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var count = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextTextPersonName.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                val inputlength: Int = editTextTextPersonName.getText().toString().length
                if (count <= inputlength && (inputlength == 4 ||
                        inputlength == 9 || inputlength == 14 || inputlength == 19 )){
                    editTextTextPersonName.setText(editTextTextPersonName.getText().toString() + " ");
                    val pos: Int = editTextTextPersonName.getText().length
                    editTextTextPersonName.setSelection(pos)
                }else if (count >= inputlength && (inputlength == 4 ||
                                inputlength == 9 || inputlength == 14 || inputlength == 19)){
                    editTextTextPersonName.setText(editTextTextPersonName.getText().toString()
                            .substring(0, editTextTextPersonName.getText()
                                    .toString().length - 1));
                    val pos: Int = editTextTextPersonName.getText().length
                    editTextTextPersonName.setSelection(pos)
                }
                count = editTextTextPersonName.getText().toString().length


            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

        })

    }

}