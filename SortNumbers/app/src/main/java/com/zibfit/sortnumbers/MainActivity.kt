package com.zibfit.sortnumbers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        txtRes.text=""
        val list = arrayListOf<Int>(41, 3, 19,1, 12,4)
            txtfirst.text= list.toString()

        val numbers = listOf("one", "two", "three", "four")
        //txtfirst.text= numbers.toString()
        btnSort.setOnClickListener {
            val sort =  list.sortedWith(compareBy { it.dec() })
            txtRes.text= sort.toString()
        }




     }

}