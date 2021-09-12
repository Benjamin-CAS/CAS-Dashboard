package com.cas.casdashboard.adapter


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cas.casdashboard.databinding.PopupRvItemBinding
import com.cas.casdashboard.model.room.entity.CompanyAllEntity

class LoginEditTextRvAdapter: ListAdapter<CompanyAllEntity, LoginEditTextRvAdapter.ViewHolder>(
    object :DiffUtil.ItemCallback<CompanyAllEntity>(){
        override fun areItemsTheSame(
            oldItem: CompanyAllEntity,
            newItem: CompanyAllEntity
        ) = oldItem == newItem

        override fun areContentsTheSame(
            oldItem: CompanyAllEntity,
            newItem: CompanyAllEntity
        ) = oldItem == newItem
    }
){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val holder = ViewHolder(PopupRvItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
        holder.viewRoot.setOnClickListener {
            Log.e(TAG, "onCreateViewHolder: ")
            this.textViewOnClick?.let { click->
                click(getItem(holder.absoluteAdapterPosition))
            }
        }
        return holder
    }

    inner class ViewHolder(private val binding:PopupRvItemBinding):RecyclerView.ViewHolder(binding.root){
        val companyTitleText = binding.companyTitleText
        val viewRoot = binding.root
        fun bind(companyAllEntity: CompanyAllEntity){
            companyTitleText.text = companyAllEntity.companyAllName
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    private var textViewOnClick:((CompanyAllEntity) ->Unit) ?= null
    fun setOnTextViewOnClick(listener:(CompanyAllEntity) ->Unit){
        this.textViewOnClick = listener
    }
    companion object{
        const val TAG = "LoginEditTextRvAdapter"
    }
}
