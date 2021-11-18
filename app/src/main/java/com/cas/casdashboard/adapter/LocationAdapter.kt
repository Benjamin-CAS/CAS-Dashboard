package com.cas.casdashboard.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cas.casdashboard.databinding.LocationItemBinding
import com.cas.casdashboard.https.response.decode.CompanyLocationDecodeItem

/**
 * @author Benjamin
 * @description:
 * @date :2021.9.15 16:46
 */
class LocationAdapter(private val itemClick: (CompanyLocationDecodeItem) -> Unit) :
    ListAdapter<CompanyLocationDecodeItem, LocationAdapter.ViewHolder>(
        object : DiffUtil.ItemCallback<CompanyLocationDecodeItem>() {
            override fun areItemsTheSame(
                oldItem: CompanyLocationDecodeItem,
                newItem: CompanyLocationDecodeItem
            ) =
                oldItem == newItem

            override fun areContentsTheSame(
                oldItem: CompanyLocationDecodeItem,
                newItem: CompanyLocationDecodeItem
            ) =
                oldItem == newItem

        }
    ) {
    inner class ViewHolder(view: LocationItemBinding) : RecyclerView.ViewHolder(view.root) {
        val title = view.companyName
        val root = view.root
        fun bind(companyLocationDecodeItem: CompanyLocationDecodeItem) {
            title.text = companyLocationDecodeItem.name_en
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LocationItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    ).apply {
        root.setOnClickListener {
            itemClick(getItem(absoluteAdapterPosition))
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}