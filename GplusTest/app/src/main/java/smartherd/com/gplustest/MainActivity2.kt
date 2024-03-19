package smartherd.com.gplustest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main2.*

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        btnNext1.isEnabled = false
        btnNext1.isClickable = false
        seekBar2.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                Toast.makeText(this@MainActivity2,"Progress: $progress",Toast.LENGTH_SHORT).show()
                btnNext1.isEnabled = true
                btnNext1.isClickable = false
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                Toast.makeText(this@MainActivity2,"Please Choose Your Number",Toast.LENGTH_SHORT).show()

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                Toast.makeText(this@MainActivity2,"Selected: ${seekBar2.progress}",Toast.LENGTH_SHORT).show()
            }

        })


    }
}