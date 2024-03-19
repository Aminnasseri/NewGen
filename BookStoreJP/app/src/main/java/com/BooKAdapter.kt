package com

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zibfit.bookstorejp.Book
import com.zibfit.bookstorejp.BookDetail
import com.zibfit.bookstorejp.R
import kotlinx.android.synthetic.main.rv_items.view.*


class BookAdapter internal constructor(val context: Context):RecyclerView.Adapter<BookAdapter.BookViewHolder>(){

    private val inflater= LayoutInflater.from(context)

    private var bookList = emptyList<Book>()

    inner class BookViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = inflater.inflate(R.layout.rv_items,parent,false)
        return BookViewHolder(view)
    }

    override fun getItemCount(): Int = bookList.size

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {

        val current = bookList[position]

        val resId = context.resources.getIdentifier(current.image,
            "drawable",
            context.packageName)

        holder.itemView.itemImage.setImageResource(resId)

        holder.itemView.itemTitle.text = current.title
        holder.itemView.itemGenre.text = current.genre

        holder.itemView.setOnClickListener {
            val intent = Intent(context, BookDetail::class.java)
            intent.putExtra("book",current.id)
            context.startActivity(intent)
        }

//        holder.itemView.setOnLongClickListener {
//
//            val mDialog = BottomSheetDialog(context)
//            mDialog.setContentView(R.layout.my_dialog)
//
//            mDialog.my_update.setOnClickListener {
//                mDialog.setContentView(R.layout.dialog_update)
//                mDialog.update_title.setText(current.title)
//                mDialog.update_genre.setText(current.genre)
//                mDialog.update_submit.setOnClickListener {
//                    BookDatabase.getDatabase(context).bookDao().update(
//                        current.id,
//                        mDialog.update_title.text.toString(),
//                        mDialog.update_genre.text.toString()
//                    )
//                    mDialog.dismiss()
//                }
//            }
//
//            mDialog.my_delete.setOnClickListener {
//                BookDatabase.getDatabase(context).bookDao().delete(current)
//                mDialog.dismiss()
//            }
//
//            mDialog.show()
//
//
//
//            return@setOnLongClickListener false
//        }

    }

    internal fun setBooks(books: List<Book>){
        this.bookList = books
        notifyDataSetChanged()
    }

}
