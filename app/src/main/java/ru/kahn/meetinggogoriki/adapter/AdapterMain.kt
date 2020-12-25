package ru.kahn.meetinggogoriki.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_card_party.view.*
import ru.kahn.meetinggogoriki.R
import ru.kahn.meetinggogoriki.model.ModelListMembers
import ru.kahn.meetinggogoriki.other.CircularTransform

class AdapterMain(var listMembers: MutableList<ModelListMembers>) : RecyclerView.Adapter<AdapterMain.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.item_card_party, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return listMembers.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: ModelListMembers = listMembers[position]
        val imagePath = model.image_member
        Picasso.with(holder.itemView.context)
            .load(imagePath)
            .fit()
            .transform(CircularTransform(50))
            .into(holder.itemView.iv_member)
        holder.itemView.tv_name_member.text = model.name_member
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}