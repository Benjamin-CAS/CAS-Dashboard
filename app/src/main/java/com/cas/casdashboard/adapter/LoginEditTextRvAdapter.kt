package com.cas.casdashboard.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cas.casdashboard.databinding.PopupRvItemBinding
import com.cas.casdashboard.model.room.entity.CompanyAllEntity

class LoginEditTextRvAdapter(private val itemClick:(CompanyAllEntity) -> Unit): ListAdapter<CompanyAllEntity, LoginEditTextRvAdapter.ViewHolder>(
    object :DiffUtil.ItemCallback<CompanyAllEntity>(){
        override fun areItemsTheSame(
            oldItem: CompanyAllEntity,
            newItem: CompanyAllEntity
        ) = oldItem === newItem

        override fun areContentsTheSame(
            oldItem: CompanyAllEntity,
            newItem: CompanyAllEntity
        ) = oldItem == newItem
    }
){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        PopupRvItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)).apply {
        viewRoot.setOnClickListener {
            itemClick(getItem(absoluteAdapterPosition))
        }
    }

    inner class ViewHolder(binding:PopupRvItemBinding):RecyclerView.ViewHolder(binding.root){
        private val companyTitleText = binding.companyTitleText
        val viewRoot = binding.root
        fun bind(companyAllEntity: CompanyAllEntity){
            companyTitleText.text = companyAllEntity.companyAllName
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    companion object{
        const val TAG = "LoginEditTextRvAdapter"
    }

}
