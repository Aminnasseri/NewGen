package com.zibfit

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_finish_game.*


class FinishGameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish_game)
        animation_view.setAnimation(R.raw.finish)

        btn_star.setOnClickListener{
            if(isPackageInstalled(this,"com.farsitel.bazaar")) {

                val intent = Intent(Intent.ACTION_EDIT)
                intent.data = Uri.parse("bazaar://details?id=" + "com.zibfit.chalab")
                intent.setPackage("com.farsitel.bazaar")
                startActivity(intent)
            }
            else{
                Toast.makeText(this,"لطفا کافه بازار را نصب کنید.",Toast.LENGTH_SHORT).show()
            }
        }

        btn_again.setOnClickListener {
            finishAffinity();
            val intent = Intent(applicationContext, SelectPlayerActivity::class.java)
            startActivity(intent)
        }
        btn_insta.setOnClickListener {
            val uri: Uri = Uri.parse("https://www.instagram.com/chalab.app")
            val likeIng = Intent(Intent.ACTION_VIEW, uri)

            likeIng.setPackage("com.instagram.android")

            try {
                startActivity(likeIng)
            } catch (e: ActivityNotFoundException) {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://www.instagram.com/chalab.app")
                    )
                )
            }
        }

    }
    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "یه بار دیگه دکمه برگشت رو بزن تا از برنامه خارج شی", Toast.LENGTH_SHORT).show()

        Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
    }
    fun isPackageInstalled(context: Context, packageName: String?): Boolean {
        return try {

            context.getPackageManager().getPackageInfo(packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }
}
