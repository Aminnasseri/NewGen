package ir.hetbo.roomkotlin

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class BookViewModel(app: Application): AndroidViewModel(app) {

    /*
*
* 1. get all
* 2. get popular
* 3. get favorite
*
* 4. get by genre
* 5. get bu id
*
* */

    private val bookRepo: BookRepo

    val allBooks: LiveData<List<Book>>
    val popularBooks: LiveData<List<Book>>
    val favoriteBooks: LiveData<List<Book>>

    init {
        val bookDao = BookDatabase.getDatabase(app).bookDao()
        bookRepo = BookRepo(bookDao)

        allBooks = bookRepo.allBooks
        popularBooks = bookRepo.popularBooks
        favoriteBooks = bookRepo.favoriteBooks

    }

    fun getByGenre(genre: String) : LiveData<List<Book>> = bookRepo.getByGenre(genre)
    fun getById(id: Int) : LiveData<Book> = bookRepo.getById(id)

}
