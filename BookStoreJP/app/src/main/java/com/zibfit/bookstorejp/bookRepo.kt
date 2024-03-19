package com.zibfit.bookstorejp

import androidx.lifecycle.LiveData

class bookRepo(val bookDao: BookDao) {
    //getAll

    val allBooks:LiveData<List<Book>> = bookDao.getAll()

    //getPop
    val popBook:LiveData<List<Book>> = bookDao.getPopular()

    //getFav
    val FavBook:LiveData<List<Book>> = bookDao.getFavorite()
    //get by genre

    fun getByGenre (genre:String) :LiveData<List<Book>> = bookDao.getByGenre(genre)

    //get by id
    fun getById (id:Int) :LiveData<Book> = bookDao.getById(id)
}