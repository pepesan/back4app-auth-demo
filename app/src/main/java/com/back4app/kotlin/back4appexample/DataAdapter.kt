package com.back4app.kotlin.back4appexample

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView

class DataAdapter(private val allData: MutableList<Data>, val dataActivity: Activity):RecyclerView.Adapter<DataAdapter.DataViewHolder>() {
    inner class DataViewHolder(view:View):RecyclerView.ViewHolder(view), View.OnClickListener{
        var name:TextView=view.findViewById(R.id.item_tvName)
        init {
            name.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            val bundle= bundleOf("name" to this.name.text.toString().toInt())
            (dataActivity as ListActivity).navHost.navController.navigate(R.id.action_listFragment_to_detailFragment, bundle)
        }

        fun setItem(data:Data){
            name.text=data.value
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        return DataViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item,parent,false))
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.setItem(allData[position])
    }

    override fun getItemCount(): Int {
        return allData.size
    }
}