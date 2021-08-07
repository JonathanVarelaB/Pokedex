package com.jvarela.pokdex.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jvarela.pokdex.R
import com.jvarela.pokdex.model.entity.FinalStat

class StatListAdapter(): RecyclerView.Adapter<StatListAdapter.StatViewHolder>() {

    var statList: List<FinalStat> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class StatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var statName: TextView = itemView.findViewById(R.id.statName)
        private var statProgress: ProgressBar = itemView.findViewById(R.id.statProgress)
        private var statProgressNumber: TextView = itemView.findViewById(R.id.statProgressNumber)

        fun bind(model: FinalStat) {
            statName.text = model.name[0].uppercase()+model.name.substring(1)
            statProgress.progress = model.value.toInt()
            statProgressNumber.text = model.value
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StatListAdapter.StatViewHolder {
        val holderView = LayoutInflater.from(parent.context).inflate(R.layout.item_stat_cell, parent, false)
        return StatViewHolder(holderView)
    }

    override fun onBindViewHolder(holder: StatListAdapter.StatViewHolder, position: Int) {
        holder.bind(statList[position])
    }

    override fun getItemCount(): Int = statList.size
}