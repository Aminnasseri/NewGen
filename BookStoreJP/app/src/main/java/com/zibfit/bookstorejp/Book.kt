package com.zibfit.bookstorejp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "story_tbl")
data class Book(
    @PrimaryKey val id: Int,
    val title: String,
    val story:String,
    val author:String,
    @ColumnInfo(name = "release_date") val releaseDate:Int ,
    val genre:String,
    val rating:Double,
    val image:String,
    @ColumnInfo(defaultValue = "0") val scroll:Int ,
    @ColumnInfo(defaultValue = "0") var fav:Int
) {
}
