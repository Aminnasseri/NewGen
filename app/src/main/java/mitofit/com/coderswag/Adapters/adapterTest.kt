package mitofit.com.coderswag.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import mitofit.com.coderswag.Model.Category
import mitofit.com.coderswag.R

class adapterTest(val context: Context, val categories: List<Category>) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val categoriesView: View

        categoriesView = LayoutInflater.from(context).inflate(R.layout.category_list_item,null)
        val categoryImage: ImageView = categoriesView.findViewById(R.id.categoryImage)
        val categorynName: TextView = categoriesView.findViewById(R.id.categoryName)

       val category = categories[position]

        categorynName.text = category.title

        val resurceId = context.resources.getIdentifier(category.image,"drawable",context.packageName)
        categoryImage.setImageResource(resurceId)








        return categoriesView
    }

    override fun getItem(position: Int): Any {
        return  categories[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return categories.count()
    }

}