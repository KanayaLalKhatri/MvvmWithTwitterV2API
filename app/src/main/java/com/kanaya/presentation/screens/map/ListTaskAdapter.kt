package com.kanaya.presentation.screens.map

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kanaya.core.model.dto.TweetModel
import com.kanaya.mvvmexample.R
import kotlinx.android.synthetic.main.cell_task.view.*


class ListTaskAdapter(
    private val context: Context,
    private val list: ArrayList<TweetModel>
) :
    RecyclerView.Adapter<ListTaskAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.cell_task,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = list[position]

        model.id?.let {
            holder.id.text = it.toString()
        }
        model.text?.let {
            holder.task.text = it
        }

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val id = view.tvId
        val task = view.tvTask
    }
}