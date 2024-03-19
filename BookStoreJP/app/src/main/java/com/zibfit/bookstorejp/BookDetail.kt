package com.zibfit.bookstorejp

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_book_detail.*

class BookDetail : AppCompatActivity() {
    var id = 0
    var sc = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_detail)

        id = intent.getIntExtra("book", 0)
        val viewModel =
            ViewModelProvider(this, BookFactory(application)).get(BookViewModel::class.java)

        viewModel.getByID(id).observe(this, Observer {
            bindUi(it)
            //For Scroll
            sc = it.scroll
        })

        bDBtnContinue.setOnClickListener {

            if (sc > 0) storyScroll.smoothScrollBy(0, sc)
        }
        bDBtnFav.setOnClickListener {
            //Update database for favorit
            BookDatabase.getDatabase(this).bookDao().updateFav(id)
        }

    }

    @SuppressLint("Range")
    private fun bindUi(it: Book) {
        val resID = resources.getIdentifier(it.image, "drawable", packageName)
        bDImage.setImageResource(resID)

        bDTxtTitle.text = it.title
        bDTxtAuthor.text = it.author
        bDTxtYear.text = it.releaseDate.toString()
        bDtxtGenre.text = it.genre
        bDtxtRate.text = it.rating.toString()
        bDTxtStory.text=it.story

        if (it.fav == 1) {
            bDBtnFav.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.activFavBtn));

        //    bDBtnFav.setBackgroundColor(Color.parseColor("#fe3333"));
            bDBtnFav.setTextColor(Color.WHITE)
        } else {
            bDBtnFav.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.deActivBtnFav));

         //   bDBtnFav.setBackgroundColor(ContextCompat.getColor(this, R.color.deActivBtnFav));
            bDBtnFav.setTextColor(Color.BLACK)
//            bDBtnFav.setTextColor(Color.parseColor("#ffffff"))

        }
    }

    override fun onBackPressed() {
        super.onBackPressed()

        // this is for save Scroll position when we click on backPress
        if (sc != storyScroll.scrollY){
            BookDatabase.getDatabase(this).bookDao().updateScroll(id,storyScroll.scrollY)
        }
    }

}
