package com.cas.casdashboard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cas.casdashboard.databinding.SelectZoneItemBinding
import com.cas.casdashboard.https.response.decode.Zone

/**
 * @author Benjamin
 * @description:
 * @date :2021.11.30 11:31
 */
class SelectZoneAdapter(private val itemClick:(Zone,Int) -> Unit):ListAdapter<Zone,SelectZoneAdapter.ViewHolder>(
    object : DiffUtil.ItemCallback<Zone>(){
        override fun areItemsTheSame(oldItem: Zone, newItem: Zone) = oldItem == newItem

        override fun areContentsTheSame(oldItem: Zone, newItem: Zone) = oldItem == newItem

    }
) {
    private var currentPos = -1
    fun setPosition(){
        currentPos = 0
        itemClick(getItem(0),0)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        SelectZoneItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
    ).apply {
        root.setOnClickListener {
            itemClick(getItem(absoluteAdapterPosition),absoluteAdapterPosition)
            val temp = currentPos
            val currentlyPosition = it.tag as Int
            if (temp != currentlyPosition){
                currentPos = currentlyPosition
                notifyItemChanged(temp)
                it.isSelected = true
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.background.apply {
            isSelected = currentPos == position
            tag = position
        }
    }
    inner class ViewHolder(binding:SelectZoneItemBinding):RecyclerView.ViewHolder(binding.root){
        private val text = binding.zoneItemText
        val root = binding.root
        val background = binding.rootBgc
        fun bind(zone:Zone){
            text.text = zone.nameEn
        }
    }
}