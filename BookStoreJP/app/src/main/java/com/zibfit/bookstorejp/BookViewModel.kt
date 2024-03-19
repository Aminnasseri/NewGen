package com.zibfit.bookstorejp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class BookViewModel (app:Application) : AndroidViewModel(app) {

    private val bookRepo:bookRepo

    val allBooks:LiveData<List<Book>>
    val favBooks:LiveData<List<Book>>
    val popBooks:LiveData<List<Book>>

    init {
        val bookDao = BookDatabase.getDatabase(app.applicationContext).bookDao()
        bookRepo=bookRepo(bookDao)

        allBooks = bookRepo.allBooks
        favBooks = bookRepo.FavBook
        popBooks = bookRepo.popBook
    }

    fun getByGenre(genre:String) = bookRepo.getByGenre(genre)
    fun getByID(id:Int) = bookRepo.getById(id)


}