package AminAmix.com.socialpersonality

import AminAmix.com.socialpersonality.model.MbtiResult
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.advantages_row_org.view.*

class MbtiAdvantagesAdapter(val advanatagesClass: MbtiResult) :
    RecyclerView.Adapter<AdvantagesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdvantagesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(
            AminAmix.com.socialpersonality.R.layout.advantages_row_org,
            parent,
            false
        )
        return AdvantagesViewHolder(cellForRow)    }

    override fun onBindViewHolder(holder: AdvantagesViewHolder, position: Int) {
        holder.view.txtAdvantageUpOrg.text = advanatagesClass.strengths[position].title.toString()

    }

    override fun getItemCount(): Int {
        return advanatagesClass.strengths.size
    }
}

class AdvantagesViewHolder(val view: View) : RecyclerView.ViewHolder(view) {


}