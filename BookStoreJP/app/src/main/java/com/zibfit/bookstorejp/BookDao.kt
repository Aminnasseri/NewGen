package com.zibfit.bookstorejp

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BookDao {

    /*

1 get all : live data list
2 get popular : live data list
3 get by genre : live data list
4 search : list
5 get by id : live data
6 get favorite : live data list
7 update scroll
8 update favorite

9 insert
10 delete

 */

    @Query("SELECT * FROM story_tbl  ")
    fun getAll(): LiveData<List<Book>>

    @Query("SELECT * FROM story_tbl ORDER BY rating")
    fun getPopular(): LiveData<List<Book>>

    @Query("SELECT * FROM story_tbl WHERE genre = :genre")
    fun getByGenre(genre: String): LiveData<List<Book>>

    @Query("SELECT * FROM story_tbl WHERE story LIKE '%' || :query || '%' OR title LIKE '%' || :query || '%' ")
    fun search(query: String): List<Book>

    @Query("SELECT * FROM story_tbl WHERE id = :id ")
    fun getById(id: Int): LiveData<Book>

    @Query("SELECT * FROM story_tbl WHERE fav = 1 ")
    fun getFavorite(): LiveData<List<Book>>

    @Query("UPDATE story_tbl SET scroll = :value WHERE id = :id ")
    fun updateScroll(id: Int, value: Int)

    @Query("UPDATE story_tbl SET fav = 1-fav WHERE id = :id ")
    fun updateFav(id: Int)

    @Insert
    fun insert(book: Book)

    @Delete
    fun delete(book: Book)

    @Query("UPDATE story_tbl SET title = :title , genre = :genre WHERE id = :id ")
    fun update(id: Int, title: String, genre: String)

    @Query("SELECT id FROM story_tbl ORDER BY id DESC LIMIT 1")
    fun getLastId(): Int

}