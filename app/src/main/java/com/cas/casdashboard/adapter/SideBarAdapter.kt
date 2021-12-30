package com.cas.casdashboard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cas.casdashboard.databinding.SideBarRvItemBinding
import com.cas.casdashboard.https.response.decode.LoginResultItem

/**
 * @author Benjamin
 * @description:Home side navigation bar, single choice。
 * @date :2021.11.11 11:11
 */
class SideBarAdapter(private val itemClick: (LoginResultItem) -> Unit):ListAdapter<LoginResultItem,SideBarAdapter.ViewHolder>(
    object : DiffUtil.ItemCallback<LoginResultItem>() {
        override fun areItemsTheSame(
            oldItem: LoginResultItem,
            newItem: LoginResultItem
        ) =
            oldItem == newItem

        override fun areContentsTheSame(
            oldItem: LoginResultItem,
            newItem: LoginResultItem
        ) =
            oldItem == newItem

    }
) {
    private var currentPos = -1
    fun setPosition(){
        currentPos = 0
        itemClick(getItem(0))
    }
    inner class ViewHolder(binding:SideBarRvItemBinding):RecyclerView.ViewHolder(binding.root){
        private val sideBarName = binding.sideBarName
        val background = binding.background
        fun bind(loginResultItem: LoginResultItem){
            sideBarName.text = loginResultItem.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        SideBarRvItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
    ).apply {
        background.setOnClickListener {
            itemClick(getItem(absoluteAdapterPosition))
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
}