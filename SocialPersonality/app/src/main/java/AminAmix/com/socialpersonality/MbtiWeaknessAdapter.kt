package AminAmix.com.socialpersonality

import AminAmix.com.socialpersonality.model.MbtiResult
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.advantages_row.view.*

class MbtiWeaknessAdapter(val advanatagesClass: MbtiResult) :
    RecyclerView.Adapter<WeaknessesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeaknessesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(
            AminAmix.com.socialpersonality.R.layout.advantages_row,
            parent,
            false
        )
        return WeaknessesViewHolder(cellForRow)    }

    override fun onBindViewHolder(holder: WeaknessesViewHolder, position: Int) {
//      holder.view.cardAdvantages.setCardBackgroundColor(Color.BLACK);
        holder.view.txtAdvantageUp.text = advanatagesClass.weaknesses[position].title.toString()
        holder.view.txtAdvantageDown.text = advanatagesClass.weaknesses[position].tips.toString()


    }

    override fun getItemCount(): Int {
        return advanatagesClass.weaknesses.size
    }
}

class WeaknessesViewHolder(val view: View) : RecyclerView.ViewHolder(view) {


}