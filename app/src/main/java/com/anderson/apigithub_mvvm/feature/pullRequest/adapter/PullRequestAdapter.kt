package com.anderson.apigithub_mvvm.feature.pullRequest.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anderson.apigithub_mvvm.R
import com.anderson.apigithub_mvvm.data.presentation.PullRequestPresentation
import com.anderson.apigithub_mvvm.databinding.ItemPullRequestBinding

/**
 * Created by anderson on 22/09/19.
 */
class PullRequestAdapter constructor(private val clickAction: ((presentation: PullRequestPresentation) -> Unit)) :
    ListAdapter<PullRequestPresentation, PullRequestListItemViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PullRequestListItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pull_request, parent, false)

        return PullRequestListItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: PullRequestListItemViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener{
            clickAction.invoke(getItem(position))
        }
    }
}

class PullRequestListItemViewHolder ( itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(presentation: PullRequestPresentation){
        val binding: ItemPullRequestBinding? = DataBindingUtil.bind(itemView)
        binding?.item = presentation
    }

}

private class DiffCallback : DiffUtil.ItemCallback<PullRequestPresentation>() {

    override fun areItemsTheSame(oldItem: PullRequestPresentation, newItem: PullRequestPresentation): Boolean {
        return oldItem.id == newItem.id
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: PullRequestPresentation, newItem: PullRequestPresentation): Boolean {
        return oldItem == newItem
    }
}
