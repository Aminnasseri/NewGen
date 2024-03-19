package com.zibfit.bookstorejp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.BookAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bookViewModel =
            ViewModelProvider(this, BookFactory(application)).get(BookViewModel::class.java)

        //POP BOOKS
        val popularAdapter = BookAdapter(this)
        rv_popular.adapter = popularAdapter
        val popularManager = LinearLayoutManager(this)
        popularManager.orientation = RecyclerView.HORIZONTAL
        rv_popular.layoutManager = popularManager

        bookViewModel.popBooks.observe(this, Observer {
            popularAdapter.setBooks(it)
        })


        //FictionBOOKs

        val genreAdapter = BookAdapter(this)
        rvFiction.adapter = genreAdapter
        val genreManager = LinearLayoutManager(this)
        genreManager.orientation = RecyclerView.HORIZONTAL
        rvFiction.layoutManager = genreManager
        bookViewModel.getByGenre("Fiction").observe(this, Observer {
            genreAdapter.setBooks(it)
        })


        val allAdapter = BookAdapter(this)
        rvAll.adapter = allAdapter
        val allManager = LinearLayoutManager(this)
        allManager.orientation = RecyclerView.HORIZONTAL
        rvAll.layoutManager = allManager
        bookViewModel.allBooks.observe(this, Observer {
            allAdapter.setBooks(it)
        })


        val favAdapter = BookAdapter(this)
        rvFav.adapter = favAdapter
        val favManager = LinearLayoutManager(this)
        favManager.orientation = RecyclerView.HORIZONTAL
        rvFav.layoutManager = favManager
        bookViewModel.favBooks.observe(this, Observer {
            if (it.isNotEmpty()) {
                txtFavorits.visibility = View.VISIBLE
                cardFav.visibility = View.VISIBLE

            } else {
                txtFavorits.visibility = View.GONE
                cardFav.visibility = View.GONE
            }

            favAdapter.setBooks(it)
        })


        val searchAdapter = BookAdapter(this)
        rvSearch.adapter = searchAdapter
        val searchManager = StaggeredGridLayoutManager(3, RecyclerView.VERTICAL)
        rvSearch.layoutManager = searchManager


        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchAdapter.setBooks(emptyList())

                val books = BookDatabase.getDatabase(this@MainActivity).bookDao().search(query!!)
                search_res.visibility = View.VISIBLE
                if (books.isEmpty()) {
                    txtNotFoung.visibility = View.VISIBLE
                    txtNotFoung.text = "No Result for this Book"
                } else
                    txtNotFoung.visibility = View.GONE
                searchAdapter.setBooks(books)

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }


        })

        //When search is Close run this to hide every things that relevant to Search

        search_view.setOnCloseListener {
            search_view.clearFocus()
            search_res.visibility = View.GONE
            txtNotFoung.visibility = View.GONE
            search_res.visibility = View.GONE
            searchAdapter.setBooks(emptyList())
            return@setOnCloseListener false

        }


    }
}