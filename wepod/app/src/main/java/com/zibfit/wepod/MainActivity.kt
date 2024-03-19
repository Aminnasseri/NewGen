package com.zibfit.wepod

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        click.setOnClickListener {
            Toast.makeText(this,"احسان دوست دارم",Toast.LENGTH_LONG).show()
           // startActivity(Intent(this,CardBuilder::class.java))
        }
    }
}