package com.anderson.apigithub_mvvm.feature.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.databinding.DataBindingUtil
import com.anderson.apigithub_mvvm.R
import com.anderson.apigithub_mvvm.data.presentation.RepositoryPresentation

/**
 * Created by anderson on 21/09/19.
 */
class RepositoryAdapter constructor(
    var list: ArrayList<RepositoryPresentation>
) : BaseAdapter() {

    override fun getItem(position: Int): Any {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        //val binding: ItemMovieBinding
        var view  = LayoutInflater.from(parent?.context).inflate(R.layout.item_repository, null)

//        binding = DataBindingUtil.bind(view)!!
//        binding.item = this.moviesList[position]
//
//        return  binding.root

        return view
    }
}