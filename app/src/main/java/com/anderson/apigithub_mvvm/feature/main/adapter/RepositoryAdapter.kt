package com.anderson.apigithub_mvvm.feature.main.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anderson.apigithub_mvvm.R
import com.anderson.apigithub_mvvm.data.presentation.RepositoryPresentation
import com.anderson.apigithub_mvvm.databinding.ItemRepositoryBinding

/**
 * Created by anderson on 21/09/19.
 */
class RepositoryAdapter constructor(private val clickAction: ((presentation: RepositoryPresentation) -> Unit)): ListAdapter<RepositoryPresentation, RepositoryListItemViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryListItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_repository, parent, false)

        return RepositoryListItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: RepositoryListItemViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener{
            clickAction.invoke(getItem(position))
        }
    }
}

class RepositoryListItemViewHolder ( itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(presentation: RepositoryPresentation){
        val binding: ItemRepositoryBinding? = DataBindingUtil.bind(itemView)
        binding?.item = presentation
    }

}

private class DiffCallback : DiffUtil.ItemCallback<RepositoryPresentation>() {

    override fun areItemsTheSame(oldItem: RepositoryPresentation, newItem: RepositoryPresentation): Boolean {
        return oldItem.id == newItem.id
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: RepositoryPresentation, newItem: RepositoryPresentation): Boolean {
        return oldItem == newItem
    }
}
