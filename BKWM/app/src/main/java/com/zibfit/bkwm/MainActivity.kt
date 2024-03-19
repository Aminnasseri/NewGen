 package com.zibfit.bkwm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.hetbo.roomkotlin.BookAdapter
import ir.hetbo.roomkotlin.BookFactory
import ir.hetbo.roomkotlin.BookViewModel
import kotlinx.android.synthetic.main.activity_main.*

 class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bookViewModel = ViewModelProvider(this, BookFactory(application)).get(BookViewModel::class.java)

        val popularAdapter = BookAdapter(this)
        rv_popular.adapter = popularAdapter
        val popularManager = LinearLayoutManager(this)
        popularManager.orientation = RecyclerView.HORIZONTAL
        rv_popular.layoutManager = popularManager

        bookViewModel.popularBooks.observe(this, Observer {
            popularAdapter.setBooks(it)
        })

        val genreAdapter = BookAdapter(this)
        rvFiction.adapter = genreAdapter
        val genreManager = LinearLayoutManager(this)
        genreManager.orientation = RecyclerView.HORIZONTAL
        rvFiction.layoutManager = genreManager
        bookViewModel.getByGenre("Fiction").observe(this,
            Observer { genreAdapter.setBooks(it) })


        val allAdapter = BookAdapter(this)
        rvAll.adapter = allAdapter
        val allManager = LinearLayoutManager(this)
        allManager.orientation = RecyclerView.HORIZONTAL
        rvAll.layoutManager = allManager
        bookViewModel.allBooks.observe(this,
            Observer { allAdapter.setBooks(it) })



    }
}