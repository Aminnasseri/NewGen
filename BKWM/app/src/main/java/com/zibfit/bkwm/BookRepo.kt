package ir.hetbo.roomkotlin

import androidx.lifecycle.LiveData

class BookRepo(private val bookDao:BookDao) {

    /*
*
* 1. get all
* 2. get popular
* 3. get favorite
* 4. get by genre
* 5. get by id
*
* */


    val allBooks: LiveData<List<Book>> = bookDao.getAll()
    val favoriteBooks: LiveData<List<Book>> = bookDao.getFavorite()
    val popularBooks: LiveData<List<Book>> = bookDao.getPopular()

    fun getByGenre(genre: String) : LiveData<List<Book>> = bookDao.getByGenre(genre)
    fun getById(id: Int) : LiveData<Book> = bookDao.getById(id)

}